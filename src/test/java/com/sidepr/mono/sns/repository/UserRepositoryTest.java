package com.sidepr.mono.sns.repository;

import com.sidepr.mono.sns.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class UserRepositoryTest extends RepositoryTest{

    @Test
    @DisplayName("유저 생성")
    void userCreate() {
        User user = User.builder()
                .build();
    }
}
