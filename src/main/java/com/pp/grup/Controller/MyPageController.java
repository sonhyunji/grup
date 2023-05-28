package com.pp.grup.Controller;

import com.pp.grup.Dto.MemberDTO;
import com.pp.grup.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    //생성자 주입
    private final MemberService memberService;

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);

        return "mypage";
    }
}
