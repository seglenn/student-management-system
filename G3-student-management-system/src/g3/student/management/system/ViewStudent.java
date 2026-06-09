/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g3.student.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author glenn
 */
public class ViewStudent{
    
    private JFrame viewWindow;
    private JLabel photoLabel, titleLabel, separatorLine;
    private JLabel lblStudNo, valStudNo;
    private JLabel nameLabel, nameValue;
    private JLabel emailLabel, emailValue;
    private JLabel programLabel, programValue;
    private JLabel statusLabel, statusValue;
    private JLabel lblDateEnrolled, valDateEnrolled;
    private JButton closeButton;
    private JSeparator line;
    
    public ViewStudent(ImageIcon photoIcon, String studentNumber, String name, 
                            String email, String program, String status, String dateEnrolled) {
        
        
        
        viewWindow = new JFrame();
        viewWindow.setSize(460, 580);
        viewWindow.setLayout(null);
        viewWindow.setLocationRelativeTo(null);
        viewWindow.setTitle("STUDENT DETAILS");
        viewWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewWindow.getContentPane().setBackground(Color.WHITE);

        // ===== PHOTO SECTION =====
        photoLabel = new JLabel();
        photoLabel.setBounds(155, 30, 150, 150);
        photoLabel.setBackground(Color.decode("#e3f2fd"));
        photoLabel.setOpaque(true);
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#1f87e2"), 2));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (photoIcon != null) {
            Image resizedPhoto = photoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(resizedPhoto));
            photoLabel.setText("");
        } else {
            photoLabel.setText("No Photo Available");
            photoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            photoLabel.setForeground(Color.decode("#737373"));
        }
        viewWindow.add(photoLabel);

        // ===== TITLE SECTION =====
        titleLabel = new JLabel("Student Details");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(35, 200, 300, 35);
        viewWindow.add(titleLabel);

        line = new JSeparator();
        line.setBounds(35, 240, 390, 2);
        line.setForeground(new Color(220, 220, 220));
        viewWindow.add(line);

        // ===== STUDENT INFORMATION =====
        // Student No.
        lblStudNo = new JLabel("Student No.:");
        lblStudNo.setForeground(Color.decode("#737373"));
        lblStudNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStudNo.setBounds(35, 255, 130, 28);
        viewWindow.add(lblStudNo);

        valStudNo = new JLabel(studentNumber);
        valStudNo.setForeground(Color.BLACK);
        valStudNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valStudNo.setBounds(175, 255, 240, 28);
        viewWindow.add(valStudNo);

        // Full Name
        nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(Color.decode("#737373"));
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setBounds(35, 293, 130, 28);
        viewWindow.add(nameLabel);

        nameValue = new JLabel(name);
        nameValue.setForeground(Color.BLACK);
        nameValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameValue.setBounds(175, 293, 240, 28);
        viewWindow.add(nameValue);

        // Email
        emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#737373"));
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLabel.setBounds(35, 331, 130, 28);
        viewWindow.add(emailLabel);

        emailValue = new JLabel(email);
        emailValue.setForeground(Color.BLACK);
        emailValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        emailValue.setBounds(175, 331, 240, 28);
        viewWindow.add(emailValue);

        // Program
        programLabel = new JLabel("Program:");
        programLabel.setForeground(Color.decode("#737373"));
        programLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        programLabel.setBounds(35, 369, 130, 28);
        viewWindow.add(programLabel);

        programValue = new JLabel(program);
        programValue.setForeground(Color.BLACK);
        programValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        programValue.setBounds(175, 369, 240, 28);
        viewWindow.add(programValue);

        // Status
        statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.decode("#737373"));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setBounds(35, 407, 130, 28);
        viewWindow.add(statusLabel);

        statusValue = new JLabel(status);
        statusValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        if (status.equals("Active")) {
            statusValue.setForeground(Color.decode("#16a34a"));
        } else {
            statusValue.setForeground(Color.decode("#e53935"));
        }
        statusValue.setBounds(175, 407, 240, 28);
        viewWindow.add(statusValue);

        // Date Enrolled
        lblDateEnrolled = new JLabel("Date Enrolled:");
        lblDateEnrolled.setForeground(Color.decode("#737373"));
        lblDateEnrolled.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDateEnrolled.setBounds(35, 445, 130, 28);
        viewWindow.add(lblDateEnrolled);

        valDateEnrolled = new JLabel(dateEnrolled);
        valDateEnrolled.setForeground(Color.BLACK);
        valDateEnrolled.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valDateEnrolled.setBounds(175, 445, 240, 28);
        viewWindow.add(valDateEnrolled);

        // ===== CLOSE BUTTON =====
        closeButton = new JButton("Close");
        closeButton.setBounds(155, 490, 150, 45);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.decode("#1f87e2"));
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewWindow.add(closeButton);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewWindow.dispose();
            }
        });

        viewWindow.setVisible(true);
    }
}