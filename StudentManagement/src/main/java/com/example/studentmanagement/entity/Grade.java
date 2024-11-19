package com.example.studentmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    @EmbeddedId
    private GradeId id; // 嵌入式主键

    @Column(nullable = false)
    private int grade;

    @ManyToOne
    @MapsId("studentId") // 改为驼峰命名
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId") // 改为驼峰命名
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    // Getters 和 Setters
    public GradeId getId() {
        return id;
    }

    public void setId(GradeId id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
