package com.pp.grup.Service;

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

    public void commentDelete(Integer commentId){
        commentRepository.deleteById(commentId);
    }
}

