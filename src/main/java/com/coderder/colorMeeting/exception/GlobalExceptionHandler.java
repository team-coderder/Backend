package com.coderder.colorMeeting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;
import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business Exception Hanlding
     * 비즈니스 요구사항 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error("handleNotFoundException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("handleBadRequestException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
        log.error("handleUnAuthorizedException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        log.error("handleForbiddenException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.FORBIDDEN);
    }

    /**
     * Spring에서 발생하는 에러 Handling
     * 아래는 가장 기본적이며 필수적으로 처리하는 코드라고 함
     */

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE), BAD_REQUEST);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE), BAD_REQUEST);

    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.TYPE_MISMATCH), BAD_REQUEST);

    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED), BAD_REQUEST);

    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED), FORBIDDEN);

    }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
//        log.error("handleEntityNotFoundException", e);
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    /* 잘못된 URL 요청시 - 404 Not Found
     * 500 메세지는 Internal Server Error이므로 @ExceptionHandler를 이용해서 처리가 가능하지만,
     * 404 메세지는 문법 오류가 아니고 잘못된 URL을 호출할 때 보이므로 다르게 처리해주어야 함.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle404(NoHandlerFoundException e){
        log.error("NoHandlerFoundException", e);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.URL_NOT_FOUND), NOT_FOUND);
    }
}
