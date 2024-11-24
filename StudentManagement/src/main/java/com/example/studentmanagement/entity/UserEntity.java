package com.example.studentmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // 对应数据库表名
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // 数据库字段名为 user_id
    private Integer userId;

    @Column(nullable = false, unique = true) // 数据库字段名为 username
    private String username;

    @Column(nullable = false) // 数据库字段名为 password
    private String password;

    @Column(nullable = false) // 数据库字段名为 role
    private String role;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
