package com.coderder.colorMeeting.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.secret.key}")
    private String TEST;
//    private static String SECRET; // 우리 서버만 알고 있는 비밀값
//    @Value("${jwt.secret.key}")
//    static String SECRET;
    static int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    static String TOKEN_PREFIX = "Bearer ";
    static String HEADER_STRING = "Authorization";

//    public static String getSECRET() {
//        return SECRET;
//    }

    public String getTEST() {
        return TEST;
    }
}
