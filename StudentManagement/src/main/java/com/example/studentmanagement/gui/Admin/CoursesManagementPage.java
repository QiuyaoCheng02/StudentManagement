package com.example.studentmanagement.gui.Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class CoursesManagementPage extends JFrame {
    private final String sessionId; // 保存 sessionId
    private DefaultTableModel tableModel;

    public CoursesManagementPage(String jsonResponse, String sessionId) {
        this.sessionId = sessionId; // 初始化 sessionId
        setTitle("Courses Management");
        setSize(1000, 600); // 调整宽度以适应按钮
        setLayout(new BorderLayout());
        initializeUI(jsonResponse);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);
    }

    private void initializeUI(String jsonResponse) {
        // 创建表格模型
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // 只允许编辑 "Edit" 列
            }
        };
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Credits");
        tableModel.addColumn("Action"); // 添加 "Edit" 列

        // 填充表格数据
        loadCoursesData(jsonResponse);

        // 创建表格并设置模型
        JTable table = new JTable(tableModel);

        // 自定义渲染器，使 "Edit" 列显示按钮
        table.getColumn("Action").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JButton button = new JButton("Edit");
            if (isSelected) {
                button.setBackground(table1.getSelectionBackground());
                button.setForeground(table1.getSelectionForeground());
            } else {
                button.setBackground(UIManager.getColor("Button.background"));
                button.setForeground(table1.getForeground());
            }
            return button;
        });

        // 添加按钮点击事件
        table.getColumn("Action").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JButton button = new JButton("Edit");
                button.addActionListener(e -> {
                    int courseId = (int) tableModel.getValueAt(row, 0); // 获取 Course ID
                    String courseName = (String) tableModel.getValueAt(row, 1);
                    String description = (String) tableModel.getValueAt(row, 2);
                    int credits = (int) tableModel.getValueAt(row, 3);

                    // 打开编辑对话框
                    openEditDialog(courseId, courseName, description, credits, row);
                });
                return button;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(new JLabel("Courses Management", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose()); // 返回到上一级窗口
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadCoursesData(String jsonResponse) {
        try {
            JSONArray courses = new JSONArray(jsonResponse);
            for (int i = 0; i < courses.length(); i++) {
                JSONObject course = courses.getJSONObject(i);
                tableModel.addRow(new Object[]{
                        course.getInt("courseId"),
                        course.getString("courseName"),
                        course.optString("description", "N/A"),
                        course.getInt("credits"),
                        "Edit" // 按钮文字
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing courses data: " + e.getMessage());
        }
    }

    private void openEditDialog(int courseId, String courseName, String description, int credits, int row) {
        JTextField courseNameField = new JTextField(courseName);
        JTextField descriptionField = new JTextField(description);
        JTextField creditsField = new JTextField(String.valueOf(credits));

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Course Name:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Credits:"));
        panel.add(creditsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newCourseName = courseNameField.getText();
            String newDescription = descriptionField.getText();
            int newCredits;
            try {
                newCredits = Integer.parseInt(creditsField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid credits value.");
                return;
            }

            // 更新数据库
            if (updateCourseInDatabase(courseId, newCourseName, newDescription, newCredits)) {
                // 更新表格数据
                tableModel.setValueAt(newCourseName, row, 1);
                tableModel.setValueAt(newDescription, row, 2);
                tableModel.setValueAt(newCredits, row, 3);
                JOptionPane.showMessageDialog(this, "Course updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update course.");
            }
        }
    }

    private boolean updateCourseInDatabase(int courseId, String courseName, String description, int credits) {
        try {
            URL url = new URL("http://localhost:8080/admin/courses/" + courseId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Cookie", sessionId); // 添加会话 ID

            // 构建 JSON 数据
            JSONObject json = new JSONObject();
            json.put("courseName", courseName);
            json.put("description", description);
            json.put("credits", credits);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.toString().getBytes());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true; // 更新成功
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update course. Response code: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating course: " + e.getMessage());
            return false;
        }
    }
}
