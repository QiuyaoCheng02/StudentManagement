package com.example.studentmanagement.gui;

import org.json.JSONObject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginPage extends JFrame {
    private JTextField userIdField;  // 这里改为使用 userId
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // userId label and field (修改为 userId)
        JLabel userIdLabel = new JLabel("User ID:");
        userIdField = new JTextField();
        add(userIdLabel);
        add(userIdField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        add(new JLabel()); // Placeholder for layout alignment
        add(loginButton);

        // Add ActionListener to login button
        loginButton.addActionListener(new LoginActionListener());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * ActionListener for the login button.
     */
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取 userId 输入的值，转换为 Integer 类型
            Integer userId = null;
            try {
                userId = Integer.parseInt(userIdField.getText());  // 转换为 Integer
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid User ID format.");
                return;  // 如果转换失败，退出该方法
            }

            String password = new String(passwordField.getPassword());

            try {
                URL url = new URL("http://localhost:8080/authenticate");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Prepare JSON request body
                String jsonInputString = "{\"userId\":" + userId + ",\"password\":\"" + password + "\"}";
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                System.out.println("Sending request to server...");
                int responseCode = connection.getResponseCode();
                System.out.println("Response code: " + responseCode);

                if (responseCode == 200) {
                    String role = getRoleFromResponse(connection);
                    JOptionPane.showMessageDialog(LoginPage.this, "Login Successful!");

                    dispose();
                    if ("ROLE_ADMIN".equals(role)) {
                        new AdminDashboard();
                    } else if ("ROLE_STUDENT".equals(role)) {
                        new StudentDashboard();
                    }
                } else {
                    // If response code is not 200, handle error and avoid reading input stream
                    System.err.println("Error response: " + responseCode);
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid Credentials. Response code: " + responseCode);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LoginPage.this, "Error connecting to server.");
            }
        }

        // Parse the user's role from the server's JSON response
        private String getRoleFromResponse(HttpURLConnection connection) throws Exception {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // 打印后端返回内容，用于调试
                System.out.println("Response from server: " + response);

                // 检查是否是 JSON 格式
                if (!response.toString().startsWith("{")) {
                    throw new IllegalStateException("Unexpected response format: " + response);
                }

                // 解析 JSON 响应
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject.getString("role");
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}

