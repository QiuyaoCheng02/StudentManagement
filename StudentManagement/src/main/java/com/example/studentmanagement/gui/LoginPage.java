package com.example.studentmanagement.gui;

import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginPage extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JLabel userIdErrorField, passwordErrorField, loginErrorField;

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
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(userIdLabel, gbc);

        // User ID Field
        userIdField = new JTextField(15);
        userIdField.setFont(labelFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(userIdField, gbc);

        // User ID Error Field
        userIdErrorField = new JLabel(" ");
        userIdErrorField.setFont(hintFont);
        userIdErrorField.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(userIdErrorField, gbc);

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
            // Reset error messages
            userIdErrorField.setText(" ");
            passwordErrorField.setText(" ");
            loginErrorField.setText(" ");

            Integer userId = null;
            try {
                userId = Integer.parseInt(userIdField.getText().trim());
            } catch (NumberFormatException ex) {
                userIdErrorField.setText("Invalid User ID format.");
                return;
            }

            String password = new String(passwordField.getPassword()).trim();
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
                    loginErrorField.setText("Invalid Credentials. Response code: " + responseCode);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                loginErrorField.setText("Error connecting to server.");
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

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject.getString("role");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
