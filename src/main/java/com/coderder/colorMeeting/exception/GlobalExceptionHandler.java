package com.coderder.colorMeeting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse>notFoundExceptionHandler(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> BadRequestExceptionHandler(BadRequestException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unAuthorizedExceptionHandler(UnAuthorizedException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> ForbiddenExceptionHandler(ForbiddenException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getErrorCode()), HttpStatus.FORBIDDEN);
    }
}
