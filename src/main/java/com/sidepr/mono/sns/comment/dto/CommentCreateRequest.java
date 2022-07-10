package com.sidepr.mono.sns.comment.dto;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CommentCreateRequest {

    // TODO prevComment 처리해야 함
    private Long prevCommentId;
    private String content;

    public Comment toEntity(User user, Post post){
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(content)
                .isDeleted(false)
                .build();
        return comment;
    }
}
