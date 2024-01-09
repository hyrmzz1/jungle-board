package com.example.jungleboard.board.entity;

import com.example.jungleboard.board.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id // pk. 입력값은 pk로 두지 말 것
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long code;  // 회원 코드

    @Column(unique = true)
    private String memberId;

    @Column
    private String memberPassword;

    // DTO -> Entity 변환
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setCode(memberDTO.getCode());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }
}