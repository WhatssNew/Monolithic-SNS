package com.sidepr.mono.sns.post.dto;

//TODO list용 dto 만들기

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class PostListResponse {
    private Long id;
    private Long userId;
    private String content;
    private Boolean isDeleted;
    private List<String> images;
    private List<String> tags;
    private List<String> likes;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-HH-dd'T'HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostListResponse(
            Long id, Long userId, String content, Boolean isDeleted,
            List<String> images, List<String> tags, List<String> likes,
            LocalDateTime createdDate, LocalDateTime lastModifiedDate
    ){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isDeleted = isDeleted;
        this.images = images;
        this.tags = tags;
        this.likes = likes;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
