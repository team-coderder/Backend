package com.coderder.colorMeeting.exception;

import com.coderder.colorMeeting.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?>notFoundExceptionHandler(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> invalidGroupNameExceptisonHandler(BadRequestException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> unAuthorizedExceptionHandler(UnAuthorizedException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> ForbiddenExceptionHandler(ForbiddenException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.FORBIDDEN);
    }
}
