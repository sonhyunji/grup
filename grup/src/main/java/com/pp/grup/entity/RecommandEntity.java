package com.pp.grup.entity;
//

import com.pp.grup.dto.RecommandDTO;
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

    public static RecommandEntity toRecommandEntity(RecommandDTO recommandDTO) {
        RecommandEntity recommandEntity = new RecommandEntity();

        recommandEntity.setAnswerId(recommandDTO.getAnswerId());
        recommandEntity.setUserEmail(recommandDTO.getUserEmail());
        recommandEntity.setAnswerTemperature(recommandDTO.getAnswerTemperature());
        recommandEntity.setAnswerLight(recommandDTO.getAnswerLight());
        recommandEntity.setAnswerWater(recommandDTO.getAnswerWater());
        recommandEntity.setAnswerLevel(recommandDTO.getAnswerLevel());
        return recommandEntity;
    }

}
