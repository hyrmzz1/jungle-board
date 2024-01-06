package com.example.jungleboard.board.controller;

//import ch.qos.logback.core.model.Model;
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
    public String findById(@PathVariable Long id, Model model) {    // 경로상에 있는 값 받아오기 위해 @PathVariable 사용
        boardService.updateHits(id);    // 해당 게시글의 조회수 하나 올리고
        BoardDTO boardDTO = boardService.findById(id);  // 게시글 데이터 가져와서
        model.addAttribute("board", boardDTO);  // model에 데이터 넣어주고
        return "detail";    // detail.html에 출력
    }
}