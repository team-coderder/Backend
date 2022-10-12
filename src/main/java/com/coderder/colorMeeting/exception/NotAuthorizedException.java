package com.coderder.colorMeeting.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotAuthorizedException extends RuntimeException{

    private final ErrorCode errorCode;

}
