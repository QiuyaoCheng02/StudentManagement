package com.example.studentmanagement.gui;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}
