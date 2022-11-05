package com.coderder.colorMeeting.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    // @Value("${jwt.secret.key}")
    // private String TEST;
    // try 1 : 깃에 노출됨
    // private static String SECRET = "secret";
    // try 2 : 읽어오기
    @Value("${jwt.secret.key}")
    private String SECRET;
    static int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    static String TOKEN_PREFIX = "Bearer ";
    static String HEADER_STRING = "Authorization";

    public String getSECRET() {
        return SECRET;
    }

    // public String getTEST() {
    //     return TEST;
    // }
}
