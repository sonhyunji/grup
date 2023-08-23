package com.pp.grup.Controller;

import com.pp.grup.Dto.MemberDTO;
import com.pp.grup.Entity.Board;
import com.pp.grup.Service.BoardService;
import com.pp.grup.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    //생성자 주입
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);

        return "mypage";
    }

    //내가 쓴 게시글 조회
    @GetMapping("/myboard")
    public String myPosts(HttpSession session, Model model) {
        String memberName = (String) session.getAttribute("loginName");
        List<Board> myboard = boardService.getMyPosts(memberName);
        model.addAttribute("myboard", myboard);
        return "myboardlist";
    }
}
