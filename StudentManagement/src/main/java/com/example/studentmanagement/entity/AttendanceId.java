package com.example.studentmanagement.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AttendanceId implements Serializable {
    private int studentID;
    private int courseID;
    private Date date; // 修改为 Date 类型

    public AttendanceId() {}

    public AttendanceId(int studentID, int course_id, Date date) {
        this.studentID = studentID;
        this.courseID = course_id;
        this.date = date;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getcourse_id() {
        return courseID;
    }

    public void setcourse_id(int course_id) {
        this.courseID = course_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceId that = (AttendanceId) o;
        return Objects.equals(studentID, that.studentID) &&
                Objects.equals(courseID, that.courseID) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, courseID, date);
    }
}
