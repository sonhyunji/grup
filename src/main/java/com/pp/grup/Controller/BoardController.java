package com.pp.grup.Controller;

import com.pp.grup.Entity.Board;
import com.pp.grup.Entity.Comment;
import com.pp.grup.Service.BoardService;
import com.pp.grup.Service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @GetMapping("/board/write")
    public String boardWriteForm(HttpSession session) {
        String myEmail= (String) session.getAttribute("loginEmail");
        if (myEmail == null) {
            return "redirect:/PlantsPlanet/login"; // 로그인 페이지 URL로 리다이렉트
        }
        return "boardwrite";
    }

    @PostMapping("/board/writedo")
    public String boardWritePro(Board board, Model model, MultipartFile file, HttpSession session) throws Exception {
        String memberName = (String) session.getAttribute("loginName");
        model.addAttribute("memberName", memberName);

        board.setMemberName(memberName); // board 객체에 memberName 설정
        board.setBoardView(0);
        board.setLikeCount(0);

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }


    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable,
                            String searchKeyword){ // 데이터를 담아 페이지로 보내기 위해 Model 자료형을 인자로 , 검색할 때 (searchKeyword가 있을 떄) 안할 때 구분해 if문 사용

        Page<Board> list = null;

        if(searchKeyword != null){
            list = boardService.boardSearchList(searchKeyword, pageable);
        } else {
            list = boardService.boardlist(pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; // 현재 페이지를 가져옴 , 0에서 시작하기에 처리를 위해 + 1
        int startPage = Math.max(nowPage - 4, 1); // Math.max(a, b) -- a 와 b 중 큰 값을 반환 --> 그냥 nowPAge - 4만 하면 nowpage가 1인 경우 -3도 가능하기에 이를 방지하기 위함
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // totalPage보다 크면 안되기에 두개 중 최소값 반환하는 Math.min을 사용

        model.addAttribute("list", list ); // boardService에서 생성한 boardlist메소드를 통해 list가 반환되는데 해당 list를 "list"라는 이름으로 넘겨주겠다는 것(html에 나올 수 있게)
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardview(Model model, @RequestParam Integer id){

        Board board = boardService.boardview(id);
        List<Comment> comments = commentService.getCommentsByPostId(id);

        board.setBoardView(board.getBoardView() + 1);
        boardService.saveBoard(board); // 변경된 조회수를 저장

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        return "boardview";
    }

    @GetMapping("/board/delete/{id}")
    public String deleteBoard(@PathVariable Integer id, HttpSession session) {
        String username = (String) session.getAttribute("loginName");

        // 게시물 삭제 로직
        boolean success = boardService.deleteBoard(id, username);

        if (success) {
            return "redirect:/board/list"; // 삭제 처리 후 리스트로 이동 (리다이렉트)
        } else {
            return "redirect:/board/view?id=" + id; // 삭제할 수 없는 경우, 해당 게시물 상세 페이지로 이동 (리다이렉트)
        }
    }


    @GetMapping("/board/modify/{id}") //id는 path variable(주소 변수, 경로 변수)
    public String boardModify(@PathVariable("id") Integer id, Model model, HttpSession session){
        String username = (String) session.getAttribute("loginName");

        boolean success = boardService.modifyBoard(id, username);

        if (success) {
            model.addAttribute("board", boardService.boardview(id));
            return "boardModify";
        } else {
            return "redirect:/board/view?id=" + id; // 삭제할 수 없는 경우, 해당 게시물 상세 페이지로 이동 (리다이렉트)
        }
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, @RequestParam("title") String title,
                              @RequestParam("content") String content, Board board, MultipartFile file) throws Exception {
        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(title);
        boardTemp.setContent(content);

        boardService.modify(boardTemp, file);

        return "redirect:/board/list";
    }
}
