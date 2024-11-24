package com.example.studentmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.studentmanagement.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用 CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate").permitAll() // 允许匿名访问 /authenticate
                .requestMatchers("/admin/**").hasRole("ADMIN") // 管理员权限
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/login") // 使用 /login 作为 Spring Security 的默认登录路径
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureHandler(new CustomAuthenticationFailureHandler()) // 自定义失败处理
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService) // 使用自定义 UserDetailsService
            .passwordEncoder(passwordEncoder()) // 使用明文密码
            .and()
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 明文密码
    }
}
