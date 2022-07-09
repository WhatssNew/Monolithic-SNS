package com.sidepr.mono.sns.post.domain;

import com.sidepr.mono.sns.post.domain.id.PostTagId;
import com.sidepr.mono.sns.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@IdClass(PostTagId.class)
@NoArgsConstructor(access = PROTECTED)
public class PostTag {

    @Id @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Id @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public PostTag(Tag tag) {
        this.tag = tag;
    }

    public void specifyPostTag(Post post){
        this.post = post;
    }
}
