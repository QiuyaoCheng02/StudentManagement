package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.UserEntity;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> request) {
        System.out.println("Received authenticate request");
        String username = request.get("username");
        String password = request.get("password");
   

        // 打印接收到的 username 和 password
        System.out.println("Received username: " + username);
        System.out.println("Received password: " + password);

        Optional<UserEntity> userOpt = userRepository.findByUsernameAndPassword(username, password);

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
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
