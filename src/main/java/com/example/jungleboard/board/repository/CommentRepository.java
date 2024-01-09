package com.example.jungleboard.board.repository;

import com.example.jungleboard.board.entity.BoardEntity;
import com.example.jungleboard.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // findAllByBoardEntity: select * from comment_table where board_id=?
    // OrderByIdDesc: order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}