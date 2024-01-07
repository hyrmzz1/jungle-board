package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.BoardDTO;
import com.example.jungleboard.board.entity.BoardEntity;
import com.example.jungleboard.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();  // Repository로부터 list 형태의 entity 받음
        List<BoardDTO> boardDTOList = new ArrayList<>();    // entity -> DTO 변환해서 컨트롤러에 리턴해줘야 함.

        // entity -> DTO
        for (BoardEntity boardEntity: boardEntityList) {    // boardEntityList 요소 하나씩
            boardDTOList.add(BoardDTO.toBoardDTO((boardEntity)));   // DTO 객체로 변환하고 DTO List에 추가
        }

        return boardDTOList;
    }

    @Transactional  // 추가한 메서드 사용한다면 이 어노테이션 호출
    public void updateHits(Long id) {
        boardRepository.updateHits(id); // updateHits => JPA 기본 제공 메서드 아니고, 추가한 메서드 (쿼리,....)
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);   // entity -> DTO 변환
            return boardDTO;
        } else {return null;}
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity); // Entity에 id 값 전달되면 update로 처리
        return findById((boardDTO.getId()));    // 게시글 상세 정보 컨트롤러 쪽으로 넘겨줌
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
