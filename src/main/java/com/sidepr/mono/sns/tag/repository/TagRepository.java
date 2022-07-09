package com.sidepr.mono.sns.tag.repository;

import com.sidepr.mono.sns.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

    Optional<Tag> findByContent(String content);
}
