package com.sidepr.mono.sns.comment.service;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.comment.dto.CommentCreateRequest;
import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import com.sidepr.mono.sns.comment.dto.CommentUpdateRequest;
import com.sidepr.mono.sns.comment.exception.NotFoundCommentException;
import com.sidepr.mono.sns.comment.exception.NotPermittedCommentException;
import com.sidepr.mono.sns.comment.repository.CommentRepository;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.repository.PostRepository;
import com.sidepr.mono.sns.user.domain.User;
import com.sidepr.mono.sns.user.exception.NotFoundUserException;
import com.sidepr.mono.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sidepr.mono.sns.global.error.ErrorCode.NOT_FOUND_RESOURCE_ERROR;
import static com.sidepr.mono.sns.global.error.ErrorCode.NOT_PERMITTED_RESOURCE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveComment(
            Long userId,
            Long postId,
            CommentCreateRequest commentCreateRequest
    ){
        User user = findActiveUser(userId);
        Post post = findActivePost(postId);
        Comment comment = commentCreateRequest.toEntity(user, post);

        return commentRepository.save(comment).getId();
    }

    @Transactional
    public Long deleteComment(Long userId, Long commentId) {
        Comment comment = findActiveMyComment(userId, commentId);
        comment.deleteComment();
        return comment.getId();
    }

    @Transactional
    public Long updateComment(Long userId, Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = findActiveMyComment(userId, commentId);
        comment.updateComment(commentUpdateRequest);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public User findActiveUser(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
    }

    @Transactional(readOnly = true)
    public Post findActivePost(Long postId) {
        Post post =  postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
        return post;
    }

    @Transactional(readOnly = true)
    public Comment findActiveMyComment(Long userId, Long commentId){
        User user = findActiveUser(userId);
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new NotFoundCommentException(NOT_FOUND_RESOURCE_ERROR));
        if(comment.getUser() != user) throw new NotPermittedCommentException(NOT_PERMITTED_RESOURCE_ERROR);
        return comment;
    }

    @Transactional(readOnly = true)
    public Page<CommentDetailResponse> findComments(Long postId, Pageable pageable){
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(Comment::toCommentDetailResponse);
    }
}
