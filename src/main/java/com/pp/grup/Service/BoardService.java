package com.pp.grup.Service;

import com.pp.grup.Entity.Board;
import com.pp.grup.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws Exception {
        // 파일이 존재하는 경우
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }

        board.setBoardDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    public void modify(Board board, @RequestParam(required = false) MultipartFile file) throws Exception {
        // 파일이 존재하는 경우
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);

            // 파일 저장
            try {
                file.transferTo(saveFile);
                board.setFilename(fileName);
                board.setFilepath("/files/" + fileName);
            } catch (IOException e) {
                throw new Exception("파일 업로드에 실패하였습니다.");
            }
        }

        boardRepository.save(board);
    }

    public Page<Board> boardlist(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Board boardview(Integer id){
        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

    public List<Board> getMyPosts(String memberName) {
        return boardRepository.findByMemberName(memberName);
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public boolean deleteBoard(Integer id, String username) {
        Board board = boardRepository.findById(id).get();
        if (board != null && board.getMemberName().equals(username)) {
            boardRepository.delete(board);
            return true;
        } else {
            return false;
        }
    }

    public boolean modifyBoard(Integer id, String username) {
        Board board = boardRepository.findById(id).get();
        if (board != null && board.getMemberName().equals(username)) {
            return true;
        } else {
            return false;
        }
    }

    public Board getBoardById(Integer id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        return boardOptional.orElse(null); // orElse(null)를 사용하여 값이 없을 때는 null을 반환하도록 처리
    }

    public List<Board> getTopLikedBoardsWithinMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1); // 한 달 전 날짜
        LocalDateTime startDateTime = oneMonthAgo.atStartOfDay(); // LocalDate를 LocalDateTime으로 변환
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
        return boardRepository.findTop10LikedBoardsWithinMonth(startDateTime, pageable);
    }

    public List<Board> getTopViewedBoardsWithinMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1); // 한 달 전 날짜
        LocalDateTime startDateTime = oneMonthAgo.atStartOfDay(); // LocalDate를 LocalDateTime으로 변환
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "boardView"));
        return boardRepository.findTop10ViewedBoardsWithinMonth(startDateTime, pageable);
    }

}
