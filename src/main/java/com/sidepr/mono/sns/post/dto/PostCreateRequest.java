package com.sidepr.mono.sns.post.dto;

import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PostCreateRequest {

    private String content;

    public Post toEntity(User user){
        Post post = Post.builder()
                .user(user)
                .content(this.content)
                .isDeleted(false)
                .build();
        return post;
    }
}
