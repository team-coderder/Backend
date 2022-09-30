package com.coderder.colorMeeting.dto;

import com.coderder.colorMeeting.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseMessage<T> {
    private Boolean isSuccess;
    private T data;
    private ErrorCode code;
    private String message;

    public ResponseMessage(Boolean isSuccess, T data, ErrorCode errorCode){
        this.isSuccess = isSuccess;
        this.data = data;
        this.code = errorCode;
        this.message = errorCode.getMessage();
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(true, data, ErrorCode.NULL);
    }

    public static <T> ResponseMessage<T> fail(ErrorCode errorCode) {
        return new ResponseMessage<>(false, null, errorCode);
    }
}