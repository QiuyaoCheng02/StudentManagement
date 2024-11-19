package com.example.studentmanagement.entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int billID;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @Column(name = "bill_amount", nullable = false)
    private double billAmount;


    @Column(name = "due_date")
    private LocalDate dueDate;

    public enum PaidStatus {
        PAID,
        UNPAID,
        PENDING
    }

    @Column(name = "paid_status", columnDefinition = "ENUM('PAID', 'UNPAID', 'PENDING')")
    @Enumerated(EnumType.STRING)
    private PaidStatus paidStatus;



    // Getters and Setters
    public int getbill_id() {
        return billID;
    }

    public void setbill_id(int billID) {
        this.billID = billID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getbill_amount() {
        return billAmount;
    }

    public void setbill_amount(double bill_amount) {
        this.billAmount = bill_amount;
    }

    public LocalDate getdue_date() {
        return dueDate;
    }

    public void setdue_date(LocalDate due_date) {
        this.dueDate = due_date;
    }

    public PaidStatus getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(PaidStatus paidStatus) {
        this.paidStatus = paidStatus;
    }
}
