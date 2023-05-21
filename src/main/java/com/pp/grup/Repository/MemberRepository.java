package com.pp.grup.Repository;

import com.pp.grup.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//디비를 다루는 최종 클래스

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    //다룰 엔티티 클래스, pk type(id)
    //꼭 엔티티로 넘겨줘야함

    // 이메일로 회원 정보 조회(select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    Optional<MemberEntity> findByMemberName(String memberName);
    //optional: null 방지, 1개 조회

}
