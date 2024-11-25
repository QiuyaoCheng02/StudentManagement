package com.example.studentmanagement.gui;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.studentmanagement.events.AdminDashboardEvents;
import com.example.studentmanagement.gui.Admin.AdminDashboard;
import com.example.studentmanagement.gui.Admin.CoursesManagementPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginPage extends JFrame {
    private String sessionId;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel userNameErrorField, passwordErrorField, loginErrorField;

    public LoginPage() {
        setTitle("Login Page");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        initializeComponents();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void initializeComponents() {
        // Fonts
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font hintFont = new Font("Arial", Font.PLAIN, 14);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel pageLabel = new JLabel("Login to Your Account");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(pageLabel, gbc);

        // User ID Label
        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(userNameLabel, gbc);

        // User ID Field
        userNameField = new JTextField(15);
        userNameField.setFont(labelFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(userNameField, gbc);

        // User ID Error Field
        userNameErrorField = new JLabel(" ");
        userNameErrorField.setFont(hintFont);
        userNameErrorField.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(userNameErrorField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(15);
        passwordField.setFont(labelFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // Password Error Field
        passwordErrorField = new JLabel(" ");
        passwordErrorField.setFont(hintFont);
        passwordErrorField.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(passwordErrorField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.addActionListener(new LoginActionListener());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Login Error Field
        loginErrorField = new JLabel(" ");
        loginErrorField.setFont(hintFont);
        loginErrorField.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(loginErrorField, gbc);
    }

    /**
     * ActionListener for the login button.
     */
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userNameErrorField.setText(" ");
            passwordErrorField.setText(" ");
            loginErrorField.setText(" ");

            String username = userNameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty()) {
                userNameErrorField.setText("Username cannot be empty.");
                return;
            }
            if (password.isEmpty()) {
                passwordErrorField.setText("Password cannot be empty.");
                return;
            }

            try {
                URL url = new URL("http://localhost:8080/authenticate");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // 准备 JSON 请求体
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);
                String jsonInputString = json.toString();

      
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    // 提取 JSESSIONID
                    String setCookie = connection.getHeaderField("Set-Cookie");
                    if (setCookie != null && setCookie.contains("JSESSIONID")) {
                        sessionId = setCookie.split(";")[0]; // 提取 JSESSIONID
                        System.out.println("Login Page Session ID: " + sessionId);
                    } else {
                        System.out.println("No Session ID received.");
                    }
                
                    // 读取服务器返回的 JSON 响应
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }
                
                    String jsonResponse = response.toString();
                    System.out.println("JSON Response: " + jsonResponse);
                
                    // 提取角色信息
                    String role = getRoleFromResponse(jsonResponse);
                
                    JOptionPane.showMessageDialog(LoginPage.this, "Login Successful!");
                
                    dispose(); // 关闭登录窗口
                
                    // 根据角色跳转到相应的 Dashboard，并传递 sessionId
                    if ("ROLE_ADMIN".equals(role)) {
                        new AdminDashboard(sessionId);
                    } else if ("ROLE_STUDENT".equals(role)) {
                        //new StudentDashboard(sessionId);
                    } else {
                        JOptionPane.showMessageDialog(null, "Unknown role: " + role);
                    }}
                } catch (Exception ex) {
                ex.printStackTrace();
                loginErrorField.setText("Error connecting to server.");
            }
        }

        private String getRoleFromResponse(String jsonResponse) throws Exception {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            return jsonObject.getString("role"); // 从 JSON 中提取角色信息
        }
    }        
}