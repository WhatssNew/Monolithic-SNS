package com.sidepr.mono.sns.post.domain;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import com.sidepr.mono.sns.global.BaseTimeEntity;
import com.sidepr.mono.sns.post.dto.PostDetailResponse;
import com.sidepr.mono.sns.post.dto.PostListResponse;
import com.sidepr.mono.sns.post.dto.PostUpdateRequest;
import com.sidepr.mono.sns.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT default false")
    private Boolean isDeleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();


    public Post(User user, String content) {
        this.user = user;
        this.content = content;
        images = new ArrayList<>();
        comments = new ArrayList<>();
        postLikes = new ArrayList<>();
        postTags = new ArrayList<>();
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.postComment(this);
    }

    public void addPostLike(PostLike postLike){
        this.postLikes.add(postLike);
        postLike.likePost(this);
    }

    public void addPostTag(PostTag postTag){
        this.getPostTags().add(postTag);
        postTag.specifyPostTag(this);
    }

    public void addImage(PostImage postImage){
        this.getImages().add(postImage);
        postImage.postImage(this);
    }

    public void deletePost(){
        this.isDeleted = true;
    }

    public void updatePost(PostUpdateRequest postUpdateRequest){
        this.content = content;
    }

    public PostDetailResponse toPostDetailResponse(List<CommentDetailResponse> comments) {
        return PostDetailResponse.builder()
                .id(id)
                .userId(user.getEmail())
                .isDeleted(isDeleted)
                .content(content)
                .images(
                        getImages().stream()
                                .map(PostImage::getImage)
                                .collect(Collectors.toList())
                )
                .comments(comments)
                .likes((long) getPostLikes().size())
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }

    public PostListResponse toPostListResponse(){
        return PostListResponse.builder()
                .id(id)
                .userId(user.getId())
                .isDeleted(isDeleted)
                .content(content)
                .images(
                        getImages().stream()
                                .map(PostImage::getImage)
                                .collect(Collectors.toList())
                )
                .likes((long) getPostLikes().size())
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }
}
