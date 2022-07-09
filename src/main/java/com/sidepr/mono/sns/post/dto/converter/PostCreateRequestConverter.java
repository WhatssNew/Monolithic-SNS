package com.sidepr.mono.sns.post.dto.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidepr.mono.sns.post.dto.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreateRequestConverter implements Converter<String, PostCreateRequest> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public PostCreateRequest convert(String source) {
        return objectMapper.readValue(source, new TypeReference<>() {});
    }
}
