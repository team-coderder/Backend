package com.coderder.colorMeeting.config.jwt;

import com.coderder.colorMeeting.dto.response.ResponseDto;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final ObjectMapper objectMapper;

    // 예외 응답
    public void exceptionResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto<?> responseDto = ResponseDto.fail(errorCode);
        String httpResponse = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(httpResponse);
    }

}
