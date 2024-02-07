package com.pj.oil.config;

import com.pj.oil.auth.LoginFailHandler;
import com.pj.oil.auth.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "configuration/ui",
            "configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/assets/**",
            "/templates/**",
            "/js/**",
            "/css/**",
            "/brand/**",
            "/**",
            "/"};
    // 주입된 커스텀 JWT 인증 필터
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 사용자 정의 AuthenticationProvider
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final LogoutHandler logoutHandler;
//    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                WHITE_LIST_URL
                        )
                        .permitAll()
//                        .requestMatchers("/api/v1/auth/**").permitAll()// "/api/v1/auth/**" 경로는 인증 없이 허용
                        .requestMatchers("/gas-station/**").permitAll()// "/api/v1/gas-station/**" 경로는 인증 없이 허용
                        .requestMatchers("/admin/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/member/**").hasAnyRole(ADMIN.name(), USER.name())
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않고, Stateless하게 설정
                .authenticationProvider(authenticationProvider) // 커스텀 AuthenticationProvider 설정
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 이전에 jwtAuthenticationFilter 추가
//                .rememberMe(remember -> remember // rememberMe 기능 작동함
//                        .rememberMeParameter("remember") // default: remember-me, checkbox 등의 이름과 맞춰야함
//                        .tokenValiditySeconds(3600) // 쿠키의 만료시간 설정(초), default: 14일
//                        .alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
//                        .userDetailsService(userDetailsService) // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함.
//                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Controller 엔드포인트 대신 사용
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        SecurityContextHolder.clearContext()
                        )
                );
        return http.build();
    }
}
