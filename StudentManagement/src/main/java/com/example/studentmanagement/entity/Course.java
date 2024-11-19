package com.example.studentmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    private String description;
    private int credits;

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course_id) {
        this.courseId = course_id;
    }

    public String getcourse_name() {
        return courseName;
    }

    public void setcourse_name(String course_name) {
        this.courseName = course_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
