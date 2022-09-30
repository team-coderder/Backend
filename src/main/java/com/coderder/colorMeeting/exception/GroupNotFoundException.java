package com.coderder.colorMeeting.exception;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
public class GroupNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

}