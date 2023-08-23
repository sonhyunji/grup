package com.pp.grup.Dto;
// 컨트롤러 클래스에서 사용하는 모델 클래스
import com.pp.grup.Entity.RecommandEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AnswerDTO {
    private Long answerId;
    private String userEmail;
    private Integer answerTemperature;
    private Integer answerLight;
    private Integer answerWater;
    private Integer answerLevel;

    public static AnswerDTO toRecommandDTO(RecommandEntity recommandEntity){
        AnswerDTO answerDTO =new AnswerDTO();

        answerDTO.setAnswerId(recommandEntity.getAnswerId());
        answerDTO.setUserEmail(recommandEntity.getUserEmail());
        answerDTO.setAnswerTemperature(recommandEntity.getAnswerTemperature());
        answerDTO.setAnswerLight(recommandEntity.getAnswerLight());
        answerDTO.setAnswerWater(recommandEntity.getAnswerWater());
        answerDTO.setAnswerLevel(recommandEntity.getAnswerLevel());
        return answerDTO;
    }

}
