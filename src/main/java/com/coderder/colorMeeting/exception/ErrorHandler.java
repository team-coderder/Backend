package com.coderder.colorMeeting.exception;

import com.coderder.colorMeeting.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

    public static ResponseEntity<?> returnResponse(ResponseMessage<?> data) {
        if(data.getIsSuccess()) return ResponseEntity.ok().body(data);
        else{
            switch(data.getCode()) {
                // 400 : 잘못된 문법 (ex. data form)

                // 401 : 인증이 필요함 (ex. 로그인 / 토큰)

                // 403 : 접근 권한 없음
                case NOT_AUTHORIZED:
                    return new ResponseEntity(data, HttpStatus.UNAUTHORIZED);

                //404 : 리소스가 없음
                case GROUP_NOT_FOUND:
                    return new ResponseEntity(data, HttpStatus.NOT_FOUND);
            }
        }
        // 500 : 알 수 없는 서버 에러
        return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
