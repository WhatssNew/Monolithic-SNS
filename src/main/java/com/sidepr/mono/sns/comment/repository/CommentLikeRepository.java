package com.sidepr.mono.sns.comment.repository;

import com.sidepr.mono.sns.comment.domain.CommentLike;
import com.sidepr.mono.sns.comment.domain.id.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
}
