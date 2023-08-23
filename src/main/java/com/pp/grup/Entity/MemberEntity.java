package com.pp.grup.Entity;

import com.pp.grup.Dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "users")
public class MemberEntity {

    @Id // pk
    @Column(unique = true, name = "userEmail") //unique 제약조건 추가
    private String memberEmail;

    @Column(name = "userPassword")
    private String memberPassword;
    @Column(unique = true, name = "userName") // unique
    private String memberName;
    @Column(name = "userBirth")
    private String memberBirth;
    @Column(name = "userPhone")
    private String memberNum;
    @Column(name = "profilePic")
    private String memberPic;

    //처음에 회원가입할 땐 id가 자동으로 부여되는데
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        memberEntity.setMemberPic(memberDTO.getMemberPic());
        return memberEntity;
    }

    //수정할 땐 이미 id가 있으니까 그걸 가져오는게 필요함
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        memberEntity.setMemberPic(memberDTO.getMemberPic());
        return memberEntity;
    }


}
