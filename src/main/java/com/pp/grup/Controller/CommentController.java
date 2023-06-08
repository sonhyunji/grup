package com.pp.grup.Controller;

import com.pp.grup.Entity.Comment;
import com.pp.grup.Service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public String createComment(@RequestParam("postId") Integer postId,
                                @RequestParam("content") String content,
                                HttpSession session, Model model, Comment comment) throws Exception{
        String myEmail= (String) session.getAttribute("loginEmail");
        if (myEmail == null) {
            return "redirect:/PlantsPlanet/login"; // 로그인 페이지 URL로 리다이렉트
        }

        String commentWriter = (String) session.getAttribute("loginName");
        if (commentWriter == null) {
            return "redirect:/PlantsPlanet/login"; // 로그인 페이지 URL로 리다이렉트
        }
        model.addAttribute("commentWriter", commentWriter);

        comment.setCommentWriter(commentWriter);
        comment.setPostId(postId);
        comment.setContent(content);

        commentService.write(comment);

        return "redirect:/board/view?id=" + postId;
    }

    @PostMapping("/comments/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId, @RequestParam("postId") Integer postId) {

        commentService.commentDelete(commentId);

        return "redirect:/board/view?id=" + postId;
    }


}

