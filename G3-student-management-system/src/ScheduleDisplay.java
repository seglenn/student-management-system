/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 *
 * @author glenn
 */
public class ScheduleDisplay extends JFrame {
    
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnDelete, btnSave;
    
    public ScheduleDisplay() {
        
        setTitle("Schedule");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("SCHEDULE:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        
        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnSave = new JButton("Save");
        
        // Style buttons
        JButton[] buttons = {btnAdd, btnDelete, btnSave};
        for (JButton btn : buttons) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(45, 115, 255));
            btn.setForeground(Color.WHITE);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);
        
        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // ===== TABLE SETUP =====
        String[] columns = {"Time", "Subject", "Section", "Room"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        
        // Load schedule from database
        loadScheduleFromDB();
        
        // Style the table
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(60);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 230, 255));
        table.setSelectionForeground(Color.BLACK);
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 22));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 60));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // ===== BUTTON ACTIONS =====
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{"New Time", "New Subject", "New Section", "000"});
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveScheduleToDB();
            }
        });
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // ===== LOAD SCHEDULE FROM DATABASE =====
    private void loadScheduleFromDB() {
        model.setRowCount(0);
        
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sms_db", "root", "");
            
            String sql = "SELECT time_slot, subject, section, room FROM schedule ORDER BY schedule_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String timeSlot = rs.getString("time_slot");
                String subject = rs.getString("subject");
                String section = rs.getString("section");
                String room = rs.getString("room");
                model.addRow(new Object[]{timeSlot, subject, section, room});
            }
            
            rs.close();
            ps.close();
            conn.close();
            
            // If no data, add sample schedule
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{"8:00 AM - 10:30 AM", "History", "Section A", "403"});
                model.addRow(new Object[]{"10:30 AM - 12:30 PM", "English", "Section B", "101"});
                model.addRow(new Object[]{"2:00 PM - 4:30 PM", "Math", "Section C", "202"});
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Fallback to sample data
            model.addRow(new Object[]{"8:00 AM - 10:30 AM", "History", "Section A", "403"});
            model.addRow(new Object[]{"10:30 AM - 12:30 PM", "English", "Section B", "101"});
            model.addRow(new Object[]{"2:00 PM - 4:30 PM", "Math", "Section C", "202"});
        }
    }
    
    // ===== SAVE SCHEDULE TO DATABASE =====
    private void saveScheduleToDB() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sms_db", "root", "");
            
            // Delete all existing schedule
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM schedule");
            deleteStmt.executeUpdate();
            
            // Insert current schedule
            String sql = "INSERT INTO schedule (time_slot, subject, section, room) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            for (int i = 0; i < model.getRowCount(); i++) {
                ps.setString(1, (String) model.getValueAt(i, 0));
                ps.setString(2, (String) model.getValueAt(i, 1));
                ps.setString(3, (String) model.getValueAt(i, 2));
                ps.setString(4, (String) model.getValueAt(i, 3));
                ps.addBatch();
            }
            
            ps.executeBatch();
            ps.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this, "Schedule saved to database successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleDisplay frame = new ScheduleDisplay();
            frame.setVisible(true);
        });
    }
}