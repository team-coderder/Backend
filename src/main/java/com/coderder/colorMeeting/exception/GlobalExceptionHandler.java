package com.coderder.colorMeeting.exception;

import com.coderder.colorMeeting.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> groupNotFoundExceptionHandler(GroupNotFoundException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.GROUP_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> memberNotFoundExceptionHandler(MemberNotFoundException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> invalidGroupNameExceptionHandler(InvalidGroupNameException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_GROUP_NAME), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> notAuthorizedExceptionHandler(NotAuthorizedException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.NOT_AUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

}
