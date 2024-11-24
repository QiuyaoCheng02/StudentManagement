package com.example.studentmanagement.events;
import javax.swing.*;

import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.gui.AdminDashboard;
import com.example.studentmanagement.gui.CoursesManagementPage;
import com.example.studentmanagement.gui.LoginPage;
import com.example.studentmanagement.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.List;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminDashboardEvents implements ActionListener {
    private final AdminDashboard adminDashboard;

    public AdminDashboardEvents(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();

        if ("Courses Management".equals(buttonText)) {
            openCoursesManagement();
        }
    }
    private void openCoursesManagement() {
        try {
            URL url = new URL("http://localhost:8080/admin/courses");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            // 添加 Basic Auth 认证
            String auth = "username:password"; // 替换为真实的用户名和密码
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
    
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                }
    
                new CoursesManagementPage(response.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Failed to fetch courses. Response code: " + responseCode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching courses: " + ex.getMessage());
        }
    }
    
}
