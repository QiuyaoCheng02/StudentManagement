package com.example.studentmanagement;


import com.example.studentmanagement.gui.LoginPage;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        // 启动 Swing 登录界面
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
