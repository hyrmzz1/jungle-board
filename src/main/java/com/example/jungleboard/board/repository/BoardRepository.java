package com.example.jungleboard.board.repository;

import com.example.jungleboard.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // <EntityClass, pk type>
}
