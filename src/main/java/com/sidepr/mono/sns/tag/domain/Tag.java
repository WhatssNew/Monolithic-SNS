package com.sidepr.mono.sns.tag.domain;

import com.sidepr.mono.sns.global.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Long id;

    @Column(length = 45, nullable = false, unique = true)
    private String content;

    public Tag(String content) {
        this.content = content;
    }
}
