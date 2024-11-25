package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.UserEntity;
import com.example.studentmanagement.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest) {
        System.out.println("Received authenticate request");
        String username = request.get("username");
        String password = request.get("password");

        Optional<UserEntity> userOpt = userRepository.findByUsernameAndPassword(username, password);

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();

            // 创建会话
            HttpSession session = httpServletRequest.getSession();
            System.out.println("Session created with ID: " + session.getId());

            // 将用户保存到 SecurityContext
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, List.of(new SimpleGrantedAuthority(user.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 返回成功响应
            Map<String, String> response = new HashMap<>();
            response.put("role", user.getRole());
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        }

        // 返回 401 Unauthorized
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}