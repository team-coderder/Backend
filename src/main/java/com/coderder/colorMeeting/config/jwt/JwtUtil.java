package com.coderder.colorMeeting.config.jwt;

import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger("JwtUtil 의 로그");

    // 예외 응답
    public void exceptionResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
//        ResponseDto<?> responseDto = ResponseDto.fail(errorCode);
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
//        String httpResponse = objectMapper.writeValueAsString(errorResponse);
        String httpResponse = objectMapper.writeValueAsString(errorResponse);

        logger.debug("response에 Write 전");
        logger.debug(httpResponse);
        logger.debug(httpResponse.toString());

//        log.debug("어노테이션, response에 Write 전");
//        log.debug(httpResponse);

        response.getWriter().write(httpResponse);

        logger.debug("response에 Write 후");
        logger.debug(response.toString());

//        log.debug("어노테이션, response에 Write 후");
//        log.debug(response.toString());
    }

}
