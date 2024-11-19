package com.example.studentmanagement.repository;



import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 根据 userId 和 password 查询用户
    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.password = :password")
    Optional<User> findByUserIdAndPassword(@Param("userId") int userId, @Param("password") String password);
}
