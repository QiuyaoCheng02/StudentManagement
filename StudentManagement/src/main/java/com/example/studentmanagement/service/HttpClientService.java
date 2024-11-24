package com.example.studentmanagement.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {

    public String authenticate(String username, String password) throws IOException {
        URL url = new URL("http://localhost:8080/authenticate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 准备 JSON 请求体
        String jsonInputString = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(jsonInputString.getBytes("utf-8"));
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject.getString("role"); // 返回用户角色
            }
        } else if (responseCode == 401) {
            throw new IllegalArgumentException("Invalid username or password.");
        } else {
            throw new IllegalArgumentException("Unexpected error. Response code: " + responseCode);
        }
    }
}
