package com.sidepr.mono.sns.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.sidepr.mono.sns.post.domain.QPost.post;
import static com.sidepr.mono.sns.post.domain.QPostTag.postTag;
import static com.sidepr.mono.sns.tag.domain.QTag.tag;
import static com.sidepr.mono.sns.user.domain.QFollow.follow;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Post> findByTag(String tagString, Pageable pageable) {
        List<Post> content = searchPostByTagString(tagString);
        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public Page<Post> findByFollowing(User user, Pageable pageable) {
        List<Post> content = searchPostByFollowingUser(user);
        return new PageImpl<>(content, pageable, content.size());
    }

    private List<Post> searchPostByFollowingUser(User user) {
        return queryFactory
                .selectFrom(post)
                .where(post.user.in(
                        select(follow.follower)
                                .from(follow)
                                .where(follow.followed.eq(user))
                ))
                .fetch();
    }

    private List<Post> searchPostByTagString(String tagString) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.postTags, postTag)
                .where(postTag.tag.in(
                                select(tag)
                                        .from(tag)
                                        .where(tag.content.eq(tagString))
                        ))
                .fetch();
    }
}
