package com.sidepr.mono.sns.service;

import com.sidepr.mono.sns.post.dto.PostListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest extends ServiceTest{

    private Pageable pageable(){
        return PageRequest.of(0, 20);
    }

    @Test
    @DisplayName("팔로잉 하는 유저가 작성한 포스트 검색")
    void findPostByFollower() {
        List<PostListResponse> posts = postService.findPosts(eachFollow.getId(), pageable()).getContent();
        assertThat(posts).extracting("userId").contains(myAccount.getId());

        posts = postService.findPosts(myFollower.getId(), pageable()).getContent();
        assertThat(posts).extracting("userId").contains(myAccount.getId());
    }

    @Test
    @DisplayName("팔로우 하지 않는 유저가 작성한 포스트 검색")
    void findPostByNotFollower(){
        List<PostListResponse> posts = postService.findPosts(normalUser.getId(), pageable()).getContent();
        assertThat(posts).extracting("userId").doesNotContain(myAccount.getId());

        posts = postService.findPosts(myFollowing.getId(), pageable()).getContent();
        assertThat(posts).extracting("userId").doesNotContain(myAccount.getId());
    }
}
