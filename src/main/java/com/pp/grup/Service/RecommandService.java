package com.pp.grup.Service;
// 하나 이상의 repository를 묶어서 관리하는 목적으로 사용
// repository 하나만 연결해도 o

import com.pp.grup.Dto.PlantsDTO;
import com.pp.grup.Dto.AnswerDTO;
import com.pp.grup.Entity.PlantsEntity;
import com.pp.grup.Entity.RecommandEntity;
import com.pp.grup.Repository.PlantsRepository;
import com.pp.grup.Repository.RecommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommandService {
    private final RecommandRepository recommandRepository;
    private final PlantsRepository plantsRepository;


//    private final RecommandService recommandService;

    // 질문지에서 받은 내용을 그대로 저장
    public AnswerDTO question(AnswerDTO answerDTO) {
        // 1 dto->entity 변환(서비스 클래스에서 변환하는 메서드 추가/엔티티에서 변환/ ...
        // 2 repository의 question 메서드 호출
        RecommandEntity recommandEntity = RecommandEntity.toRecommandEntity(answerDTO);

//        recommandDTO.setUserEmail((String) session.getAttribute("loginEmail"));
        recommandRepository.save(recommandEntity);
        // repository의 question메서드 호출(조건. 엔티티 객체를 넘겨줘야함)
        //jpa어쩌구 상속받아 쓰는거라 repository에 question 직접 쓴게 없음
        AnswerDTO dto= AnswerDTO.toRecommandDTO(recommandEntity);
        return dto;
    }

    public AnswerDTO getAnswerById(Long answerId){
        Optional<RecommandEntity> optionalRecommandEntity=recommandRepository.findByAnswerId(answerId);
        if (optionalRecommandEntity.isPresent()){
            return AnswerDTO.toRecommandDTO(optionalRecommandEntity.get());
        } else {
            return null;
        }
    }

    public List<Long> findTemperatureABS(Long userAnswerId){
        List<Long> temperatureABSId=plantsRepository.findTemperatureABS(userAnswerId);
        return temperatureABSId;
    }
    public List<Long> findLightABS(Long userAnswerId){
        List<Long> lightABSId=plantsRepository.findLightABS(userAnswerId);
        return lightABSId;
    }
    public List<Long> findWaterABS(Long userAnswerId){
        List<Long> waterABSId=plantsRepository.findWaterABS(userAnswerId);
        return waterABSId;
    }
    public List<Long> findLevelABS(Long userAnswerId){
        List<Long> levelABSId=plantsRepository.findLevelABS(userAnswerId);
        return levelABSId;
    }

    public PlantsDTO getPlantsById(Long plantsId){
        Optional<PlantsEntity> optionalPlantsEntity=plantsRepository.findByPlantsId(plantsId);
        if (optionalPlantsEntity.isPresent()){
            return PlantsDTO.toPlantsDTO(optionalPlantsEntity.get());
        } else {
            return null;
        }
    }


}
