package com.sidepr.mono.sns;

import com.sidepr.mono.sns.comment.repository.CommentLikeRepository;
import com.sidepr.mono.sns.comment.repository.CommentRepository;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.domain.PostTag;
import com.sidepr.mono.sns.post.repository.PostLikeRepository;
import com.sidepr.mono.sns.post.repository.PostRepository;
import com.sidepr.mono.sns.post.repository.PostTagRepository;
import com.sidepr.mono.sns.tag.domain.Tag;
import com.sidepr.mono.sns.tag.repository.TagRepository;
import com.sidepr.mono.sns.user.domain.Follow;
import com.sidepr.mono.sns.user.domain.Role;
import com.sidepr.mono.sns.user.domain.User;
import com.sidepr.mono.sns.user.repository.FollowRepository;
import com.sidepr.mono.sns.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class TestData {

    @Autowired UserRepository userRepository;
    @Autowired FollowRepository followRepository;
    @Autowired TagRepository tagRepository;
    @Autowired PostTagRepository postTagRepository;
    @Autowired PostRepository postRepository;
    @Autowired PostLikeRepository postLikeRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired CommentLikeRepository commentLikeRepository;
    @Autowired EntityManager em;
    private final PasswordEncoder passwordEncoder;

    public TestData(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void init() {
        initUser();
        initPost();
    }

    private void initUser(){
        User admin = new User(1L,
                "관리자",
                "admin_01",
                "abc123@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자",
                "010-1234-1234",
                Role.ADMIN);
        User user1 = new User(2L,
                "관리자맞팔",
                "user_01",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자맞팔",
                "010-4321-4321",
                Role.USER);
        User user2 = new User(3L,
                "관리자팔로잉",
                "user_02",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자팔로잉",
                "010-1241-5231",
                Role.USER);
        User user3 = new User(4L,
                "관리자팔로워",
                "user_03",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자팔로워",
                "010-2314-3124",
                Role.USER);
        User user4 = new User(5L,
                "관리자와관계없는유저",
                "user_04",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자와관계없는유저",
                "010-2124-3124",
                Role.USER);

        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        followRepository.save(new Follow(admin, user1));
        followRepository.save(new Follow(user1, admin));
        followRepository.save(new Follow(admin, user2));
        followRepository.save(new Follow(user3, admin));
    }

    private void initPost(){
        User publisher = findAdmin();
        Post post1 = new Post(publisher, "테스트 테스트~~ #태그1 #태그2 #태그_3");
        Post post2 = new Post(publisher, "테스트 테스트~~ #태그1#태그2#태그3");
        Post post3 = new Post(publisher, "테스트 테스트~~ #태그1 #태그2#태그_3");
        Post post4 = new Post(publisher, "테스트 테스트~~ #태그2 #태그_3");
        Post post5 = new Post(publisher, "테스트 테스트~~ #태그1 #태그_3");
        Post post6 = new Post(publisher, "테스트 테스트~~ #태그1 #태그2");
        Post post7 = new Post(publisher, "테스트 테스트~~ #태그2");
        Post post8 = new Post(publisher, "테스트 테스트~~ #태그_3");
        Post post9 = new Post(publisher, "테스트 테스트~~#태그1");

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);
        postRepository.save(post8);
        postRepository.save(post9);

        Tag tag1 = new Tag("태그1");
        Tag tag2 = new Tag("태그2");
        Tag tag3 = new Tag("태그_3");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        PostTag postTag1 = new PostTag(post1, tag1);
        PostTag postTag2 = new PostTag(post1, tag2);
        PostTag postTag3 = new PostTag(post1, tag3);
        PostTag postTag4 = new PostTag(post2, tag1);
        PostTag postTag5 = new PostTag(post2, tag2);
        PostTag postTag6 = new PostTag(post3, tag1);
        PostTag postTag7 = new PostTag(post3, tag2);
        PostTag postTag8 = new PostTag(post3, tag3);
        PostTag postTag9 = new PostTag(post4, tag2);
        PostTag postTag10 = new PostTag(post4, tag3);
        PostTag postTag11 = new PostTag(post5, tag1);
        PostTag postTag12 = new PostTag(post5, tag3);
        PostTag postTag13 = new PostTag(post6, tag1);
        PostTag postTag14 = new PostTag(post6, tag2);
        PostTag postTag15 = new PostTag(post7, tag2);
        PostTag postTag16 = new PostTag(post8, tag3);
        PostTag postTag17 = new PostTag(post9, tag1);

        post1.addPostTag(postTag1);
        post1.addPostTag(postTag2);
        post1.addPostTag(postTag3);
        post2.addPostTag(postTag4);
        post2.addPostTag(postTag5);
        post3.addPostTag(postTag6);
        post3.addPostTag(postTag7);
        post3.addPostTag(postTag8);
        post4.addPostTag(postTag9);
        post4.addPostTag(postTag10);
        post5.addPostTag(postTag11);
        post5.addPostTag(postTag12);
        post6.addPostTag(postTag13);
        post6.addPostTag(postTag14);
        post7.addPostTag(postTag15);
        post8.addPostTag(postTag16);
        post9.addPostTag(postTag17);
    }

    @Transactional(readOnly=true)
    public User findAdmin(){
        return userRepository.findByNicknameAndIsDeletedFalse("admin_01").get();
    }

    @Transactional(readOnly = true)
    public User findEachFollowWithAdmin(){
        return userRepository.findByNicknameAndIsDeletedFalse("user_01").get();
    }

    @Transactional(readOnly = true)
    public User findFollowingAdmin(){
        return userRepository.findByNicknameAndIsDeletedFalse("user_02").get();
    }

    @Transactional(readOnly = true)
    public User findFollowerOfAdmin(){
        return userRepository.findByNicknameAndIsDeletedFalse("user_03").get();
    }

    @Transactional(readOnly = true)
    public User findNoLinkWithAdmin(){
        return userRepository.findByNicknameAndIsDeletedFalse("user_04").get();
    }

}
