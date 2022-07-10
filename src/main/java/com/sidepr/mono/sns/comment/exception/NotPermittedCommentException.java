package com.sidepr.mono.sns.comment.exception;

import com.sidepr.mono.sns.global.error.ErrorCode;
import com.sidepr.mono.sns.global.error.exception.NotPermittedResourceException;

public class NotPermittedCommentException extends NotPermittedResourceException {
    public NotPermittedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
