package com.pp.grup.Service;

import com.pp.grup.Entity.Board;
import com.pp.grup.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws Exception{ // 오류처리 throws Exception

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files"; // 저장경로 지정

        UUID uuid = UUID.randomUUID(); // 파일 이름에 붙일 랜덤이름 생성

        String fileName = uuid + "_" + file.getOriginalFilename(); // 랜덤이름을 파일네임 앞에 붙인 후 _ 그리고 원래 파일이름으로 파일이름 생성

        File saveFile = new File(projectPath, fileName); // 파일을 생성해줄건데, projectPath에 담기고, name이름으로 담긴다는 의미

        file.transferTo(saveFile); // 예외처리 필요하기에 throws를 이용, 해주기

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board); // 이렇게 생성한 서비스는 다시 컨트롤러에서 사용할 것
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

}
