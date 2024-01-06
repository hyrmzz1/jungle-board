package com.example.jungleboard.board.controller;

import com.example.jungleboard.board.dto.BoardDTO;
import com.example.jungleboard.board.repository.BoardRepository;
import com.example.jungleboard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}