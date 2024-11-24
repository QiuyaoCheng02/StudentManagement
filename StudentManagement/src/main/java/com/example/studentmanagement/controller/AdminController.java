package com.example.studentmanagement.controller;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.repository.CourseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseRepository courseRepository;

    public AdminController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // 返回所有课程
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll(); // 查询所有课程
    }
}
