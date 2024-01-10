package com.example.jungleboard.board.controller;

import com.example.jungleboard.board.dto.MemberDTO;
import com.example.jungleboard.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;  // 생성자 주입

    @GetMapping("/board/join")
    public String joinForm() { return "join"; } // join.html과 연결

    @PostMapping("/board/join")
    public String join(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.join");
        System.out.println("memberDTO = " + memberDTO);
        memberService.join(memberDTO);

        // DB에 가입 정보 삽입된 상태
        return "login"; // 가입 완료되면 로그인 페이지로
    }

    @GetMapping("/board/login")
    public String loginForm() {return "login";} // login.html과 연결

    @PostMapping("/board/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        System.out.println("memberDTO = " + memberDTO + ", session = " + session);
        if (loginResult != null) {  // 로그인 성공
            session.setAttribute("loginId", loginResult.getMemberId());
            return "index";    // paging.html로
        } else {    // 로그인 실패
            return "login"; // login.html로
        }
    }

    @GetMapping("/board/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}