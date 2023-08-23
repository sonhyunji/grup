package com.pp.grup.Service;

import com.pp.grup.Entity.Board;
import com.pp.grup.Entity.Comment;
import com.pp.grup.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void write(Comment comment) throws Exception {
        comment.setCommentDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    public boolean deleteComment(Integer id, String username) {
        Comment comment = commentRepository.findById(id).get();
        if (comment != null && comment.getCommentWriter().equals(username)) {
            commentRepository.delete(comment);
            return true;
        } else {
            return false;
        }
    }
}

