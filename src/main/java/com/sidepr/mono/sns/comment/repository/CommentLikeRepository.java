package com.sidepr.mono.sns.comment.repository;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.comment.domain.CommentLike;
import com.sidepr.mono.sns.comment.domain.id.CommentLikeId;
import com.sidepr.mono.sns.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    Boolean existsByUserAndComment(User user, Comment comment);
}
