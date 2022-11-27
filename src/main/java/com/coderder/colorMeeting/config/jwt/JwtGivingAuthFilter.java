package com.coderder.colorMeeting.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coderder.colorMeeting.config.auth.PrincipalDetails;
import com.coderder.colorMeeting.exception.ErrorCode;
import com.coderder.colorMeeting.exception.NotFoundException;
import com.coderder.colorMeeting.exception.UnAuthorizedException;
import com.coderder.colorMeeting.model.Member;
import com.coderder.colorMeeting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.coderder.colorMeeting.exception.ErrorCode.MEMBER_NOT_FOUND;


@RequiredArgsConstructor
public class JwtGivingAuthFilter extends OncePerRequestFilter {


    // 의존성 주입
    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // header에서 jwt토큰을 가져온다
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        String requestUri = request.getRequestURI();

        if(requestUri.contains("api")){
            // jwt 토큰이 없으면
            if(header == null){
                jwtUtil.exceptionResponse(response,ErrorCode.INVALID_LOGIN);
                return;
            }

            if(!header.startsWith(JwtProperties.TOKEN_PREFIX)){
                jwtUtil.exceptionResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
                return;
            }

            // jwt 토큰이 있으면, 검증한다
            String token = request.getHeader(JwtProperties.HEADER_STRING)
                    .replace(JwtProperties.TOKEN_PREFIX, "");
            String username = JWT.require(Algorithm.HMAC512(jwtProperties.getSECRET())).build().verify(token)
                    .getClaim("username").asString();

            // 해당하는 유저가 없으면
            if (username == null) {
                jwtUtil.exceptionResponse(response, ErrorCode.MEMBER_NOT_FOUND);
                return;
            }

            // 모든 예외 처리를 통과했으면
            Member userEntity = memberRepository.findByUsername(username);

            // 권한 처리
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails,
                            null,
                            principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } else {
        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        // auth가 포함되지 않은 uri는 인가를 거치지 않고 다음 필터로 넘어간다.
        chain.doFilter(request,response);
        }
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        // header에서 jwt토큰을 가져온다
//        String header = request.getHeader(JwtProperties.HEADER_STRING);
//
//
//        // jwt 토큰이 없으면
//        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//            // exception 처리하기
//        }
//
//        // jwt 토큰이 있으면, 검증한다
//        String token = request.getHeader(JwtProperties.HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//
//        String username = JWT.require(Algorithm.HMAC512(jwtProperties.getSECRET())).build().verify(token)
//                .getClaim("username").asString();
//
//        if(username != null) {
//            Member userEntity = memberRepository.findByUsername(username);
//
//            // 권한 처리
//            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
//
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            principalDetails,
//                            null,
//                            principalDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        // exception 처리하기
//        chain.doFilter(request, response);
//
//
//    }

}

//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//
//    // 의존성 주입
//    private MemberRepository memberRepository;
//    private JwtProperties jwtProperties;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtProperties jwtProperties) {
//        super(authenticationManager);
//        // System.out.println("======== jwt authorization filter 생성 =======");
//        this.memberRepository = memberRepository;
//        this.jwtProperties = jwtProperties;
//    }
//
//    // 인증 필터
//    // jwt 토큰을 검증한다
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        // System.out.println("=============== Authorization starts");
//        // System.out.println("=============== is this colorMeeting? " + jwtProperties.getTEST());
//
//        // header에서 jwt토큰을 가져온다
//        String header = request.getHeader(JwtProperties.HEADER_STRING);
//        // System.out.println("=============== header에서 jwt 토큰을 가져온다 " + header);
//        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response); // 아무 내용도 없는 필터
//            return;
//        }
//
//        // jwt 토큰을 검증한다
//        String token = request.getHeader(JwtProperties.HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//        // System.out.println("=============== 토큰 값 가져온만다 " + token);
//
//        String username = JWT.require(Algorithm.HMAC512(jwtProperties.getSECRET())).build().verify(token)
//                .getClaim("username").asString();
//        // System.out.println("=============== 토큰을 디코딩하여 username을 얻는다 " + username + "======");
//
//        if(username != null) {
//            Member userEntity = memberRepository.findByUsername(username);
//            // System.out.println("=============== username으로 Member를 찾는다 " + userEntity.getUsername());
//
//            // 권한 처리
//            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
//            // System.out.println("=============== Member로 principalDetails를 만든다 " + principalDetails.getUsername());
//
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            principalDetails,
//                            null,
//                            principalDetails.getAuthorities());
//
//            // System.out.println("=============== 권한 관리를 위해 Authentication 객체에 저장한다 " + authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(request, response);
//
//
//        // System.out.println("=============== Authorization ends");
//
//
//    }
//
//
//}
