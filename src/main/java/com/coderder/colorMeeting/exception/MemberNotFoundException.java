package com.coderder.colorMeeting.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.MEMBER_NOT_FOUND;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
