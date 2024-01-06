package com.example.jungleboard.board.dto;

import com.example.jungleboard.board.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

// Data Transfer Object
@Getter // Lombok이 제공하는 어노테이션
@Setter // 각각의 필드에 대해 getter, setter 만들어 줌
@ToString   // 필드값 확인할 때 사용
@NoArgsConstructor  // 기본 생성자 자동으로 만들어 줌
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동으로 만들어 줌
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    // Entity -> DTO 변환
    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }
}