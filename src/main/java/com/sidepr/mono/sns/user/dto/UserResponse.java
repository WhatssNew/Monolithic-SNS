package com.sidepr.mono.sns.user.dto;


import com.sidepr.mono.sns.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserResponse {

    private Long userId;
    private String name;
    private String email;
    private String nickname;
    private String profileImage;
    private Boolean isFollowed;

    private Boolean isMyAccount;

    public void setIsFollowed(Boolean isFollowed){
        this.isFollowed = isFollowed;
    }

    public void setIsMyAccount(Boolean isMyAccount){
        this.isMyAccount = isMyAccount;
    }

    @Builder
    public UserResponse(
            Long userId, String name, String email, String nickname, String profileImage
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static UserResponse of(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImage()
        );
    }
}
