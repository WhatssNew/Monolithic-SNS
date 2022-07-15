package com.sidepr.mono.sns.post.repository;

import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.domain.PostLike;
import com.sidepr.mono.sns.post.domain.id.PostLikeId;
import com.sidepr.mono.sns.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    Boolean existsByUserAndPost(User user, Post post);
}
