package com.pp.grup.Controller;

import com.pp.grup.Dto.MemberDTO;
import com.pp.grup.Entity.MemberEntity;
import com.pp.grup.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {
    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지 요청 출력
    @GetMapping("/PlantsPlanet/save")
    public String saveForm(){
        return "save";
    }
    @PostMapping("/PlantsPlanet/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "login";
    }

    //로그인페이지
    @GetMapping("/PlantsPlanet/login")
    public String loginForm(){
        return "login";
    }
    @PostMapping("/PlantsPlanet/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult=memberService.login(memberDTO);
        if (loginResult!=null){
            //login success
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("loginName", loginResult.getMemberName());
            //세션에 로그인 정보를 담는다
            return "main";
        } else {
            //login fail
            return "login";
        }
    }

    //이메일 중복 확인(ajax)
    @PostMapping("/PlantsPlanet/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String emailCheckResult=memberService.emailCheck(memberEmail);
        return emailCheckResult;
    }
    //닉네임 중복 확인(ajax)
    @PostMapping("/PlantsPlanet/name-check")
    public @ResponseBody String nameCheck(@RequestParam("memberName") String memberName) {
        String nameCheckResult = memberService.nameCheck(memberName);
        return nameCheckResult;
    }

    //회원정보 수정
    @GetMapping("/PlantsPlanet/update")
    public String updateForm(HttpSession session, Model model){
        //오브젝트를 스트링에 담기엔 넘 크다, 강제 형변환
        String myEmail= (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO=memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }
    @PostMapping("/PlantsPlanet/update")
    public String update(MemberDTO memberDTO, HttpSession session){
        memberService.update(memberDTO);
        //수정된 정보를 업데이트해서 리다이렉트
        return "redirect:/mypage";
    }
//    @PostMapping("/PlantsPlanet/save")
//    public String save(@ModelAttribute MemberDTO memberDTO){
//        memberService.save(memberDTO);
//        return "login";
//    }

    //삭제
    @GetMapping("/PlantsPlanet/delete/{memberEmail}")
    public String deleteByMemberEmail(@PathVariable("memberEmail") String memberEmail, HttpSession session) {
        memberService.deleteByMemberEmail(memberEmail);
        session.invalidate();
        return "redirect:/";
    }

    //로그아웃, 세션에서 지운다
    @GetMapping("/PlantsPlanet/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

}