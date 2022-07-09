package com.sidepr.mono.sns.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponse {
    //TODO tags 처리 안됨
    private Long id;
    private String userId;
    private String content;
    private Boolean isDeleted;
    private List<String> images;
    private List<String> tags;
    //TODO Response용 comment 만들어야 함
    private List<String> comments;
    //TODO Response용 like 만들어야 함
    private List<String> likes;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostDetailResponse(
            Long id, String userId, String content, Boolean isDeleted,
            List<String> images, List<String> tags, List<String> comments,
            List<String> likes, LocalDateTime createdDate, LocalDateTime lastModifiedDate
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
