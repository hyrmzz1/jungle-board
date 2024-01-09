package com.example.jungleboard.board.controller;

import com.example.jungleboard.board.dto.CommentDTO;
import com.example.jungleboard.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor    // 서비스 클래스 호출하기 위함
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")   // /comment/save
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {   // @ResponseBody: 반환된 문자열이 HTTP 응답 본문의 일부로 전송됨.
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {   // 작성 성공하면
            // 해당 게시글의 댓글 전체 가져와서 리턴
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK); // Entity는 바디와 헤더를 같이 다루는 객체. commentDTOList-바디, HttpStatus-헤더
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}