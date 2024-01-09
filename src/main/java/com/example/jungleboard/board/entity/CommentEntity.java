package com.example.jungleboard.board.entity;

import com.example.jungleboard.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
// DB의 테이블 역할을 하는 클래스
public class CommentEntity extends BaseEntity {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 댓글 id

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 500, nullable = false)
    private String commentContents;

    // Board:Comment = 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    // 댓글 생성 시간 => 상속

    // DTO -> Entity 변환
    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}