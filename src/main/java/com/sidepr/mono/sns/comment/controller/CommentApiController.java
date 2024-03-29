package com.sidepr.mono.sns.comment.controller;

import com.sidepr.mono.sns.comment.dto.CommentCreateRequest;
import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import com.sidepr.mono.sns.comment.dto.CommentUpdateRequest;
import com.sidepr.mono.sns.comment.service.CommentService;
import com.sidepr.mono.sns.global.utils.ApiUtils;
import com.sidepr.mono.sns.user.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{postId}/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiUtils.ApiResult<Long>> create(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("postId") Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ){
        Long commentId = commentService.saveComment(token.getId(), postId, commentCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiUtils.success(commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiUtils.ApiResult<Long>> delete(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        Long deleteCommentId = commentService.deleteComment(token.getId(), commentId);
        return ResponseEntity.ok(ApiUtils.success(deleteCommentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiUtils.ApiResult<Long>> update(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ){
        Long updatedCommentId = commentService.updateComment(
                token.getId(),
                commentId,
                commentUpdateRequest
        );
        return ResponseEntity.ok(ApiUtils.success(updatedCommentId));
    }

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<Page<CommentDetailResponse>>> get(
            @PathVariable("postId") Long postId,
            @PageableDefault(size = 20) Pageable pageable
    ){
        Page<CommentDetailResponse> comments = commentService.findComments(postId, pageable);
        return ResponseEntity.ok(ApiUtils.success(comments));
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<ApiUtils.ApiResult<Void>> saveLikeComment(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("commentId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        commentService.likeComment(token.getId(), commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{commentId}/nolike")
    public ResponseEntity<ApiUtils.ApiResult<Void>> deleteLikeComment(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        commentService.notLikePost(token.getId(), commentId);
        return ResponseEntity.noContent().build();
    }
}
