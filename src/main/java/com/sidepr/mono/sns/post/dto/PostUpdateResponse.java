package com.sidepr.mono.sns.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PostUpdateResponse {

    //TODO tags 처리 안됨
    private Long id;
    private String userId;
    private String content;
    private Boolean isDeleted;
    private List<String> images;
    private List<String> tags;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXX")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXX")
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostUpdateResponse(
            Long id, String userId, String content, Boolean isDeleted,  List<String> images,
            List<String> tags, LocalDateTime createdDate, LocalDateTime lastModifiedDate
    ){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isDeleted = isDeleted;
        this.images = images;
        this.tags = tags;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
