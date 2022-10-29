package com.coderder.colorMeeting.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.dto.request.LoginRequestDto;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.model.UserTest;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // DB에 저장된 값과 일치하는가?
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        try {
            // 1) request에서 username, password 받아오기
            // ObjectMapper는 json을 파싱한다
            // User.class를 넣으면 username, password 외에 기타 정보도 다 가져온다. loginDto를 추가로 만들자
            ObjectMapper om = new ObjectMapper();
            LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);

            // 2) 토큰을 만들고 로그인을 시도한다
            // am.authenticate()는 토큰의 파라메터(getUsername, getPassword)를 차례대로 검증한다
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // 로깅
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("Authentication : "+principalDetails.getMember().getUsername());

            // 3) authenticate를 성공하면 authentication을 만든다.
            // attemptAuthentication의 리턴값은 session 영역에 저장된다.
            // 권한 관리를 위해 session에 저장한다. 권한 관리를 안 하면 굳이 세션에 저장하지 않아도 된다.
            return authentication;

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    };

    // attemptAuthentication 다음에 실행되는 함수
    // jwt 토큰을 만들어서 request 요청한 사용자에게 jwt 토큰을 응답해준다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername()) // 토큰 이름. 크게 의미 없음
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME)) // 만료시간
                .withClaim("id", principalDetailis.getMember().getId()) // payload : id. pk
                .withClaim("username", principalDetailis.getMember().getUsername()) // payload : username. pk
                .sign(Algorithm.HMAC512(JwtProperties.getSECRET())); // 검증값

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken); // 헤더key, value

    }
}
