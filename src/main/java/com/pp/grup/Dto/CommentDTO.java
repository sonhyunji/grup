package com.pp.grup.Dto;

import com.pp.grup.Entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {
    private Integer id;
    private String content;
    private Integer postId;
    private String commentWriter;
    private LocalDateTime commentDate;

    public static CommentDTO toCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setPostId(comment.getPostId());
        commentDTO.setCommentWriter(comment.getCommentWriter());
        commentDTO.setCommentDate(comment.getCommentDate());
        return commentDTO;
    }
}
