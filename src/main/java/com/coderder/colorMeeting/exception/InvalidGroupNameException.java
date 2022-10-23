package com.coderder.colorMeeting.exception;

public class InvalidGroupNameException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.INVALID_TEAM_NAME;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
