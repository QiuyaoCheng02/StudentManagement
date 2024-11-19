package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@IdClass(AttendanceId.class)
public class Attendance {
    @Id
    @Column(name = "student_id", nullable = false)
    private int studentID;

    @Id
    @Column(name = "course_id", nullable = false)
    private int courseID;

    @Id
    @Temporal(TemporalType.DATE) // 指定日期格式
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false)
    private Course course;

    // Getters 和 Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
