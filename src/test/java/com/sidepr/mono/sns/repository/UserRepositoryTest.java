package com.sidepr.mono.sns.repository;

import com.sidepr.mono.sns.user.domain.Role;
import com.sidepr.mono.sns.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
public class UserRepositoryTest extends RepositoryTest{

    private User user;
    private User admin;

    @BeforeEach
    void before(){
        admin = User.builder()
                .name("관리자")
                .email("admin@1.com")
                .roles(Role.ADMIN)
                .nickname("admin")
                .password(passwordEncoder.encode("1234"))
                .build();

        user = User.builder()
                .name("유저")
                .email("user@1.com")
                .roles(Role.USER)
                .nickname("user_")
                .password(passwordEncoder.encode("1234"))
                .build();
        userRepository.save(user);
        userRepository.save(admin);
    }

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        User createUser = User.builder()
                .name("유저1")
                .email("1@1.com")
                .roles(Role.USER)
                .nickname("user_01")
                .password(passwordEncoder.encode("1234"))
                .build();
        userRepository.save(createUser);

        User findUser = userRepository.findByIdAndIsDeletedFalse(createUser.getId()).get();

        assertThat(createUser).isEqualTo(findUser);
        assertThat(createUser.getPassword()).isNotEqualTo("1234");
        assertThat(createUser.getRoles()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("관리자 계정 생성")
    void createAdmin() {
        User createAdmin = User.builder()
                .name("관리자")
                .email("12@1.com")
                .roles(Role.ADMIN)
                .nickname("admin_01")
                .password(passwordEncoder.encode("1234"))
                .build();
        userRepository.save(createAdmin);

        User findAdmin = userRepository.findByIdAndIsDeletedFalse(createAdmin.getId()).get();

        assertThat(createAdmin).isEqualTo(findAdmin);
        assertThat(createAdmin.getPassword()).isNotEqualTo("1234");
        assertThat(createAdmin.getRoles()).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser(){
        user.updateUserDeleted();
        assertThat(user.isDeleted()).isTrue();

        assertThat(userRepository.findByIdAndIsDeletedFalse(user.getId()).orElse(null))
                .isNull();
    }
}
