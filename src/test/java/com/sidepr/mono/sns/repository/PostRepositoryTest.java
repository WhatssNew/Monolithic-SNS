package com.sidepr.mono.sns.repository;

import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.domain.PostTag;
import com.sidepr.mono.sns.tag.domain.Tag;
import com.sidepr.mono.sns.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
class PostRepositoryTest extends RepositoryTest{


    @BeforeEach
    void before(){
        User publisher = new User(1L,
                "이글써",
                "saefdf_04",
                "abc123@gmail.com",
                passwordEncoder.encode("1234"),
                null,
                "스무살~",
                "010-1234-1234");

        User subscriber = new User(2L,
                "이글봐",
                "lee_03",
                "abc1234@gmail.com",
                passwordEncoder.encode("5678"),
                null,
                "스물네짤~",
                "010-4321-4321");

        userRepository.save(publisher);
        userRepository.save(subscriber);

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

    @Test
    @DisplayName("tag로 포스트 검색")
    public void findByTag(){
        Page<Post> postOfTag1 = postRepository.findByTag("태그1", PageRequest.of(0, 20));
        Page<Post> postOfTag2 = postRepository.findByTag("태그2", PageRequest.of(0, 20));
        Page<Post> postOfTag3 = postRepository.findByTag("태그_3", PageRequest.of(0, 20));

        postOfTag1.stream().map(Post::getContent)
                        .forEach(p -> {
                            assertThat(p).isNotNull()
                                    .contains("#태그1");
                        });
        postOfTag2.stream().map(Post::getContent)
                        .forEach(p -> {
                            assertThat(p).isNotNull()
                                    .contains("#태그2");
                        });
        postOfTag3.stream().map(Post::getContent)
                        .forEach(p -> {
                            assertThat(p).isNotNull()
                                    .contains("#태그_3");
                        });

    }
}