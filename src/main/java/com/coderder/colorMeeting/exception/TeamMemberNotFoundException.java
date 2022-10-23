package com.coderder.colorMeeting.exception;

public class TeamMemberNotFoundException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.TEAM_MEMBER_NOT_FOUND;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
