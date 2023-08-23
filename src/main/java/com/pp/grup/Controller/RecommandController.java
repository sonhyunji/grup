package com.pp.grup.Controller;
// repository와 서비스 레이어를 구분지어 관리
// 직접 repository에 접근해도 되긴하는데 보통 서비스 클래스 사용

import com.pp.grup.Dto.PlantsDTO;
import com.pp.grup.Dto.AnswerDTO;
import com.pp.grup.Service.RecommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class RecommandController {
    //생성자
    private final RecommandService recommandService;

    @GetMapping("/grup/startquestion")
    public String startQuestion(){
        return "startquestion";
    }

    //추천페이지 질문지
    @GetMapping("/grup/question")
    public String questionForm(HttpSession session, Model model){
        String myEmail= (String) session.getAttribute("loginEmail");
        if(myEmail == null){
            return "login";
        } else {
            model.addAttribute("userEmail", myEmail);
            return "question";
        }
    }
    @PostMapping("/grup/question")
    public String question(@ModelAttribute AnswerDTO answerDTO, HttpSession session){
        //session에 answerId 담기

        AnswerDTO answerResult=recommandService.question(answerDTO);
        //answerResult= question의 값이 저장된 dto
        session.setAttribute("userAnswerId", answerResult.getAnswerId());
        return "resulting";
    }

    @GetMapping("/grup/resulting")
    public String resulting(HttpSession session, Model model) {
        Long userAnswerId = (Long) session.getAttribute("userAnswerId");
        Long plantsId;
        List<Map<Long, Float>> resultingPlantsList = new ArrayList<>();

        AnswerDTO answerDTO = recommandService.getAnswerById(userAnswerId);
        PlantsDTO plantsDTO;

        List<Long> levelABSId =recommandService.findLevelABS(userAnswerId);
        model.addAttribute("levelABSId", levelABSId);
        System.out.println("-----level, answerId-----: "+userAnswerId);
        for (Long value : levelABSId) {
            plantsId = value;
            System.out.println("plantsId: "+plantsId);

            plantsDTO=recommandService.getPlantsById(plantsId);
            if(!containsPlantsId(resultingPlantsList, plantsId)){
                Map<Long, Float> resultingMap = new HashMap<>();
                getResulting(plantsId, answerDTO, plantsDTO, resultingMap, resultingPlantsList);
            }
        }
        List<Long> temperatureABSId = recommandService.findTemperatureABS(userAnswerId);
        model.addAttribute("temperatureABSId", temperatureABSId);
        System.out.println("-----temperature, answerId-----: "+userAnswerId);
        for (Long value : temperatureABSId) {
            plantsId = value;
            System.out.println("plantsId: "+plantsId);

            plantsDTO=recommandService.getPlantsById(plantsId);

            if(!containsPlantsId(resultingPlantsList, plantsId)){
                Map<Long, Float> resultingMap = new HashMap<>();
                getResulting(plantsId, answerDTO, plantsDTO, resultingMap, resultingPlantsList);
            }
        }
        List<Long> lightABSId = recommandService.findLightABS(userAnswerId);
        model.addAttribute("lightABSId", lightABSId);
        System.out.println("-----light, answerId-----: "+userAnswerId);
        for (Long value : lightABSId) {
            plantsId = value;
            System.out.println("plantsId: "+plantsId);

            plantsDTO=recommandService.getPlantsById(plantsId);
            if(!containsPlantsId(resultingPlantsList, plantsId)){
                Map<Long, Float> resultingMap = new HashMap<>();
                getResulting(plantsId, answerDTO, plantsDTO, resultingMap, resultingPlantsList);
            }
        }
        List<Long> waterABSId = recommandService.findWaterABS(userAnswerId);
        model.addAttribute("waterABSID", waterABSId);
        System.out.println("-----water, answerId-----: "+userAnswerId);
        for (Long value : waterABSId) {
            plantsId = value;
            System.out.println("plantsId: "+plantsId);

            plantsDTO=recommandService.getPlantsById(plantsId);
            if(!containsPlantsId(resultingPlantsList, plantsId)){
                Map<Long, Float> resultingMap = new HashMap<>();
                getResulting(plantsId, answerDTO, plantsDTO, resultingMap, resultingPlantsList);
            }
        }

        resultingPlantsList.sort(Comparator.comparing(map -> map.values().iterator().next()));
//        //오름차순

        List<Long> resultPlantsId = new ArrayList<>();
        int count = 0;
        for (Map<Long, Float> resultMap : resultingPlantsList) {
            Long plantId = resultMap.keySet().iterator().next();
            resultPlantsId.add(plantId);
            count++;
            if (count == 3) {
                break;
            }
        }
        model.addAttribute("resultPlantsId", resultPlantsId);
        System.out.println(resultPlantsId);
        session.setAttribute("resultPlantsId", resultPlantsId);

        return "resulting";
    }

    private boolean containsPlantsId(List<Map<Long, Float>> list, Long plantsId) {
        for (Map<Long, Float> map : list) {
            if (map.containsKey(plantsId)) {
                return true;
            }
        }
        return false;
    }
    private void getResulting (Long plantsId, AnswerDTO answerDTO, PlantsDTO plantsDTO, Map resultingMap, List<Map<Long, Float>> resultingPlantsList){
        Float temperatureABS;
        Float lightABS;
        Float waterABS;
        Float levelABS;
        Float similarity;

        temperatureABS = (float) Math.abs(plantsDTO.getPlantsTemperature() - answerDTO.getAnswerTemperature());
        lightABS = (float) Math.abs(plantsDTO.getPlantsLight() - answerDTO.getAnswerLight());
        waterABS = (float) Math.abs(plantsDTO.getPlantsWater() - answerDTO.getAnswerWater());
        levelABS = (float) Math.abs(plantsDTO.getPlantsLevel() - answerDTO.getAnswerLevel());
        if (plantsDTO.getPlantsSelected() == 0) {
            similarity = (temperatureABS + lightABS + waterABS + levelABS) / 4;
        } else {
            similarity = (temperatureABS + lightABS + waterABS + levelABS) / 4 - plantsDTO.getPlantsSelected() / 10;
        }
        resultingMap.put(plantsId, similarity);
        resultingPlantsList.add(resultingMap);
    }

    @PostMapping("/grup/resulting")
    public String setAnswerSession(HttpSession session, Model model){

        Long userAnswerId = (Long) session.getAttribute("userAnswerId");
        AnswerDTO answerDTO = recommandService.getAnswerById(userAnswerId);
        model.addAttribute("userAnswerPlants", answerDTO);

        List<Long> resultPlantsId= (List<Long>) session.getAttribute("resultPlantsId");
        for (int i = 0; i < resultPlantsId.size(); i++) {
            Long plantsId = resultPlantsId.get(i);
            String resultId="result"+i;
            PlantsDTO resultDTO = recommandService.getPlantsById(plantsId);
            model.addAttribute(resultId, resultDTO);
        }
        return "result";
    }

    @GetMapping("/grup/result")
    public String result(HttpSession session, Model model) {
        Long userAnswerId = (Long) session.getAttribute("userAnswerId");
        AnswerDTO answerDTO = recommandService.getAnswerById(userAnswerId);
        model.addAttribute("userAnswerPlants", answerDTO);

        List<Long> resultPlantsId= (List<Long>) session.getAttribute("resultPlantsId");
        for (int i = 0; i < resultPlantsId.size(); i++) {
            Long plantsId = resultPlantsId.get(i);
            String resultId="result"+i;
            PlantsDTO resultDTO = recommandService.getPlantsById(plantsId);
            model.addAttribute(resultId, resultDTO);
        }
        return "result";
    }
    @PostMapping("/grup/resultplants")
    public String resultPlants(){

        return "resultplants";
    }

    @GetMapping("/grup/resultplants")
    public String resultPlants(@RequestParam("resultId") String resultId, Model model, HttpSession session){
        List<Long> resultPlantsId= (List<Long>) session.getAttribute("resultPlantsId");

        if (!resultId.isEmpty()) {
            Long plantsId = resultPlantsId.get(Integer.parseInt(resultId));
            PlantsDTO resultDTO = recommandService.getPlantsById(plantsId);
            model.addAttribute("resultDTO", resultDTO);
        } else {
            System.out.println("resultId is null");
            return null;
        }
        return "resultplants";
    }
}
