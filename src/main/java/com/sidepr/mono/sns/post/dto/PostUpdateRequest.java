package com.sidepr.mono.sns.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

    private String content;

    private List<String> images = new ArrayList<>();

    public void addImage(String image){
        this.images.add(image);
    }

}
