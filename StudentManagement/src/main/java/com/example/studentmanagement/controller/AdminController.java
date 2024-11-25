package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.repository.CourseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseRepository courseRepository;

    public AdminController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

 @GetMapping("/courses")
public ResponseEntity<?> getAllCourses(HttpServletRequest request) {
    // 打印所有请求头
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        System.out.println(headerName + ": " + request.getHeader(headerName));
    }

    // 打印接收到的 Cookie
    String cookieHeader = request.getHeader("Cookie");
    System.out.println("Received Cookie header: " + cookieHeader);

    // 验证会话是否存在
    HttpSession session = request.getSession(false);
    if (session == null) {
        System.out.println("Access denied: No valid session.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session is invalid or expired.");
    }

    System.out.println("Session ID: " + session.getId());
    List<Course> courses = courseRepository.findAll();
    return ResponseEntity.ok(courses);
}
}
