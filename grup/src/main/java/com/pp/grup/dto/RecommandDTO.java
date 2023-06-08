package com.pp.grup.dto;
// 컨트롤러 클래스에서 사용하는 모델 클래스
import com.pp.grup.entity.RecommandEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RecommandDTO {
    private Long answerId;
    private String userEmail;
    private Integer answerTemperature;
    private Integer answerLight;
    private Integer answerWater;
    private Integer answerLevel;

    public static RecommandDTO toRecommandDTO(RecommandEntity recommandEntity){
        RecommandDTO recommandDTO =new RecommandDTO();

        recommandDTO.setAnswerId(recommandEntity.getAnswerId());
        recommandDTO.setUserEmail(recommandEntity.getUserEmail());
        recommandDTO.setAnswerTemperature(recommandEntity.getAnswerTemperature());
        recommandDTO.setAnswerLight(recommandEntity.getAnswerLight());
        recommandDTO.setAnswerWater(recommandEntity.getAnswerWater());
        recommandDTO.setAnswerLevel(recommandEntity.getAnswerLevel());
        return recommandDTO;
    }

}
