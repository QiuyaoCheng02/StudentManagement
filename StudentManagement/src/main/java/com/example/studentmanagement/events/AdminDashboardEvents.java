package com.example.studentmanagement.events;
import javax.swing.*;

import com.example.studentmanagement.gui.Admin.AdminDashboard;
import com.example.studentmanagement.gui.Admin.CoursesManagementPage;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminDashboardEvents implements ActionListener {
    private final AdminDashboard adminDashboard;
    private final String sessionId;

    public AdminDashboardEvents(AdminDashboard adminDashboard, String sessionId) {
        this.adminDashboard = adminDashboard;
        this.sessionId = sessionId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取触发事件的按钮
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();

        // 根据按钮文字决定操作
        if ("Courses Management".equals(button.getText())) {
            String jsonResponse = fetchCoursesFromServer(); // 获取课程数据
            if (jsonResponse != null) {
                new CoursesManagementPage(jsonResponse, sessionId); // 打开课程管理页面
            }
        }else if ("Students Management".equals(buttonText)) {
            JOptionPane.showMessageDialog(adminDashboard, "Students Management is not implemented yet.");
        } else if ("Back".equals(buttonText)) {
            adminDashboard.dispose(); // 关闭 AdminDashboard
            JOptionPane.showMessageDialog(null, "Returning to previous menu.");
        } else if ("Logout".equals(buttonText)) {
            JOptionPane.showMessageDialog(null, "Logged out successfully.");
            System.exit(0); // 退出程序
        } else {
            JOptionPane.showMessageDialog(adminDashboard, "Unknown action: " + buttonText);
        }
    }

    private String fetchCoursesFromServer() {
        try {
            URL url = new URL("http://localhost:8080/admin/courses");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            // 设置 Cookie，包含 sessionId
            if (sessionId != null) {
                connection.setRequestProperty("Cookie", sessionId);
                System.out.println("Sending sessionId: " + sessionId);
            } else {
                System.out.println("Session ID is null!");
            }
    
            // 打印请求头
            connection.getRequestProperties().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });
    
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                }
                return response.toString();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to fetch courses. Response code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching courses: " + e.getMessage());
            return null;
        }
    }
}
