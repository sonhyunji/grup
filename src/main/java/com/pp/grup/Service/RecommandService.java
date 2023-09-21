package com.pp.grup.Service;
// 하나 이상의 repository를 묶어서 관리하는 목적으로 사용
// repository 하나만 연결해도 o

import com.pp.grup.Dto.PlantsDTO;
import com.pp.grup.Dto.RecommandDTO;
import com.pp.grup.Entity.PlantsEntity;
import com.pp.grup.Entity.RecommandEntity;
import com.pp.grup.Repository.PlantsRepository;
import com.pp.grup.Repository.RecommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommandService {
    private final RecommandRepository recommandRepository;
    //    private final MemberRepository memberRepository;
    private final PlantsRepository plantsRepository;


//    private final RecommandService recommandService;

    // 질문지에서 받은 내용을 그대로 저장
    public RecommandDTO question(RecommandDTO recommandDTO) {
        // 1 dto->entity 변환(서비스 클래스에서 변환하는 메서드 추가/엔티티에서 변환/ ...
        // 2 repository의 question 메서드 호출
        RecommandEntity recommandEntity = RecommandEntity.toRecommandEntity(recommandDTO);

//        recommandDTO.setUserEmail((String) session.getAttribute("loginEmail"));
        recommandRepository.save(recommandEntity);
        // repository의 question메서드 호출(조건. 엔티티 객체를 넘겨줘야함)
        //jpa어쩌구 상속받아 쓰는거라 repository에 question 직접 쓴게 없음
        RecommandDTO dto= RecommandDTO.toRecommandDTO(recommandEntity);
        return dto;
    }

    public RecommandDTO getAnswerById(Long answerId){
        Optional<RecommandEntity> optionalRecommandEntity=recommandRepository.findByAnswerId(answerId);
        if (optionalRecommandEntity.isPresent()){
            return RecommandDTO.toRecommandDTO(optionalRecommandEntity.get());
        } else {
            return null;
        }
    }

    public Long findTemperatureABS(Long userAnswerId){
        Long temperatureABSId=plantsRepository.findTemperatureABS(userAnswerId);
        return temperatureABSId;
    }
    public Long findLightABS(Long userAnswerId){
        Long lightABSId=plantsRepository.findLightABS(userAnswerId);
        return lightABSId;
    }
    public Long findWaterABS(Long userAnswerId){
        Long waterABSId=plantsRepository.findWaterABS(userAnswerId);
        return waterABSId;
    }
    public Long findLevelABS(Long userAnswerId){
        Long levelABSId=plantsRepository.findLevelABS(userAnswerId);
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
    public Optional<PlantsEntity> getPlantsEntityById(Long plantsId){
        Optional<PlantsEntity> optionalPlantsEntity=plantsRepository.findByPlantsId(plantsId);
        if (optionalPlantsEntity.isPresent()){
            return optionalPlantsEntity;
        } else {
            return null;
        }
    }

    public void setPlantsSelected(Long plantsId){
        plantsRepository.setPlantsSelected(plantsId);
    }

//    public void setMemberPlantsName(String memberEmail, String plantsName){
//        memberRepository.setMemberPlantsName(plantsName, memberEmail);
//        System.out.println("setMemberPlantsName Service complete");
//    }
}
