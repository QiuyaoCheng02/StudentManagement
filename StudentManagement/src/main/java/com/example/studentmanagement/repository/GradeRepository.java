package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Grade;
import com.example.studentmanagement.entity.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GradeRepository extends JpaRepository<Grade, GradeId> {
    // 查询某学生的所有成绩
    List<Grade> findById_StudentId(int studentId);

    // 查询某课程的所有成绩
    List<Grade> findById_CourseId(int courseId);

    // 查询某学生在某课程的成绩
    Grade findById_StudentIdAndId_CourseId(int studentId, int courseId);
}
