package com.example.studentmanagement.config;

import com.example.studentmanagement.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.Collections;

//@Component
//public class PlainTextAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserRepository userRepository;
//
//    public PlainTextAuthenticationProvider(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // 从 Authentication 对象中获取 userId 和 password
//        Integer userId;
//        try {
//            userId = Integer.parseLong(authentication.getPrincipal()); // 假设 userId 作为 username 字段传入
//        } catch (NumberFormatException e) {
//            throw new BadCredentialsException("Invalid userId format");
//        }
//        String password = authentication.getCredentials().toString();
//
//        // 查询数据库
//        com.example.studentmanagement.entity.User user = userRepository.findByUserIdAndPassword(userId, password)
//                .orElseThrow(() -> new BadCredentialsException("User ID or password is incorrect"));
//
//        System.out.println("User authenticated: " + user.getUserId() + ", Role: " + user.getRole());
//
//        // 返回认证结果
//        UserDetails userDetails = new User(userId.toString(), password, Collections.singleton(() -> user.getRole()));
//        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
