package com.sidepr.mono.sns.comment.service;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.comment.domain.CommentLike;
import com.sidepr.mono.sns.comment.domain.CommentTagUser;
import com.sidepr.mono.sns.comment.dto.CommentCreateRequest;
import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import com.sidepr.mono.sns.comment.dto.CommentUpdateRequest;
import com.sidepr.mono.sns.comment.exception.InvalidCommentRequestException;
import com.sidepr.mono.sns.comment.exception.NotFoundCommentException;
import com.sidepr.mono.sns.comment.exception.NotPermittedCommentException;
import com.sidepr.mono.sns.comment.repository.CommentLikeRepository;
import com.sidepr.mono.sns.comment.repository.CommentRepository;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.repository.PostRepository;
import com.sidepr.mono.sns.user.domain.User;
import com.sidepr.mono.sns.user.exception.NotFoundUserException;
import com.sidepr.mono.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sidepr.mono.sns.global.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Value("${tag.user.pattern}")
    private String USER_TAG_PATTERN;

    @Transactional
    public Long saveComment(
            Long userId,
            Long postId,
            CommentCreateRequest commentCreateRequest
    ){
        User user = findActiveUser(userId);
        Post post = findActivePost(postId);
        List<String> tagUserNickname = getContentTagUser(commentCreateRequest.getContent());
        Comment parentComment = findActiveCommentOrNull(commentCreateRequest.getParentCommentId());
        Comment comment = commentCreateRequest.toEntity(user, post, parentComment);

        List<CommentTagUser> tagUser = tagUserNickname.stream()
                .map(this::findActiveUserByNickname)
                .map(CommentTagUser::new)
                .collect(Collectors.toList());

        tagUser.forEach(comment::addCommentTagUSer);

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

    @Transactional
    public void likeComment(Long userId, Long commentId) {
        User user = findActiveUser(userId);
        Comment comment = findActiveComment(commentId);
        isValidCommentLikeRequest(user, comment);

        commentLikeRepository.save(
                CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .build()
        );
    }

    @Transactional
    public void notLikePost(Long userId, Long commentId) {
        User user = findActiveUser(userId);
        Comment comment = findActiveComment(commentId);

        commentLikeRepository.delete(
                findUserCommentLike(user, comment)
        );
    }

    @Transactional(readOnly = true)
    public User findActiveUser(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
    }

    @Transactional(readOnly = true)
    public User findActiveUserByNickname(String nickname) {
        return userRepository.findByNicknameAndIsDeletedFalse(nickname)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
    }

    @Transactional(readOnly = true)
    public Post findActivePost(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
    }

    @Transactional(readOnly = true)
    public Comment findActiveMyComment(Long userId, Long commentId){
        User user = findActiveUser(userId);
        Comment comment = findActiveComment(commentId);
        if(comment.getUser() != user) throw new NotPermittedCommentException(NOT_PERMITTED_RESOURCE_ERROR);
        return comment;
    }


    @Transactional(readOnly = true)
    public Page<CommentDetailResponse> findComments(Long postId, Pageable pageable){
        Post post = findActivePost(postId);
        Page<Comment> comments = commentRepository.findByPostAndIsDeletedFalseAndParentNullOrderByCreatedDateDesc(post, pageable);
        return comments.map(Comment::toCommentDetailResponse);
    }

    private Comment findActiveComment(Long commentId) {
        return commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new NotFoundCommentException(NOT_FOUND_RESOURCE_ERROR));
    }

    private Comment findActiveCommentOrNull(Long commentId){
        if(Objects.isNull(commentId)) return null;
        else return findActiveComment(commentId);
    }

    private void isValidCommentLikeRequest(User user, Comment comment) {
        if(commentLikeRepository.existsByUserAndComment(user, comment)){
            throw new InvalidCommentRequestException(NOT_VALID_REQUEST_ERROR);
        }
    }

    private CommentLike findUserCommentLike(User user, Comment comment) {
        return commentLikeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new InvalidCommentRequestException(NOT_VALID_REQUEST_ERROR));
    }

    private List<String> getContentTagUser(String source) {
        Pattern tagPattern = Pattern.compile(USER_TAG_PATTERN);
        Matcher tagMatcher = tagPattern.matcher(source);

        return tagMatcher.results()
                .map(MatchResult::group)
                .map(s -> s.substring(1))
                .collect(Collectors.toList());
    }
}
