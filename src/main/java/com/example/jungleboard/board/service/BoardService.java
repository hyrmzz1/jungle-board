package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.BoardDTO;
import com.example.jungleboard.board.entity.BoardEntity;
import com.example.jungleboard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 컨트롤러로부터 DTO로 받아오고, 리파지토리로는 Entity로 넘겨줌.
// DTO -> Entity (Entity Class에서)
// Entity -> DTO (DTO Class에서)
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;  // 생성자 주입 방식
    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);   // 메서드 호출 결과 변수에 담기
        boardRepository.save(boardEntity);    // 변수 save 메서드에 넘겨주면 insert query 나가게 됨
    }
}
