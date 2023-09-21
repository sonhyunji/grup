package com.pp.grup.Repository;

// 디비랑 연결, 데이터 처리

import com.pp.grup.Entity.RecommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RecommandRepository extends JpaRepository <RecommandEntity, Long> {
    Optional<RecommandEntity> findByAnswerId(Long answerId);
    //id로 검색

    // 다룰 엔티티 클래스, pk type(id)
    // 꼭 엔티티로 넘겨줘야함

    @Query(value = "select p.plantsId "+
            "from plants p, answerPlants a "+
            "where a.answerId=:userAnswerId "+
            "and abs(p.plantsTemperature-a.answerTemperature)=( "+
            "select  abs(p1.plantsTemperature-a1.answerTemperature) "+
            "from plants p1, answerPlants a1 "+
            "where a1.answerId=:userAnswerId "+
            "order by  abs(p1.plantsTemperature-a1.answerTemperature) "+
            "limit 1)"+
            "ORDER BY RAND() " +
            "LIMIT 1", nativeQuery = true)
    Long findTemperatureABS(@Param("userAnswerId") Long userAnswerId);

    @Query(value = "SELECT p.plantsId "+
            "FROM plants p, answerPlants a "+
            "WHERE a.answerId=:userAnswerId "+
            "AND abs(p.plantsLight-a.answerLight)=( "+
            "SELECT  abs(p1.plantsLight-a1.answerLight) "+
            "FROM plants p1, answerPlants a1 "+
            "WHERE a1.answerId=:userAnswerId "+
            "ORDER BY abs(p1.plantsLight-a1.answerLight) "+
            "LIMIT 1)"+
            "ORDER BY RAND() " +
            "LIMIT 1", nativeQuery = true)
    Long findLightABS(@Param("userAnswerId") Long userAnswerId);

    @Query(value = "SELECT p.plantsId "+
            "FROM plants p, answerPlants a "+
            "WHERE a.answerId=:userAnswerId "+
            "AND abs(p.plantsWater-a.answerWater)=( "+
            "SELECT abs(p1.plantsWater-a1.answerWater) "+
            "FROM plants p1, answerPlants a1 "+
            "WHERE a1.answerId=:userAnswerId "+
            "ORDER BY  abs(p1.plantsWater-a1.answerWater) "+
            "LIMIT 1)" +
            "ORDER BY rand() " +
            "LIMIT 1", nativeQuery = true)
    Long findWaterABS(@Param("userAnswerId") Long userAnswerId);

    @Query(value = "SELECT p.plantsId " +
            "FROM plants p, answerPlants a " +
            "WHERE a.answerId = :userAnswerId " +
            "AND ABS(p.plantsLevel - a.answerLevel) = ( " +
            "SELECT ABS(p1.plantsLevel - a1.answerLevel) " +
            "FROM plants p1, answerPlants a1 " +
            "WHERE a1.answerId = :userAnswerId " +
            "ORDER BY ABS(p1.plantsLevel - a1.answerLevel) " +
            "LIMIT 1)" +
            "ORDER BY RAND() " +
            "LIMIT 1",
            nativeQuery = true)
    Long findLevelABS(@Param("userAnswerId") Long userAnswerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE plants p "+
            "SET p.plantsSelected=p.plantsSelected+1 "+
            "WHERE p.plantsId=:plantsId",
            nativeQuery = true)
    int setPlantsSelected (@Param("plantsId") Long plantsId);
}
