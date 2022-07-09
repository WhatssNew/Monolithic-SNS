package com.sidepr.mono.sns.post.exception;

import com.sidepr.mono.sns.global.error.ErrorCode;
import com.sidepr.mono.sns.global.error.exception.NotPermittedResourceException;

public class NotPermittedPostException extends NotPermittedResourceException {
    public NotPermittedPostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
