package com.coderder.colorMeeting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<?> groupNotFoundExceptionHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidGroupNameException.class)
    public ResponseEntity<?> invalidGroupNameExceptionHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<?> notAuthorizedExceptionHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
