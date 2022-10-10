package com.coderder.colorMeeting.dto.response;

import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private ErrorResponse error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
        ErrorResponse error = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return new ResponseDto<>(false, null, error);
    }
}
