package com.example.studentmanagement.gui;

import javax.swing.*;

import com.example.studentmanagement.events.AdminDashboardEvents;

import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JLabel titleLabel;
    private JButton courseBtn, studentBtn, backButton, logoutButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setLayout(new GridBagLayout());
        setAdminPageJPanel();
        setSize(1000, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setAdminPageJPanel() {
        AdminDashboardEvents adminDashboardEvents = new AdminDashboardEvents(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        Font titleFont = new Font("Arial", Font.PLAIN, 22);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        titleLabel = new JLabel("Welcome to Admin Dashboard");
        titleLabel.setFont(titleFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 30, 0);
        add(titleLabel, gbc);

        courseBtn = new JButton("Courses Management");
        courseBtn.setFont(buttonFont);
        courseBtn.setPreferredSize(new Dimension(250, 40));
        courseBtn.addActionListener(adminDashboardEvents);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(courseBtn, gbc);

        studentBtn = new JButton("Students Management");
        studentBtn.setFont(buttonFont);
        studentBtn.setPreferredSize(new Dimension(250, 40));
        studentBtn.addActionListener(adminDashboardEvents);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(studentBtn, gbc);

        backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(new Dimension(110, 30));
        backButton.addActionListener(adminDashboardEvents);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(backButton, gbc);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(buttonFont);
        logoutButton.setPreferredSize(new Dimension(110, 30));
        logoutButton.addActionListener(adminDashboardEvents);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 50, 10, 0);
        add(logoutButton, gbc);
    }
}
