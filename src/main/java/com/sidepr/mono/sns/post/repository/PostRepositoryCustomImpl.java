package com.sidepr.mono.sns.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidepr.mono.sns.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.sidepr.mono.sns.post.domain.QPost.post;
import static com.sidepr.mono.sns.post.domain.QPostTag.postTag;
import static com.sidepr.mono.sns.tag.domain.QTag.tag;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Post> findByTag(String tagString, Pageable pageable) {
        List<Post> content = searchPostComplex(tagString);
        Long count = getCount(tagString);

        return new PageImpl<>(content, pageable, count);
    }

    private List<Post> searchPostComplex(String tagString) {
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

    private Long getCount(String tagString) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.postTags, postTag)
                .where(postTag.tag.in(
                        select(tag)
                                .from(tag)
                                .where(tag.content.eq(tagString))
                ))
                .fetchCount();
    }
}
