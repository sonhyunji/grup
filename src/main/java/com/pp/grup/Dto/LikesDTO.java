package com.pp.grup.Dto;

import com.pp.grup.Entity.Likes;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikesDTO {
    private Integer likeId;
    private Integer boardId;
    private String userEmail;
    private LocalDateTime likeDate;

    public static LikesDTO toLikeDTO(Likes likes){
        LikesDTO likesDTO = new LikesDTO();
        likesDTO.setLikeId(likes.getLikeId());
        likesDTO.setBoardId(likes.getBoardId());
        likesDTO.setUserEmail(likes.getUserEmail());
        likesDTO.setLikeDate(likes.getLikeDate());
        return likesDTO;
    }

    public LikesDTO(String userEmail, Integer boardId) {
        this.userEmail = userEmail;
        this.boardId = boardId;
    }
}
