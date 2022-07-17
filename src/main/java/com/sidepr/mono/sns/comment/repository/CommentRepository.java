package com.sidepr.mono.sns.comment.repository;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndIsDeletedFalse(Long id);

    Page<Comment> findByPostAndIsDeletedFalseAndParentNullOrderByCreatedDateDesc(Post post, Pageable pageable);
    List<Comment> findTop20ByPostAndIsDeletedFalseAndParentNullOrderByCreatedDateDesc(Post post);
}
