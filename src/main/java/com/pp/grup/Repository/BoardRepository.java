package com.pp.grup.Repository;

import com.pp.grup.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    List<Board> findByMemberName(String memberName);

    @Query("SELECT b FROM Board b WHERE b.boardDate >= :startDate ORDER BY b.boardView DESC")
    List<Board> findTop10ViewedBoardsWithinMonth(LocalDateTime startDate, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardDate >= :startDate ORDER BY b.likeCount DESC")
    List<Board> findTop10LikedBoardsWithinMonth(LocalDateTime startDate, Pageable pageable);
}
