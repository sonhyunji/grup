package com.pp.grup.Repository;

import com.pp.grup.Entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
    Likes findByUserEmailAndBoardId(String userEmail, Integer postId);

    Boolean existsByUserEmailAndBoardId(String memberEmail, Integer postId);
}
