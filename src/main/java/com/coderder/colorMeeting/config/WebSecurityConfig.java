package com.coderder.colorMeeting.config;

import com.coderder.colorMeeting.config.jwt.JwtAuthenticationFilter;
import com.coderder.colorMeeting.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf().disable() : jwt 구현 전까지 POST 등의 요청에 403 에러를 방지하기 위한 임시 설정 ----------- 민진
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .formLogin().disable()
                .httpBasic().disable()

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                // 로그인한 유저만 /api/**에 접근할 수 있다
                .authorizeRequests()
                .antMatchers("/api/member/**")
                .access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();

    }
}
