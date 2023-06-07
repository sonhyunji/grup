package com.pp.grup.Controller;

import com.pp.grup.Entity.Comment;
import com.pp.grup.Service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        String commentWriter = (String) session.getAttribute("loginName");
        model.addAttribute("commentWriter", commentWriter);

        comment.setCommentWriter(commentWriter);
        comment.setPostId(postId);
        comment.setContent(content);

        commentService.write(comment);

        return "redirect:/board/view?id=" + postId;
    }
}

