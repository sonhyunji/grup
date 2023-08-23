package com.pp.grup.Entity;

import com.pp.grup.Dto.AnswerDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "answerPlants")
public class RecommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //auto_increment
    private Long answerId;
    @Column(name = "userEmail")
    private String userEmail;
    @Column(name = "answerTemperature")
    private Integer answerTemperature;
    @Column(name = "answerLight")
    private Integer answerLight;
    @Column(name = "answerWater")
    private Integer answerWater;
    @Column(name = "answerLevel")
    private Integer answerLevel;

    public static RecommandEntity toRecommandEntity(AnswerDTO answerDTO) {
        RecommandEntity recommandEntity = new RecommandEntity();

        recommandEntity.setAnswerId(answerDTO.getAnswerId());
        recommandEntity.setUserEmail(answerDTO.getUserEmail());
        recommandEntity.setAnswerTemperature(answerDTO.getAnswerTemperature());
        recommandEntity.setAnswerLight(answerDTO.getAnswerLight());
        recommandEntity.setAnswerWater(answerDTO.getAnswerWater());
        recommandEntity.setAnswerLevel(answerDTO.getAnswerLevel());
        return recommandEntity;
    }

}
