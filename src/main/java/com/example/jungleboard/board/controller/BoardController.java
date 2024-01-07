package com.example.jungleboard.board.controller;

//import ch.qos.logback.core.model.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import com.example.jungleboard.board.dto.BoardDTO;
import com.example.jungleboard.board.repository.BoardRepository;
import com.example.jungleboard.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor    // 서비스 클래스 호출하기 위함
@RequestMapping("/board")   // 대표 주소
public class BoardController {
    private final BoardService boardService;    // 생성자 주입 방식으로 의존성 주입

    @GetMapping("/save")    // 하위 주소 (입력된 url과 매핑값이 일치하는 메서드가 호출됨.)
    // get method로 받는 '/save'
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")   // post method로 받는 '/save' (@GetMapping("/save") 중복은 안됨. - 같은 메소드와 같은 주소 여러번 X)
    public String save(@ModelAttribute BoardDTO boardDTO) { // 데이터(입력값) 세트인 DTO 전체 받기
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    @GetMapping("/")
    // DB에서 전체 게시글 데이터 가져와 list.html에 보여줌.
    public String findAll(Model model) {    // 데이터를 가져와야할 땐 model 객체 사용
        List<BoardDTO> boardDTOList = boardService.findAll();   // BoardDTO 객체가 담겨있는 List 선언, 전체 글 가져옴
        model.addAttribute("boardList", boardDTOList);  // 가져온 데이터를 model 객체에 담아감.
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,  // 경로상에 있는 값 받아오기 위해 @PathVariable 사용
                            @PageableDefault(page=1) Pageable pageable) {    // 페이지 요청 없는 경우
        boardService.updateHits(id);    // 해당 게시글의 조회수 하나 올리고
        BoardDTO boardDTO = boardService.findById(id);  // 게시글 데이터 가져와서
        model.addAttribute("board", boardDTO);  // model에 데이터 넣어주고
        model.addAttribute("page", pageable.getPageNumber());   // pageNumber 담아서 화면에 출력하기 위함.
        return "detail";    // detail.html에 출력
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);  // BoardDTO 개체를 model에 추가
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {  // 클라이언트 측에서 전송된 form data를 BoardDTO에 바인딩
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board); // 수정된 BoardDTO 개체가 속성명이 board인 model에 추가됨.
        return "detail"; // 수정이 반영된 상세페이지 보기
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {   // 경로상에 있는 값 받아오기 위해 @PathVariable 사용
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/paging")
    // /board/paging?page=1 에서 page=1을 pageable이 가져옴.
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {   // 페이지값 url에 없으면 1로 설정
        Page<BoardDTO> boardList = boardService.paging(pageable);

        // paging()에 int pageLimit = 3; 로 초기화된 상태
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
//        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages());    // 마지막 페이지가 blockLimit의 배수가 아닌 경우 고려

        // 위의 세 변수(blockLimit, startPage, endPage) 가지고 paging.html으로
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}