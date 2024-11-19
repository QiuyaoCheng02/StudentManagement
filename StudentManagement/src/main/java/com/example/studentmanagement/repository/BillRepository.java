package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Bill;
import com.example.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    // 查找特定学生的账单
    List<Bill> findByStudent(Student student);

    // 查找特定支付状态的账单
    List<Bill> findByPaidStatus(String paidStatus);
}
