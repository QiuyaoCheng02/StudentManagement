package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // 根据 course_id 查找单个课程
    Optional<Course> findByCourseId(int courseId);

    // 根据 course_name 查找单个课程
    Optional<Course> findByCourseName(String courseName);

    // 根据 course_name 查找包含关键字的课程列表
    List<Course> findByCourseNameContainingIgnoreCase(String keyword);

    // 按 credits 大于等于某值的课程列表
    List<Course> findByCreditsGreaterThanEqual(int credits);
    List<Course> findAllByOrderByCourseIdAsc(); //
    // 根据课程名和学分查找
    List<Course> findByCourseNameAndCredits(String courseName, int credits);
}
