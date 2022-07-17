package com.sidepr.mono.sns.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostDetailResponse {
    //TODO tags 처리 안됨
    private Long id;
    private String userId;
    private String content;
    private Boolean isDeleted;
    private List<String> images;
    private List<String> tags;
    private List<CommentDetailResponse> comments;
    private Long likes;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostDetailResponse(
            Long id, String userId, String content, Boolean isDeleted,
            List<String> images, List<String> tags, List<CommentDetailResponse> comments,
            Long likes, LocalDateTime createdDate, LocalDateTime lastModifiedDate
    ){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isDeleted = isDeleted;
        this.images = images;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
