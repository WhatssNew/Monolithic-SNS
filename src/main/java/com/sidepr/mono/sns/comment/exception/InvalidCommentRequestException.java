package com.sidepr.mono.sns.comment.exception;

import com.sidepr.mono.sns.global.error.ErrorCode;
import com.sidepr.mono.sns.global.error.exception.BusinessException;

public class InvalidCommentRequestException extends BusinessException {
    public InvalidCommentRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
