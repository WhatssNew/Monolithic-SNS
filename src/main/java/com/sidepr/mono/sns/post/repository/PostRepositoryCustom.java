package com.sidepr.mono.sns.post.repository;

import com.sidepr.mono.sns.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findByTag(String tagString, Pageable pageable);
}
