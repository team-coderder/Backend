package com.coderder.colorMeeting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
////        config.addAllowedOrigin("http://localhost:3000"); // e.g. http://domain1.com
////        config.addAllowedOrigin("http://localhost:8080");
////        config.addAllowedOrigin("http://43.200.220.2:3000");
////        config.addAllowedOrigin("http://43.200.220.2:8080");
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/api/**", config);
//        return new CorsFilter(source);
//    }

}
