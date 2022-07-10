package com.sidepr.mono.sns.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CommentDetailResponse {

    private Long id;
    private Long userId;
    private String content;
    // TODO dto 만들어서 좋아요한 사용자 정보도 넘
    private Long commentLike;

    @Builder
    public CommentDetailResponse(
            Long id,
            Long userId,
            String content,
            Long commentLike
    ){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.commentLike = commentLike;
    }
}
