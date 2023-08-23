package com.pp.grup.Controller;

import com.pp.grup.Entity.Board;
import com.pp.grup.Entity.Likes;
import com.pp.grup.Service.BoardService;
import com.pp.grup.Service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpSession;

@Controller
public class LikesController {

    public final BoardService boardService;
    public final LikesService likesService;

    public LikesController(BoardService boardService, LikesService likesService) {
        this.boardService = boardService;
        this.likesService = likesService;
    }

    @GetMapping("/likes/click/{id}")
    public String click(@PathVariable Integer id, Likes likes, HttpSession session) {
        String memberEmail= (String) session.getAttribute("loginEmail");
        if (memberEmail == null) {
            return "redirect:/PlantsPlanet/login"; // 로그인 페이지 URL로 리다이렉트
        }

        Board board = boardService.getBoardById(id);

        if (likesService.existsByUserEmailAndBoardId(memberEmail, id) == true) {
            board.setLikeCount(board.getLikeCount() - 1);
            boardService.saveBoard(board); // 변경된 좋아요수를 저장
            likesService.delete(id, memberEmail);
        } else {
            // 좋아요 정보가 없으면 추가
            board.setLikeCount(board.getLikeCount() + 1);
            boardService.saveBoard(board);
            likes.setUserEmail(memberEmail);
            likes.setBoardId(id);
            likesService.write(likes);
        }

        return "redirect:/board/view?id=" + id;
    }
}

