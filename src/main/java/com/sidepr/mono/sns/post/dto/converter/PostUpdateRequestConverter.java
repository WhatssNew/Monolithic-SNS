package com.sidepr.mono.sns.post.dto.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidepr.mono.sns.post.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUpdateRequestConverter implements Converter<String, PostUpdateRequest> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public PostUpdateRequest convert(String source) {
        return objectMapper.readValue(source, new TypeReference<>() {});
    }
}
