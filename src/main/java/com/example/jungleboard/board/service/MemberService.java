package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.MemberDTO;
import com.example.jungleboard.board.entity.MemberEntity;
import com.example.jungleboard.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void join(MemberDTO memberDTO) {
        /*
        * 1. dto -> entity 변환
        * 2. repository에 변환한 entity 저장
        * */
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO); // 1
        memberRepository.save(memberEntity);    // 2. insert 쿼리 자동 생성됨.
    }
}