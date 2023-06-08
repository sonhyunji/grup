package com.pp.grup.Repository;

import com.pp.grup.Entity.PlantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantsRepository extends JpaRepository<PlantsEntity, Long> {

    Optional<PlantsEntity> findByPlantsId(Long plantsId);

    @Query(value = "select p.plantsId "+
            "from plants p, answerplants a "+
            "where a.answerId=:userAnswerId "+
            "and abs(p.plantsTemperature-a.answerTemperature)=( "+
            "select  abs(p1.plantsTemperature-a1.answerTemperature) "+
            "from plants p1, answerPlants a1 "+
            "where a1.answerId=:userAnswerId "+
            "order by  abs(p1.plantsTemperature-a1.answerTemperature) "+
            "limit 1)", nativeQuery = true)
    List<Long> findTemperatureABS(Long userAnswerId);

    @Query(value = "select p.plantsId "+
            "from plants p, answerplants a "+
            "where a.answerId=:userAnswerId "+
            "and abs(p.plantsLight-a.answerLight)=( "+
            "select  abs(p1.plantsLight-a1.answerLight) "+
            "from plants p1, answerPlants a1 "+
            "where a1.answerId=:userAnswerId "+
            "order by  abs(p1.plantsLight-a1.answerLight) "+
            "limit 1)", nativeQuery = true)
    List<Long> findLightABS(Long userAnswerId);

    @Query(value = "select p.plantsId "+
            "from plants p, answerplants a "+
            "where a.answerId=:userAnswerId "+
            "and abs(p.plantsWater-a.answerWater)=( "+
            "select  abs(p1.plantsWater-a1.answerWater) "+
            "from plants p1, answerPlants a1 "+
            "where a1.answerId=:userAnswerId "+
            "order by  abs(p1.plantsWater-a1.answerWater) "+
            "limit 1)", nativeQuery = true)
    List<Long> findWaterABS(Long userAnswerId);
    @Query(value = "select p.plantsId "+
            "from plants p, answerplants a "+
            "where a.answerId=:userAnswerId "+
            "and abs(p.plantsLevel-a.answerLevel)=( "+
            "select  abs(p1.plantsLevel-a1.answerLevel) "+
            "from plants p1, answerPlants a1 "+
            "where a1.answerId=:userAnswerId "+
            "order by  abs(p1.plantsLevel-a1.answerLevel) "+
            "limit 1)", nativeQuery = true)
    List<Long> findLevelABS(Long userAnswerId);
}