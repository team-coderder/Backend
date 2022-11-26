package com.coderder.colorMeeting.config;

import com.coderder.colorMeeting.config.jwt.JwtAuthenticationFilter;
import com.coderder.colorMeeting.config.jwt.JwtAuthorizationFilter;
import com.coderder.colorMeeting.config.jwt.JwtProperties;
import com.coderder.colorMeeting.repository.MemberRepository;
import com.coderder.colorMeeting.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    private CorsConfig corsConfig;

    @Autowired
    private JwtProperties jwtProperties;


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //허용할 url 설정
        configuration.addAllowedOrigin("http://localhost:3000");
        //허용할 헤더 설정
        configuration.addAllowedHeader("*");
        //허용할 http method
        configuration.addAllowedMethod("*");
//        //클라이언트가 접근 할 수 있는 서버 응답 헤더
//        configuration.addExposedHeader(TokenProperties.AUTH_HEADER);
//        configuration.addExposedHeader(TokenProperties.REFRESH_HEADER);
        //사용자 자격 증명이 지원되는지 여부
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable()
                .addFilterBefore(new JwtAuthorizationFilter(memberRepository, jwtProperties), UsernamePasswordAuthenticationFilter.class)
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperties))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository, jwtProperties))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests((authz)->authz
                .antMatchers("/auth/**").authenticated()
                .anyRequest().permitAll());

        return http.build();
    }



}

//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private CorsConfig corsConfig;
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // csrf().disable() : jwt 구현 전까지 POST 등의 요청에 403 에러를 방지하기 위한 임시 설정 ----------- 민진
//                .addFilter(corsConfig.corsFilter())
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//                .formLogin().disable()
//                .httpBasic().disable()
//
//                // 필터
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperties))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository, jwtProperties))
//
//                // 로그인한 유저만 /api/**에 접근할 수 있다
//                .authorizeRequests()
//                .antMatchers("/api/**")
//                .access("hasRole('ROLE_USER')")
//                .anyRequest().permitAll();
//    }
//}
