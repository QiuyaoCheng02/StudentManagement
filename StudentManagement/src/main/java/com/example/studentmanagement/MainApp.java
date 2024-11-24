package com.example.studentmanagement;


import com.example.studentmanagement.gui.LoginPage;

import javax.swing.SwingUtilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);

        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = context.getBean(LoginPage.class);
            loginPage.setVisible(true);
        });
    }
}

