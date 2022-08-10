package com.sidepr.mono.sns;

import com.sidepr.mono.sns.comment.repository.CommentLikeRepository;
import com.sidepr.mono.sns.comment.repository.CommentRepository;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.domain.PostImage;
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
        User admin = new User(
                "관리자",
                "admin_01",
                "abc123@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자",
                "010-1234-1234",
                Role.ADMIN);
        User user1 = new User(
                "관리자맞팔",
                "user_01",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자맞팔",
                "010-4321-4321",
                Role.USER);
        User user2 = new User(
                "관리자팔로잉",
                "user_02",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자가 팔로우",
                "010-1241-5231",
                Role.USER);
        User user3 = new User(
                "관리자팔로워",
                "user_03",
                "abc124@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "관리자의 팔로워",
                "010-2314-3124",
                Role.USER);
        User user4 = new User(
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

        followRepository.save(
                Follow.builder()
                        .followed(admin)
                        .following(user1)
                        .build()
        );
        followRepository.save(
                Follow.builder()
                        .followed(user1)
                        .following(admin)
                        .build()
        );
        followRepository.save(
                Follow.builder()
                        .followed(user2)
                        .following(admin)
                        .build()
        );
        followRepository.save(
                Follow.builder()
                        .followed(admin)
                        .following(user3)
                        .build()
        );
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

        PostImage postImage1 = new PostImage(post1, "img1.jpg");
        PostImage postImage2 = new PostImage(post1, "img2.jpg");
        PostImage postImage3 = new PostImage(post1, "img3.jpg");
        PostImage postImage4 = new PostImage(post1, "img4.jpg");
        PostImage postImage5 = new PostImage(post2, "img5.jpg");
        PostImage postImage6 = new PostImage(post2, "img6.jpg");
        PostImage postImage7 = new PostImage(post2, "img7.jpg");
        PostImage postImage8 = new PostImage(post3, "img8.jpg");
        PostImage postImage9 = new PostImage(post3, "img9.jpg");
        PostImage postImage10 = new PostImage(post4, "img10.jpg");
        PostImage postImage11 = new PostImage(post4, "img11.jpg");
        PostImage postImage12 = new PostImage(post4, "img12.jpg");
        PostImage postImage13 = new PostImage(post5, "img13.jpg");
        PostImage postImage14 = new PostImage(post5, "img14.jpg");
        PostImage postImage15 = new PostImage(post5, "img15.jpg");
        PostImage postImage16 = new PostImage(post5, "img16.jpg");
        PostImage postImage17 = new PostImage(post5, "img17.jpg");
        PostImage postImage18 = new PostImage(post5, "img18.jpg");
        PostImage postImage19 = new PostImage(post6, "img19.jpg");
        PostImage postImage20 = new PostImage(post6, "img20.jpg");
        PostImage postImage21 = new PostImage(post6, "img21.jpg");
        PostImage postImage22 = new PostImage(post7, "img22.jpg");
        PostImage postImage23 = new PostImage(post7, "img23.jpg");
        PostImage postImage24 = new PostImage(post8, "img24.jpg");
        PostImage postImage25 = new PostImage(post8, "img25.jpg");
        PostImage postImage26 = new PostImage(post8, "img26.jpg");
        PostImage postImage27 = new PostImage(post9, "img27.jpg");
        PostImage postImage28 = new PostImage(post9, "img28.jpg");

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

        post1.addImage(postImage1);
        post1.addImage(postImage2);
        post1.addImage(postImage3);
        post1.addImage(postImage4);
        post2.addImage(postImage5);
        post2.addImage(postImage6);
        post2.addImage(postImage7);
        post3.addImage(postImage8);
        post3.addImage(postImage9);
        post4.addImage(postImage10);
        post4.addImage(postImage11);
        post4.addImage(postImage12);
        post5.addImage(postImage13);
        post5.addImage(postImage14);
        post5.addImage(postImage15);
        post5.addImage(postImage16);
        post5.addImage(postImage17);
        post5.addImage(postImage18);
        post6.addImage(postImage19);
        post6.addImage(postImage20);
        post6.addImage(postImage21);
        post7.addImage(postImage22);
        post7.addImage(postImage23);
        post8.addImage(postImage24);
        post8.addImage(postImage25);
        post8.addImage(postImage26);
        post9.addImage(postImage27);
        post9.addImage(postImage28);
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
