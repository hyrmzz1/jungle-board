package com.example.jungleboard.board.repository;

import com.example.jungleboard.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {   // <다룰 entity class, entity class의 pk type>
    // select * from member_table where member_id=?
    Optional<MemberEntity> findByMemberId(String memberId); // 위의 쿼리문 자동 생성
}