package com.coderder.colorMeeting.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotAuthorizedException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.NOT_AUTHORIZED;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
