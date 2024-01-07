package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.BoardDTO;
import com.example.jungleboard.board.entity.BoardEntity;
import com.example.jungleboard.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;    // page 위치에 있는 값은 0부터 시작하므로 실제 사용자 요청 페이지 값에서 1 빼주기
        int pageLimit = 3;  // 한 페이지에 보여줄 글 개수
        // DB로부터 페이징 처리된 데이터 가져옴
        // Entity로 담겨있으므로 DTO로 변환해줘야 함.
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));   // id 엔티티 기준 내림차순 정렬

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호 (화면상에서 1페이지 요청했다면 0으로 요청됨)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // Entity -> DTO 변환
        // BoardDTO에 객체(id, writer, title, hits, createdTime) 담기 위해 DTO 생성자 먼저 호출 (BoardDTO에)
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;   // 컨트롤러로 리턴
    }
}