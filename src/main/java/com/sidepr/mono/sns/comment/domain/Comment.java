package com.sidepr.mono.sns.comment.domain;

import com.sidepr.mono.sns.comment.dto.CommentDetailResponse;
import com.sidepr.mono.sns.comment.dto.CommentUpdateRequest;
import com.sidepr.mono.sns.global.BaseTimeEntity;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id", updatable = false)
    private Comment parent;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT default false")
    private boolean isDeleted;

    @Builder.Default
    @OneToMany(mappedBy = "comment", cascade = ALL, fetch = LAZY)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "comment", cascade = ALL, fetch = LAZY)
    private List<CommentTagUser> commentTagUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = LAZY)
    private List<Comment> children = new ArrayList<>();

    public void deleteComment(){
        this.isDeleted = true;
    }

    public void updateComment(CommentUpdateRequest commentUpdateRequest){
        this.content = commentUpdateRequest.getContent();
    }

    public void postComment(Post post){
        this.post = post;
    }

    public void addParentComment(Comment comment){
        comment.getChildren().add(this);
        this.parent = comment;
    }

    public void addCommentLike(CommentLike commentLike) {
        this.commentLikes.add(commentLike);
        commentLike.likeComment(this);
    }

    private Long getPrevCommentIdOrNull(){
        if(Objects.isNull(parent)) return null;
        else return parent.getId();
    }

    public CommentDetailResponse toCommentDetailResponse(){
        return CommentDetailResponse.builder()
                .id(id)
                .userId(user.getId())
                .content(content)
                .parentId(getPrevCommentIdOrNull())
                .commentLike((long) commentLikes.size())
                .build();
    }
}
