package com.example.jungleboard.board.service;

import com.example.jungleboard.board.dto.CommentDTO;
import com.example.jungleboard.board.entity.BaseEntity;
import com.example.jungleboard.board.entity.BoardEntity;
import com.example.jungleboard.board.entity.CommentEntity;
import com.example.jungleboard.board.repository.BoardRepository;
import com.example.jungleboard.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 컨트롤러로부터 DTO 받아오고, 리파지토리에 Entity로 넘겨줌.
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        // 부모 Entity (BoardEntity) 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId()); // commentDTO BoardId 필드에 boardId 들어있음
        if(optionalBoardEntity.isPresent()) {   // 부모 엔티티 조회된다면
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        }
        else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();  // 부모 entity 조회
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);   // 부모 entity를 parameter로

        // EntityList -> DTOList
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO); // 변환해서 commentDTOList에 추가
        }
        return commentDTOList;
    }
}
