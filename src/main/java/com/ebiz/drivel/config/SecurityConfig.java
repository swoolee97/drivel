package com.ebiz.drivel.config;

import com.ebiz.drivel.domain.auth.CustomAuthenticationEntryPoint;
import com.ebiz.drivel.domain.auth.application.JwtProvider;
import com.ebiz.drivel.domain.auth.filter.AuthExceptionHandlerFilter;
import com.ebiz.drivel.domain.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((auth) -> {
                            auth.requestMatchers("/healthy").permitAll();
                            auth.requestMatchers("/kakao/*").permitAll();
                            auth.requestMatchers("/apple/*").permitAll();
                            auth.requestMatchers("/auth/*").permitAll();
                            auth.requestMatchers(("/mail/auth")).permitAll();
                            auth.requestMatchers(("/mail/check")).permitAll();
                            auth.requestMatchers(("/mail/password-reset")).permitAll();
                            auth.requestMatchers(("/sse/connect")).permitAll();
                            auth.requestMatchers(("/ws/connect")).permitAll();
                            auth.requestMatchers("/members/**").permitAll();
                            auth.anyRequest().authenticated();
                        }
                ).addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthExceptionHandlerFilter(), JwtAuthenticationFilter.class);
        http.exceptionHandling(manager -> manager.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandlerImpl()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
