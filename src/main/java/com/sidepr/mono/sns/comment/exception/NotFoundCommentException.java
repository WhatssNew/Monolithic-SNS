package com.sidepr.mono.sns.comment.exception;

import com.sidepr.mono.sns.global.error.ErrorCode;
import com.sidepr.mono.sns.user.exception.NotFoundUserException;

public class NotFoundCommentException extends NotFoundUserException {
    public NotFoundCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
