package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, Object> request) {
        int userId=0;
        userId = (int) request.get("userId");  // 这里获取 userId
        String rawPassword = (String) request.get("password");  // 获取 password

        if (userId == 0 || rawPassword == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "UserId or password cannot be null");
            return ResponseEntity.badRequest().body(response);
        }

        // 查找用户并验证密码
        Optional<User> userOpt = userRepository.findByUserIdAndPassword(userId, rawPassword);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, String> response = new HashMap<>();
            response.put("role", user.getRole());  // 返回用户角色
            return ResponseEntity.ok(response);
        }

        // 如果用户名或密码错误
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}