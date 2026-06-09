/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g3.student.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import java.util.HashSet;

/**
 *
 * @author glenn
 */
public class DashboardPanel extends JPanel implements ActionListener {
    
    // ===== FIELDS / VARIABLES =====
    private JLabel lblTitle, lblSubtxt, imgDisplay, lblStudIcon;
    private JLabel lblSchedule, lblMyTask, lblTaskIcon, lblAttIcon, lblProgressPercent;
    private JLabel lblTotalStud, lblAttRate, lblTaskProg, lblAttendanceRate, lblNoStuds, lblSched;
    private JPanel pnlStud, pnlAtt, pnlTask, pnlSched;
    private ImageIcon imgSched, imgDashOne, imgStudIcon, imgAttIcon, imgTaskIcon, imgMyTask;
    
    // Task panel components
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JScrollPane scrollPane;
    private HashSet<String> taskSet;
    private JButton btnAdd, btnDelete, btnClear;
    private JTextField tfTaskInput;
    private JPanel taskPanel;
    
    public DashboardPanel() {
        
        setLayout(null);
        setBackground(new Color(244, 245, 247));
        
        // Initialize task tracking
        taskSet = new HashSet<>();
        listModel = new DefaultListModel<>();
        
        // ===== LOAD IMAGES =====
        imgStudIcon = new ImageIcon("images/student-icon.png");
        imgAttIcon = new ImageIcon("images/att-icon.png");
        imgTaskIcon = new ImageIcon("images/task-icon.png");
        imgMyTask = new ImageIcon("images/mytask-icon.png");
        imgSched = new ImageIcon("images/sched-icon.png");
        
        // ===== ICON LABELS =====
        lblStudIcon = new JLabel(imgStudIcon);
        lblStudIcon.setBounds(385, 250, 89, 78);
        add(lblStudIcon);
        
        lblAttIcon = new JLabel(imgAttIcon);
        lblAttIcon.setBounds(891, 250, 83, 85);
        add(lblAttIcon);
        
        lblTaskIcon = new JLabel(imgTaskIcon);
        lblTaskIcon.setBounds(1403, 254, 77, 84);
        add(lblTaskIcon);
        
        lblMyTask = new JLabel(imgMyTask);
        lblMyTask.setBounds(69, 432, 37, 51);
        add(lblMyTask);
        
        lblSchedule = new JLabel(imgSched);
        lblSchedule.setBounds(803, 430, 91, 76);
        add(lblSchedule);
        
        // ===== TITLE SECTION =====
        lblTitle = new JLabel("Dashboard");
        lblTitle.setBounds(45, 87, 230, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel("Overview");
        lblSubtxt.setBounds(45, 137, 104, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        // ===== STATISTICS NUMBERS =====
        lblNoStuds = new JLabel("0");
        lblNoStuds.setBounds(67, 262, 197, 75);
        lblNoStuds.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblNoStuds.setForeground(Color.BLACK);
        add(lblNoStuds);
        
        lblProgressPercent = new JLabel("0%");
        lblProgressPercent.setBounds(1079, 262, 197, 75);
        lblProgressPercent.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblProgressPercent.setForeground(Color.decode("#16a55d"));
        add(lblProgressPercent);
        
        lblAttendanceRate = new JLabel("0%");
        lblAttendanceRate.setBounds(576, 262, 197, 75);
        lblAttendanceRate.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblAttendanceRate.setForeground(Color.decode("#f16c56"));
        add(lblAttendanceRate);
        
        // ===== STATISTICS LABELS =====
        lblTotalStud = new JLabel("TOTAL STUDENTS");
        lblTotalStud.setBounds(69, 227, 183, 27);
        lblTotalStud.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTotalStud.setForeground(Color.decode("#737373"));
        add(lblTotalStud);
        
        lblAttRate = new JLabel("ATTENDANCE RATE");
        lblAttRate.setBounds(576, 227, 183, 27);
        lblAttRate.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblAttRate.setForeground(Color.decode("#737373"));
        add(lblAttRate);
        
        lblTaskProg = new JLabel("TASK PROGRESS");
        lblTaskProg.setBounds(1082, 227, 183, 27);
        lblTaskProg.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTaskProg.setForeground(Color.decode("#737373"));
        add(lblTaskProg);
        
        // ===== CARD PANELS (Backgrounds) =====
        pnlStud = new JPanel();
        pnlStud.setBounds(43, 203, 464, 168);
        pnlStud.setBackground(Color.WHITE);
        pnlStud.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlStud);
        
        pnlAtt = new JPanel();
        pnlAtt.setBounds(547, 203, 464, 168);
        pnlAtt.setBackground(Color.WHITE);
        pnlAtt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlAtt);
        
        pnlTask = new JPanel();
        pnlTask.setBounds(1052, 203, 464, 168);
        pnlTask.setBackground(Color.WHITE);
        pnlTask.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlTask);
        
        // ===== TASK PANEL =====
        setupTaskPanel();
        
        // ===== SCHEDULE SECTION =====
        lblSched = new JLabel("SCHEDULE");
        lblSched.setBounds(700, 441, 116, 27);
        lblSched.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSched.setForeground(Color.decode("#737373"));
        add(lblSched);
        
