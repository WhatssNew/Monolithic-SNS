package com.sidepr.mono.sns.tag.service;

import com.sidepr.mono.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final PostRepository postRepository;

}
