package com.coderder.colorMeeting.exception;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class TeamNotFoundException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.TEAM_NOT_FOUND;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}