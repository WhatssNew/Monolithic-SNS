package com.sidepr.mono.sns.tag.repository;

import com.sidepr.mono.sns.tag.domain.Tag;

public interface TagCustomRepository {

    Tag findByContentOrCreate(String content);
}
