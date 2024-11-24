package com.example.studentmanagement.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;

public class CoursesManagementPage extends JFrame {

    public CoursesManagementPage(String jsonResponse) {
        setTitle("Courses Management");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // 创建表格模型
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Credits");

        // 填充表格数据
        try {
            JSONArray courses = new JSONArray(jsonResponse);
            for (int i = 0; i < courses.length(); i++) {
                JSONObject course = courses.getJSONObject(i);
                tableModel.addRow(new Object[]{
                        course.getInt("courseId"),
                        course.getString("courseName"),
                        course.optString("description", "N/A"),
                        course.getInt("credits")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing courses data: " + e.getMessage());
        }

        // 创建表格并设置模型
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(new JLabel("Courses Management", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose()); // 关闭当前窗口
        add(backButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);
    }
}
