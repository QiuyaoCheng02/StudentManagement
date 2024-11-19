package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Grade;
import com.example.studentmanagement.repository.GradeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GradeController {
    private final GradeRepository gradeRepository;

    public GradeController(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @GetMapping("/grades/by-student")
    public List<Grade> getGradesByStudent(@RequestParam int studentID) {
        return gradeRepository.findById_StudentId(studentID);
    }

    @GetMapping("/grades/by-course")
    public List<Grade> getGradesByCourse(@RequestParam int courseID) {
        return gradeRepository.findById_CourseId(courseID);
    }

    @GetMapping("/grades")
    public Grade getGradeByStudentAndCourse(@RequestParam int studentID, @RequestParam int courseID) {
        return gradeRepository.findById_StudentIdAndId_CourseId(studentID, courseID);
    }
}
