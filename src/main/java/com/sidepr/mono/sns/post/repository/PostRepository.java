package com.sidepr.mono.sns.post.repository;

import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> getByUserAndIsDeletedFalse(User user);

    Optional<Post> findByIdAndIsDeletedFalse(Long id);

    List<Post> findAllByUser(User user);

    // TODO Following에 대한 post 검색

}
