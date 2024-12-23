package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        //해당 메서드의 리턴되는 오브젝트를 IOC로 등록
        @Bean
        public BCryptPasswordEncoder encodePW() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        // CSRF 설정
                        .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")) // H2 콘솔 접근 허용
                        .disable() // 테스트용으로 전체 CSRF 비활성화 (운영 환경에서는 활성화 권장)
                        )
                        // 헤더 설정
                        .headers(headers -> headers
                                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN // H2 콘솔 UI 표시를 위한 SAMEORIGIN 설정
                                ))
                        )
                        // URL 권한 설정
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/user/**").authenticated() // "/user/**"는 인증 필요
                                .requestMatchers("/admin/**").hasRole("ADMIN") // "/admin/**"는 ADMIN 역할 필요
                                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔은 모두 허용
                               .requestMatchers("/", "/join", "/joinForm", "/loginForm").permitAll() // 특정 경로는 모두 허용
                               .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                        )
                        // 로그인 폼 설정
                        .formLogin(form -> form
                                .loginPage("/loginForm") // 사용자 정의 로그인 페이지
                                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
                                .permitAll()
                        )
                        // 로그아웃 설정
                        .logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
                                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                                .invalidateHttpSession(true) // 세션 무효화
                                .deleteCookies("JSESSIONID") // 쿠키 삭제
                        );

        return http.build();
    }
}
