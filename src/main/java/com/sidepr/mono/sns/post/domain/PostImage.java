package com.sidepr.mono.sns.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PostImage {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_image_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String image;

    public void postImage(Post post){
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostImage postImage = (PostImage) o;
        return Objects.equals(getId(), postImage.getId()) && Objects.equals(getPost(), postImage.getPost()) && Objects.equals(getImage(), postImage.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPost(), getImage());
    }
}
