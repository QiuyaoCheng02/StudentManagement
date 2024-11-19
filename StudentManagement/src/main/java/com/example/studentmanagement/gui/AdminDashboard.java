package com.example.studentmanagement.gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        JLabel label = new JLabel("Welcome Admin!", JLabel.CENTER);
        add(label, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

