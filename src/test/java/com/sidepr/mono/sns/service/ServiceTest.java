package com.sidepr.mono.sns.service;

import com.sidepr.mono.sns.TestData;
import com.sidepr.mono.sns.comment.service.CommentService;
import com.sidepr.mono.sns.post.service.PostService;
import com.sidepr.mono.sns.tag.service.TagService;
import com.sidepr.mono.sns.user.domain.User;
import com.sidepr.mono.sns.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Slf4j
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class ServiceTest {
    User myAccount;
    User normalUser;
    User myFollower;
    User myFollowing;
    User eachFollow;

    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    TestData testData;

    @Autowired
    EntityManager em;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @BeforeEach
    void init(){
        testData.init();

        myAccount = testData.findAdmin();
        myFollower = testData.findFollowerOfAdmin();
        myFollowing = testData.findFollowingAdmin();
        eachFollow = testData.findEachFollowWithAdmin();
        normalUser = testData.findNoLinkWithAdmin();
    }
}
