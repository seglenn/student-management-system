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

/**
 *
 * @author glenn
 */
public class ScheduleDisplay extends JFrame {
    
    // ===== FIELDS / VARIABLES =====
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnDelete;
    
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
        
        // Create buttons
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        
        // Style Add button
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setBackground(new Color(45, 115, 255));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Style Delete button
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setBackground(new Color(45, 115, 255));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        
        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // ===== TABLE SETUP =====
        String[] columns = {"Time", "Subject", "Section", "Room"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        
        // Add sample schedule data
        addSampleSchedule();
        
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
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // ===== ADD SAMPLE SCHEDULE DATA =====
    private void addSampleSchedule() {
        model.addRow(new Object[]{"8:00 AM - 10:30 AM", "History", "Section A", "403"});
        model.addRow(new Object[]{"10:30 AM - 12:30 PM", "English", "Section B", "101"});
        model.addRow(new Object[]{"2:00 PM - 4:30 PM", "Math", "Section C", "202"});
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleDisplay frame = new ScheduleDisplay();
            frame.setVisible(true);
        });
    }
}