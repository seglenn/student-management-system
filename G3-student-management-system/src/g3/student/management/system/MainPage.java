package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame {
    
    private JLabel lblTitleOne, lblTitleTwo; 
    private JButton btnDashboard, btnStuds, btnGrades, btnAttendance, selectedButton;
    private JPanel sideBarPanel, contentPanel;
    private CardLayout cardLayout;
    
    MainPage() {
        
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Management System - Group 3");
        
        lblTitleOne = new JLabel("Student");
        lblTitleOne.setBounds(100, 50, 73, 31);
        lblTitleOne.setForeground(Color.WHITE);
        lblTitleOne.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblTitleOne);
        
        lblTitleTwo = new JLabel("Management System");
        lblTitleTwo.setBounds(100, 65, 179, 31);
        lblTitleTwo.setForeground(Color.WHITE);
        lblTitleTwo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblTitleTwo);
        
        sideBarPanel = new JPanel();
        sideBarPanel.setBounds(0, 0, 353, 1080);
        sideBarPanel.setBackground(Color.decode("#202c3c"));
        sideBarPanel.setLayout(null);
        add(sideBarPanel);
        
        btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(41, 142, 277, 60);
        btnDashboard.setForeground(Color.decode("#b4b4b4"));
        btnDashboard.setBackground(Color.decode("#202c3c"));
        btnDashboard.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnDashboard.setFocusPainted(false);
        btnDashboard.setBorderPainted(false);
        sideBarPanel.add(btnDashboard);
        
        btnStuds = new JButton("Students");
        btnStuds.setBounds(41, 212, 277, 60);
        btnStuds.setForeground(Color.decode("#b4b4b4"));
        btnStuds.setBackground(Color.decode("#202c3c"));
        btnStuds.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnStuds.setFocusPainted(false);
        btnStuds.setBorderPainted(false);
        sideBarPanel.add(btnStuds);
        
        btnGrades = new JButton("Grades");
        btnGrades.setBounds(41, 282, 277, 60);
        btnGrades.setForeground(Color.decode("#b4b4b4"));
        btnGrades.setBackground(Color.decode("#202c3c"));
        btnGrades.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnGrades.setFocusPainted(false);
        btnGrades.setBorderPainted(false);
        sideBarPanel.add(btnGrades);
        
        btnAttendance = new JButton("Attendance");
        btnAttendance.setBounds(41, 352, 277, 60);
        btnAttendance.setForeground(Color.decode("#b4b4b4"));
        btnAttendance.setBackground(Color.decode("#202c3c"));
        btnAttendance.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnAttendance.setFocusPainted(false);
        btnAttendance.setBorderPainted(false);
        sideBarPanel.add(btnAttendance);
        
        selectedButton = btnDashboard;
        selectedButton.setBackground(new Color(52, 152, 219));
        selectedButton.setForeground(Color.WHITE);
        
        JButton[] buttons = {btnDashboard, btnStuds, btnGrades, btnAttendance};
        
        for (JButton button : buttons) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (button != selectedButton) {
                        button.setBackground(Color.decode("#1f87e2"));
                        button.setForeground(Color.WHITE);
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (button != selectedButton) {
                        button.setBackground(Color.decode("#202c3c"));
                        button.setForeground(Color.decode("#b4b4b4"));
                    }
                }
            });
        }
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(350, 0, 1570, 1084);
        contentPanel.setBackground(Color.decode("#f6f7f9"));
        add(contentPanel);
        
        contentPanel.add(new DashboardPanel(), "Dashboard");
        contentPanel.add(new StudentsPanel(), "Students");
        contentPanel.add(new GradesPanel(), "Grades");
        contentPanel.add(new AttendancePanel(), "Attendance");
        
        cardLayout.show(contentPanel, "Dashboard");
        
        btnDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Dashboard");
                setSelectedButton(btnDashboard);
            }
        });
        
        btnStuds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Students");
                setSelectedButton(btnStuds);
            }
        });
        
        btnGrades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Grades");
                setSelectedButton(btnGrades);
            }
        });
        
        btnAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Attendance");
                setSelectedButton(btnAttendance);
            }
        });
        
        setVisible(true);
    }
    
    public void setSelectedButton(JButton clicked) {
        if (selectedButton != null) {
            selectedButton.setBackground(Color.decode("#202c3c"));
            selectedButton.setForeground(Color.decode("#b4b4b4"));
        }
        selectedButton = clicked;
        selectedButton.setBackground(new Color(52, 152, 219));
        selectedButton.setForeground(Color.WHITE);
    }
}