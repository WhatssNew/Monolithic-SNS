package com.sidepr.mono.sns.service;

import com.sidepr.mono.sns.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends ServiceTest{

    @Test
    @DisplayName("회원 정보 조회테스트")
    void loadUserInfo() {
        User user = userService.findActiveUser(myAccount.getId());
        assertThat(user).isEqualTo(myAccount);
    }
}
