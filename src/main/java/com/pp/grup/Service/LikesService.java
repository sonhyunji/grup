package com.pp.grup.Service;

import com.pp.grup.Entity.Likes;
import com.pp.grup.Repository.LikesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikesService {

    public final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }


    public void write(Likes likes){
        likes.setLikeDate(LocalDateTime.now());
        likesRepository.save(likes);
    }

    public void delete(Integer postId, String userEmail){
        Likes likes = likesRepository.findByUserEmailAndBoardId(userEmail, postId);
        likesRepository.delete(likes);
    }

    public boolean existsByUserEmailAndBoardId(String memberEmail, Integer postId) {
        Boolean likes = likesRepository.existsByUserEmailAndBoardId(memberEmail, postId);
        return likes;
    }
}
