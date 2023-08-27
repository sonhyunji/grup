package com.pp.grup.Controller;

import com.pp.grup.Entity.Board;
import com.pp.grup.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    //기본페이지 요청 메서드
    @Autowired
    private BoardService boardService; // BoardService 주입

    // 기본 페이지 요청 메서드
    @GetMapping(value = {"/", "/index", "/main"})
    public String main(HttpSession session, Model model) {
        if (session.getAttribute("loginEmail") == null) {
            // 좋아요 순으로 게시물 가져오기
            List<Board> topLikedBoards = boardService.getTopLikedBoardsWithinMonth();
            model.addAttribute("topLikedBoards", topLikedBoards);

            // 조회수 순으로 게시물 가져오기
            List<Board> topViewedBoards = boardService.getTopViewedBoardsWithinMonth();
            model.addAttribute("topViewedBoards", topViewedBoards);

            return "index";
        } else {
            session.getAttribute("loginEmail");

            // 좋아요 순으로 게시물 가져오기
            List<Board> topLikedBoards = boardService.getTopLikedBoardsWithinMonth();
            model.addAttribute("topLikedBoards", topLikedBoards);

            // 조회수 순으로 게시물 가져오기
            List<Board> topViewedBoards = boardService.getTopViewedBoardsWithinMonth();
            model.addAttribute("topViewedBoards", topViewedBoards);

            return "main";
        }
    }
}