        pnlSched = new JPanel();
        pnlSched.setLayout(null);
        pnlSched.setBounds(653, 410, 865, 478);
        pnlSched.setBackground(Color.WHITE);
        pnlSched.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Schedule label inside panel
        JLabel scheduleLabel = new JLabel("📅 Click to open schedule");
        scheduleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        scheduleLabel.setForeground(Color.GRAY);
        scheduleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scheduleLabel.setBounds(0, 200, 865, 50);
        pnlSched.add(scheduleLabel);
        
        pnlSched.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ScheduleDisplay scheduleDisplay = new ScheduleDisplay();
                scheduleDisplay.setVisible(true);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                pnlSched.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        add(pnlSched);
        
        // ===== DECORATIVE IMAGE =====
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
        
        // Load initial stats from database
        refreshStats();
        
        // Add sample tasks
        addTask("Review student records");
        addTask("Update grades");
        addTask("Mark attendance");
    }
    
    // ===== REFRESH STATISTICS FROM DATABASE =====
    public void refreshStats() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sms_db", "root", "");
            
            // Get total number of students
            String studentSql = "SELECT COUNT(*) as total FROM students";
            PreparedStatement studentPs = conn.prepareStatement(studentSql);
            ResultSet studentRs = studentPs.executeQuery();
            
            if (studentRs.next()) {
                int totalStudents = studentRs.getInt("total");
                lblNoStuds.setText(String.valueOf(totalStudents));
            }
            studentRs.close();
            studentPs.close();
            
            // Get attendance rate (present count / total attendance records)
            String attendanceSql = "SELECT " +
                "COUNT(*) as total, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as present " +
                "FROM attendance";
            PreparedStatement attendancePs = conn.prepareStatement(attendanceSql);
            ResultSet attendanceRs = attendancePs.executeQuery();
            
            if (attendanceRs.next()) {
                int total = attendanceRs.getInt("total");
                int present = attendanceRs.getInt("present");
                
                if (total > 0) {
                    int rate = (present * 100) / total;
                    lblAttendanceRate.setText(rate + "%");
                } else {
                    lblAttendanceRate.setText("0%");
                }
            }
            attendanceRs.close();
            attendancePs.close();
            
            conn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            lblNoStuds.setText("0");
            lblAttendanceRate.setText("0%");
        }
    }
    
    // ===== SETUP TASK PANEL =====
    private void setupTaskPanel() {
        taskPanel = new JPanel();
        taskPanel.setLayout(null);
        taskPanel.setBounds(45, 410, 560, 490);
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(taskPanel);
        
        // Title
        JLabel titleLabel = new JLabel("MY TASKS:");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        titleLabel.setForeground(Color.decode("#737373"));
        titleLabel.setBounds(25, 15, 200, 25);
        taskPanel.add(titleLabel);
        
        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(25, 60, 100, 40);
        btnAdd.setBackground(new Color(26, 115, 232));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(this);
        taskPanel.add(btnAdd);
        
        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(25, 115, 100, 40);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(this);
        taskPanel.add(btnDelete);
        
        // Clear Button
        btnClear = new JButton("Clear");
        btnClear.setBounds(25, 170, 100, 40);
        btnClear.setBackground(new Color(255, 165, 0));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(this);
        taskPanel.add(btnClear);
        
        // Task Input Field
        tfTaskInput = new JTextField();
        tfTaskInput.setBounds(140, 60, 390, 40);
        tfTaskInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfTaskInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        taskPanel.add(tfTaskInput);
        
        // Task List
        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        taskList.setSelectionBackground(new Color(26, 115, 232));
        taskList.setSelectionForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(taskList);
        scrollPane.setBounds(140, 115, 390, 350);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        taskPanel.add(scrollPane);
    }
    
    // ===== ADD TASK =====
    private void addTask(String task) {
        if (!taskSet.contains(task)) {
            taskSet.add(task);
            listModel.addElement(task);
            updateProgress();
        }
    }
    
    // ===== DELETE SELECTED TASK =====
    private void deleteSelectedTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedTask = listModel.getElementAt(selectedIndex);
            taskSet.remove(selectedTask);
            listModel.removeElementAt(selectedIndex);
            updateProgress();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // ===== CLEAR ALL TASKS =====
    private void clearAllTasks() {
        if (!taskSet.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all tasks?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                taskSet.clear();
                listModel.clear();
                updateProgress();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tasks to clear.", "Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ===== UPDATE TASK PROGRESS =====
    private void updateProgress() {
        int totalTasks = taskSet.size();
        int percentage;
        if (totalTasks == 0) {
            percentage = 0;
            lblProgressPercent.setText("0%");
        } else {
            lblProgressPercent.setText(totalTasks + " tasks");
        }
    }
    
    // ===== ACTION PERFORMED =====
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btnAdd) {
            String task = tfTaskInput.getText().trim();
            
            if (!task.isEmpty()) {
                if (!taskSet.contains(task)) {
                    taskSet.add(task);
                    listModel.addElement(task);
                    tfTaskInput.setText("");
                    updateProgress();
                } else {
                    JOptionPane.showMessageDialog(this, "Task already exists!", "Duplicate", JOptionPane.WARNING_MESSAGE);
                    tfTaskInput.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task.", "No Input", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (e.getSource() == btnDelete) {
            deleteSelectedTask();
            
        } else if (e.getSource() == btnClear) {
            clearAllTasks();
        }
    }
}