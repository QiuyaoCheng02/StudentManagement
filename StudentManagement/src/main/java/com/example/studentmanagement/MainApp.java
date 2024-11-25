package com.example.studentmanagement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.studentmanagement.gui.LoginPage;

import javax.swing.*;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        // 启动 Spring 应用上下文
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);

        // 手动创建 LoginPage 实例
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
