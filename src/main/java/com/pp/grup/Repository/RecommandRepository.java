package com.pp.grup.Repository;

// 디비랑 연결, 데이터 처리

import com.pp.grup.Entity.RecommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommandRepository extends JpaRepository <RecommandEntity, Long> {
    Optional<RecommandEntity> findByAnswerId(Long answerId);
    //id로 검색

    // 다룰 엔티티 클래스, pk type(id)
    // 꼭 엔티티로 넘겨줘야함
}
