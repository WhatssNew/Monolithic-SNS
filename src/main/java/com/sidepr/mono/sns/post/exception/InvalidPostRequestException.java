package com.sidepr.mono.sns.post.exception;

import com.sidepr.mono.sns.global.error.ErrorCode;
import com.sidepr.mono.sns.global.error.exception.BusinessException;

public class InvalidPostRequestException extends BusinessException {
    public InvalidPostRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
