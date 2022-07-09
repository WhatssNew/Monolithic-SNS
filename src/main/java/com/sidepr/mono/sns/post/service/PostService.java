package com.sidepr.mono.sns.post.service;

import com.sidepr.mono.sns.comment.domain.Comment;
import com.sidepr.mono.sns.global.error.exception.RuntimeIOException;
import com.sidepr.mono.sns.global.fileuploader.FileUploader;
import com.sidepr.mono.sns.post.domain.Post;
import com.sidepr.mono.sns.post.domain.PostImage;
import com.sidepr.mono.sns.post.domain.PostTag;
import com.sidepr.mono.sns.post.dto.PostCreateRequest;
import com.sidepr.mono.sns.post.dto.PostDetailResponse;
import com.sidepr.mono.sns.post.dto.PostUpdateRequest;
import com.sidepr.mono.sns.post.dto.PostUpdateResponse;
import com.sidepr.mono.sns.post.exception.NotFoundPostException;
import com.sidepr.mono.sns.post.exception.NotPermittedPostException;
import com.sidepr.mono.sns.post.repository.PostRepository;
import com.sidepr.mono.sns.tag.domain.Tag;
import com.sidepr.mono.sns.tag.repository.TagRepository;
import com.sidepr.mono.sns.user.domain.User;
import com.sidepr.mono.sns.user.exception.NotFoundUserException;
import com.sidepr.mono.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sidepr.mono.sns.global.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final FileUploader uploader;

    @Value("${file.post}")
    private String DIRECTORY;

    @Value("${tag.pattern}")
    private String TAG_PATTERN;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Transactional
    public Long savePost(
            Long userId,
            PostCreateRequest postCreateRequest,
            List<MultipartFile> files
    ){
        User user = findActiveUser(userId);
        List<String> contentTag = getContentTag(postCreateRequest.getContent());

        List<PostTag> postTags = contentTag.stream()
                .map(tagRepository::findByContentOrCreate)
                .map(PostTag::new)
                .collect(Collectors.toList());

        Post post = postCreateRequest.toEntity(user);
        files.forEach(f -> post.addImage(getImage(post, f)));
        postTags.forEach(post::addPostTag);

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long deletePost(Long userId, Long postId){
        Post post = findActiveMyPost(userId, postId);
        post.deletePost();
        return post.getId();
    }

    @Transactional
    public PostUpdateResponse updatePost(
            Long userId,
            Long postId,
            PostUpdateRequest postUpdateRequest,
            List<MultipartFile> files
    ){
        Post post = findActiveMyPost(userId, postId);
        files.forEach(f -> postUpdateRequest.addImage(getImage(post, f).getImage()));
        post.updatePost(postUpdateRequest);

        return PostUpdateResponse.builder()
                .id(postId)
                .userId(post.getUser().getEmail())
                .content(post.getContent())
                .isDeleted(post.getIsDeleted())
                .images(
                        post.getImages().stream()
                                .map(PostImage::getImage)
                                .collect(Collectors.toList())
                )
                .tags(
                        post.getPostTags().stream()
                                .map(PostTag::getTag)
                                .map(Tag::getContent)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Transactional(readOnly = true)
    public User findActiveUser(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));
    }

    @Transactional(readOnly = true)
    public Post findActiveMyPost(Long userId, Long postId) {
        User user = findActiveUser(userId);
        Post post =  postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_RESOURCE_ERROR));

        if(post.getUser() != user) throw new NotPermittedPostException(NOT_PERMITTED_RESOURCE_ERROR);
        return post;
    }

    //TODO 단건 디테일
    @Transactional(readOnly = true)
    public PostDetailResponse findPost(Long postId){
        Post post = postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_RESOURCE_ERROR));

        //TODO post에 comment 추가
        List<Comment> comments = null;
        return post.toPostDetailResponse(comments);
    }

    //TODO 리스트
    @Transactional(readOnly = true)
    public List<PostDetailResponse> findPosts(){
        return null;
    }


    private String getDayFormatDirectoryName() {
        return DIRECTORY + dateTimeFormatter.format(LocalDateTime.now());
    }

    private PostImage getImage(Post post, MultipartFile f) {
        try {
            return PostImage.builder()
                    .post(post)
                    .image(uploader.upload(f, getDayFormatDirectoryName())).build();
        } catch (IOException e) {
            throw new RuntimeIOException(INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> getContentTag(String source) {
        Pattern tagPattern = Pattern.compile(TAG_PATTERN);
        Matcher tagMatcher = tagPattern.matcher(source);

        return tagMatcher.results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
