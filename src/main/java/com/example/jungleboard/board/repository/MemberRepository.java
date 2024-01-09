package com.example.jungleboard.board.repository;

import com.example.jungleboard.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {   // <다룰 entity class, entity class의 pk type>
}