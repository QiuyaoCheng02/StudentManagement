package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Enrollment;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    // 查找学生的选课信息
    List<Enrollment> findByStudent(Student student);

    // 查找课程的学生
    List<Enrollment> findByCourse(Course course);
}
