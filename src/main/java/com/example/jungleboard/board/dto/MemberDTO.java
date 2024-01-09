package com.example.jungleboard.board.dto;

import com.example.jungleboard.board.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@ToString   // DTO 객체가 가지고있는 필드 값 출력할 때 사용
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동 생성
public class MemberDTO {
    private Long code;  // 멤버 고유 코드 (pk)
    private String memberId;
    private String memberPassword;

    // entity -> DTO 변환
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setCode(memberEntity.getCode());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        return memberDTO;
    }
}