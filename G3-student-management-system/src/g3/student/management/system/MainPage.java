package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame {

    private JLabel lblLogoName, lblLine;
    private JButton btnDashboard, btnStuds, btnGrades, btnAttendance, btnLogout, selectedButton;
    private JPanel sideBarPanel, contentPanel;
    private CardLayout cardLayout;
    private ImageIcon logoAndName, Line;

    MainPage() {

        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Management System - Group 3");

        // SIDEBAR SETUP
        sideBarPanel = new JPanel();
        sideBarPanel.setBounds(0, 0, 353, 1080);
        sideBarPanel.setBackground(Color.decode("#202c3c"));
        sideBarPanel.setLayout(null);
        add(sideBarPanel);

        // LOAD IMAGES
        logoAndName = new ImageIcon("images/logo-and-name.png");
        Line = new ImageIcon("images/line.png");

        // LOGO DISPLAY
        lblLogoName = new JLabel(logoAndName);
        lblLogoName.setBounds(50, 37, 277, 55);
        sideBarPanel.add(lblLogoName);

        // DECORATIVE LINE
        lblLine = new JLabel(Line);
        lblLine.setBounds(42, 57, 277, 112);
        sideBarPanel.add(lblLine);
               
        // NAVIGATION BUTTONS
        btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(41, 142, 277, 60);
        btnDashboard.setForeground(Color.decode("#b4b4b4"));
        btnDashboard.setBackground(Color.decode("#202c3c"));
        btnDashboard.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        btnDashboard.setFocusPainted(false);
        btnDashboard.setBorderPainted(false);
        sideBarPanel.add(btnDashboard);

        btnStuds = new JButton("Students");
        btnStuds.setBounds(41, 212, 277, 60);
        btnStuds.setForeground(Color.decode("#b4b4b4"));
        btnStuds.setBackground(Color.decode("#202c3c"));
        btnStuds.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        btnStuds.setFocusPainted(false);
        btnStuds.setBorderPainted(false);
        sideBarPanel.add(btnStuds);

        btnGrades = new JButton("Grades");
        btnGrades.setBounds(41, 282, 277, 60);
        btnGrades.setForeground(Color.decode("#b4b4b4"));
        btnGrades.setBackground(Color.decode("#202c3c"));
        btnGrades.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        btnGrades.setFocusPainted(false);
        btnGrades.setBorderPainted(false);
        sideBarPanel.add(btnGrades);

        btnAttendance = new JButton("Attendance");
        btnAttendance.setBounds(41, 352, 277, 60);
        btnAttendance.setForeground(Color.decode("#b4b4b4"));
        btnAttendance.setBackground(Color.decode("#202c3c"));
        btnAttendance.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        btnAttendance.setFocusPainted(false);
        btnAttendance.setBorderPainted(false);
        sideBarPanel.add(btnAttendance);

        // LOGOUT BUTTON
        btnLogout = new JButton("Logout");
        btnLogout.setBounds(41, 930, 277, 60);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(Color.decode("#e53935"));
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 25));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sideBarPanel.add(btnLogout);

        // SET DEFAULT ACTIVE BUTTON
        selectedButton = btnDashboard;
        applyActive(btnDashboard);

        // HOVER EFFECTS
        JButton[] navButtons = {btnDashboard, btnStuds, btnGrades, btnAttendance};
        
        for (JButton button : navButtons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (button != selectedButton) {
                        button.setBackground(Color.decode("#1f87e2"));
                        button.setForeground(Color.WHITE);
                        button.setFont(new Font("Segoe UI", Font.BOLD, 25));
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (button != selectedButton) {
                        button.setBackground(Color.decode("#202c3c"));
                        button.setForeground(Color.decode("#b4b4b4"));
                        button.setFont(new Font("Segoe UI", Font.PLAIN, 25));
                    }
                }
            });
        }

        // CONTENT PANEL (CARD LAYOUT)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(350, 0, 1570, 1084);
        contentPanel.setBackground(Color.decode("#f6f7f9"));
        add(contentPanel);

        // CREATE PANEL INSTANCES
        DashboardPanel dashboardPanel = new DashboardPanel();
        GradesPanel gradesPanel = new GradesPanel();
        AttendancePanel attendancePanel = new AttendancePanel();
        StudentsPanel studentsPanel = new StudentsPanel();

        // LINK PANELS (FOR DATA SHARING)
        studentsPanel.setGradesPanel(gradesPanel);
        studentsPanel.setAttendancePanel(attendancePanel);
        studentsPanel.setDashboardPanel(dashboardPanel);
        attendancePanel.setDashboardPanel(dashboardPanel);

        // ADD PANELS TO CARD LAYOUT
        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(studentsPanel, "Students");
        contentPanel.add(gradesPanel, "Grades");
        contentPanel.add(attendancePanel, "Attendance");

        // SHOW DASHBOARD BY DEFAULT
        cardLayout.show(contentPanel, "Dashboard");

        // DASHBOARD BUTTON ACTION
        btnDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Dashboard");
                setSelectedButton(btnDashboard);
                dashboardPanel.refreshStats();
            }
        });

        // STUDENTS BUTTON ACTION
        btnStuds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Students");
                setSelectedButton(btnStuds);
            }
        });

        // GRADES BUTTON ACTION
        btnGrades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Grades");
                setSelectedButton(btnGrades);
            }
        });

        // ATTENDANCE BUTTON ACTION
        btnAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Attendance");
                setSelectedButton(btnAttendance);
            }
        });

        // LOGOUT BUTTON ACTION
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainPage.this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);
                    JOptionPane.showMessageDialog(null, "You have been successfully logged out.", "Logout Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Logout cancelled");
                }
            }
        });
        
        setVisible(true);
    }

    // APPLY ACTIVE BUTTON STYLE
    private void applyActive(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 25));
    }

    // APPLY INACTIVE BUTTON STYLE
    private void applyInactive(JButton button) {
        button.setBackground(Color.decode("#202c3c"));
        button.setForeground(Color.decode("#b4b4b4"));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 25));
    }

    // UPDATE SELECTED BUTTON
    public void setSelectedButton(JButton clicked) {
        if (selectedButton != null) {
            applyInactive(selectedButton);
        }
        selectedButton = clicked;
        applyActive(selectedButton);
    }
}