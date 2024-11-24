package com.example.studentmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    private final PlainTextAuthenticationProvider authenticationProvider;
//
//    public SecurityConfig(PlainTextAuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // 禁用 CSRF
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/authenticate").permitAll() // 允许所有用户访问 /authenticate
            .requestMatchers("/admin/courses").permitAll() // 允许匿名访问 /admin/courses
            .anyRequest().authenticated() // 其他请求需要认证
        )
        .sessionManagement(session -> session.maximumSessions(1)); // 配置会话管理

    return http.build();
}


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return authenticationProvider;
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 明文密码（测试用，生产中需换成 BCrypt）
    }
}
