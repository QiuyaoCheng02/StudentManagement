package com.example.studentmanagement.gui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    public StudentDashboard() {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        JLabel label = new JLabel("Welcome Student!", JLabel.CENTER);
        add(label, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
