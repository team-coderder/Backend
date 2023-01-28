package com.coderder.colorMeeting.exception;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}