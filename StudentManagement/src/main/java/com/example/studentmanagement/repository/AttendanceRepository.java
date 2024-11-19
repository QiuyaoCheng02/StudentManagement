package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Attendance;
import com.example.studentmanagement.entity.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    // 按日期查找考勤记录
    List<Attendance> findByDate(Date date);
}
