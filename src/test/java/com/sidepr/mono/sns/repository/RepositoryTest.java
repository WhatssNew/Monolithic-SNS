package com.sidepr.mono.sns.repository;

import com.sidepr.mono.sns.comment.repository.CommentLikeRepository;
import com.sidepr.mono.sns.comment.repository.CommentRepository;
import com.sidepr.mono.sns.post.repository.PostLikeRepository;
import com.sidepr.mono.sns.post.repository.PostRepository;
import com.sidepr.mono.sns.post.repository.PostTagRepository;
import com.sidepr.mono.sns.tag.repository.TagRepository;
import com.sidepr.mono.sns.user.repository.FollowRepository;
import com.sidepr.mono.sns.user.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;
}
