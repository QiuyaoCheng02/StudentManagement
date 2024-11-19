package com.example.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 处理 GET 请求 "/hello"
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Student Management System!";
    }
}