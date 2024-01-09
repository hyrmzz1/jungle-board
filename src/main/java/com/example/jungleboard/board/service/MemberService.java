package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.MemberDTO;
import com.example.jungleboard.board.entity.MemberEntity;
import com.example.jungleboard.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        * 1. DB에 입력된 id가 있는지 조회
        * 2. 입력된 pw와 DB에 저장된 (id에 해당하는) pw가 같은지 조회
        * */
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());
        if(byMemberId.isPresent()) {    // 입력된 id가 DB에 존재한다면
            // id의 pw와 입력된 pw 일치여부 확인
            MemberEntity memberEntity = byMemberId.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) { // pw 일치
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);    // entity -> DTO 변환
                return dto;
            } else {    // pw 불일치
                return null;
            }
        } else {    // DB에 id 없음
            return null;
        }
    }
}