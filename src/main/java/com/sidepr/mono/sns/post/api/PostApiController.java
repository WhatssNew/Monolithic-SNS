package com.sidepr.mono.sns.post.api;

import com.sidepr.mono.sns.global.utils.ApiUtils;
import com.sidepr.mono.sns.post.dto.*;
import com.sidepr.mono.sns.post.service.PostService;
import com.sidepr.mono.sns.user.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<PostDetailResponse>> getPost(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(ApiUtils.success(postService.findPost(id)));
    }


    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiUtils.ApiResult<Long>> create(
            @AuthenticationPrincipal JwtAuthentication token,
            @RequestParam("postCreateRequest") PostCreateRequest postCreateRequest,
            @RequestPart("files") List<MultipartFile> files
    ){
        Long postId = postService.savePost(token.getId(), postCreateRequest, files);
        return ResponseEntity.ok(ApiUtils.success(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<Long>> delete(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("id") Long id
    ){
        Long postId = postService.deletePost(token.getId(), id);
        return ResponseEntity.ok(ApiUtils.success(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<PostUpdateResponse>> update(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("id") Long id,
            @RequestBody PostUpdateRequest postUpdateRequest,
            @RequestPart("files") List<MultipartFile> files
    ){
        PostUpdateResponse postUpdateResponse = postService.updatePost(
                token.getId(),
                id,
                postUpdateRequest,
                files
        );
        return ResponseEntity.ok(ApiUtils.success(postUpdateResponse));
    }

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<Page<PostListResponse>>> get(
            @AuthenticationPrincipal JwtAuthentication token,
            @PageableDefault(size = 20, sort = "createdDate") Pageable pageable
    ){
        Page<PostListResponse> posts = postService.findPosts(token.getId(), pageable);
        return ResponseEntity.ok(ApiUtils.success(posts));
    }

    // TODO 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<ApiUtils.ApiResult<Void>> saveLikePost(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("id") Long id
    ){
        postService.likePost(token.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/nolike")
    public ResponseEntity<ApiUtils.ApiResult<Void>> deleteLikePost(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("id") Long id
    ){
        postService.notLikePost(token.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
