package com.example.jungleboard.board.repository;

import com.example.jungleboard.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // <EntityClass, pk type>
    @Modifying  // update, delete 쿼리 실행할 때 붙이기
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id") // 맨 뒤의 id는 Param의 id와 같음
    void updateHits(@Param("id") Long id);
}