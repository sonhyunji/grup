package com.pp.grup.Service;

import com.pp.grup.Dto.MemberDTO;
import com.pp.grup.Entity.MemberEntity;
import com.pp.grup.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO){
        // 1 dto->entity 변환(서비스 클래스에서 변환하는 메서드 추가/엔티티에서 변환/ ...
        // 2 repository의 save 메서드 호출
        MemberEntity memberEntity=MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        // repository의 save메서드 호출(조건. 엔티티 객체를 넘겨줘야함)
        //jpa어쩌구 상속받아 쓰는거라 repository에 save 직접 쓴게 없음
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1 회원이 입력한 이메일 db 조회
        // 2 db의 비민번호와 일치하는지 확인
        // ---------이거 완전 구식이라서 나중에 좀 손보기--------
        Optional<MemberEntity> byMemberEmail=memberRepository.findByMemberEmail((memberDTO.getMemberEmail()));
        //엔티티객체를 옵셔널로 한 번 더 감싸는거임
        if (byMemberEmail.isPresent()){
            //조회 결과가 있다
            MemberEntity memberEntity=byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //비밀번호 일치
                //entity->dto변환 후 리턴
                MemberDTO dto=MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                //비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(로그인 실패)
            return null;
        }
    }

//    public List<MemberDTO> findAll() {
//        List<MemberEntity> memberEntityList = memberRepository.findAll();
//        //entity->dto 변환 필요
//        List<MemberDTO> memberDtoList=new ArrayList<>();
//        //리스트라 여러개가 있음=> 리스트에서 하나하나 꺼내서 변환하고 다시 담아줌
//        for (MemberEntity memberEntity: memberEntityList) {
//            memberDtoList.add(MemberDTO.toMemberDTO(memberEntity));
//        }
//        return memberDtoList;
//
//    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            //optional 객체를 get으로 한 번 까고 변환을 하든 뭘 하든
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
//        MemberEntity memberEntity=MemberEntity.toMemberEntity(memberDTO);
//        memberRepository.save(memberEntity);

        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
        //업데이트된 id 가져와야됨(toUpdate)
    }

    public void deleteByMemberEmail(String memberEmail) { memberRepository.deleteByMemberEmail(memberEmail);}

    //회원가입 중복 확인
    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            //조회결과가 있음 => 사용x
            return null;
        } else {
            return "ok";
        }
    }
    public String nameCheck(String memberName) {
        Optional<MemberEntity> byMemberName = memberRepository.findByMemberName(memberName);
        if (byMemberName.isPresent()) {
            //조회결과가 있음 => 사용x
            return null;
        } else {
            return "ok";
        }
    }
}
