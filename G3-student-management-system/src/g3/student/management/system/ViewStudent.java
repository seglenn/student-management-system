package g3.student.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ViewStudent {
    
    // UI COMPONENTS
    private JFrame viewWindow;
    private JLabel photoLabel, titleLabel, lblStudNo, valStudNo,nameLabel, nameValue ,emailLabel, emailValue, phoneLabel, phoneValue,programLabel, programValue ,addressLabel, addressValue,statusLabel, statusValue,lblDateEnrolled, valDateEnrolled,lblAbsences, valAbsences,lblPresent, valPresent ,lblLate, valLate;
    private JButton closeButton;
    private JSeparator line;
    
    public ViewStudent(ImageIcon photoIcon, String studentNumber, String name, 
                       String email, String program, String status, String dateEnrolled) {
        
        // DEFAULT VALUES
        String phone = "";
        String address = "";
        int absentCount = 0;
        int presentCount = 0;
        int lateCount = 0;
        
        // FETCH ADDITIONAL DATA
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "")) {
            
            // GET STUDENT DETAILS
            try (PreparedStatement studentPs = conn.prepareStatement("SELECT phone, address FROM students WHERE student_number = ?")) {
                studentPs.setString(1, studentNumber);
                try (ResultSet studentRs = studentPs.executeQuery()) {
                    if (studentRs.next()) {
                        phone = studentRs.getString("phone");
                        address = studentRs.getString("address");
                    }
                }
            }
            
            // GET ATTENDANCE STATISTICS
            String attendanceSql = "SELECT " +
                "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) as absent_count, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as present_count, " +
                "SUM(CASE WHEN status = 'Late' THEN 1 ELSE 0 END) as late_count " +
                "FROM attendance WHERE student_id = (SELECT student_id FROM students WHERE student_number = ?)";
            
            try (PreparedStatement attendancePs = conn.prepareStatement(attendanceSql)) {
                attendancePs.setString(1, studentNumber);
                try (ResultSet attendanceRs = attendancePs.executeQuery()) {
                    if (attendanceRs.next()) {
                        absentCount = attendanceRs.getInt("absent_count");
                        presentCount = attendanceRs.getInt("present_count");
                        lateCount = attendanceRs.getInt("late_count");
                    }
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // MAIN WINDOW
        viewWindow = new JFrame();
        viewWindow.setSize(500, 820);
        viewWindow.setLayout(null);
        viewWindow.setLocationRelativeTo(null);
        viewWindow.setTitle("STUDENT DETAILS");
        viewWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewWindow.getContentPane().setBackground(Color.WHITE);

        // PHOTO SECTION
        photoLabel = new JLabel();
        photoLabel.setBounds(175, 30, 150, 150);
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

        // TITLE SECTION
        titleLabel = new JLabel("Student Details");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(35, 200, 300, 35);
        viewWindow.add(titleLabel);

        line = new JSeparator();
        line.setBounds(35, 240, 430, 2);
        line.setForeground(new Color(220, 220, 220));
        viewWindow.add(line);

        // STUDENT INFORMATION
        // STUDENT NO.
        lblStudNo = new JLabel("Student No.:");
        lblStudNo.setForeground(Color.decode("#737373"));
        lblStudNo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblStudNo.setBounds(35, 260, 130, 28);
        viewWindow.add(lblStudNo);

        valStudNo = new JLabel(studentNumber);
        valStudNo.setForeground(Color.BLACK);
        valStudNo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        valStudNo.setBounds(175, 260, 280, 28);
        viewWindow.add(valStudNo);

        // FULL NAME
        nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(Color.decode("#737373"));
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nameLabel.setBounds(35, 295, 130, 28);
        viewWindow.add(nameLabel);

        nameValue = new JLabel(name);
        nameValue.setForeground(Color.BLACK);
        nameValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameValue.setBounds(175, 295, 280, 28);
        viewWindow.add(nameValue);

        // EMAIL
        emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#737373"));
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        emailLabel.setBounds(35, 330, 130, 28);
        viewWindow.add(emailLabel);

        emailValue = new JLabel(email);
        emailValue.setForeground(Color.BLACK);
        emailValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        emailValue.setBounds(175, 330, 280, 28);
        viewWindow.add(emailValue);

        // PHONE
        phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(Color.decode("#737373"));
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        phoneLabel.setBounds(35, 365, 130, 28);
        viewWindow.add(phoneLabel);

        phoneValue = new JLabel(phone);
        phoneValue.setForeground(Color.BLACK);
        phoneValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        phoneValue.setBounds(175, 365, 280, 28);
        viewWindow.add(phoneValue);

        // PROGRAM
        programLabel = new JLabel("Program:");
        programLabel.setForeground(Color.decode("#737373"));
        programLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        programLabel.setBounds(35, 400, 130, 28);
        viewWindow.add(programLabel);

        programValue = new JLabel(program);
        programValue.setForeground(Color.BLACK);
        programValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        programValue.setBounds(175, 400, 280, 28);
        viewWindow.add(programValue);

        // ADDRESS
        addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.decode("#737373"));
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        addressLabel.setBounds(35, 435, 130, 28);
        viewWindow.add(addressLabel);

        addressValue = new JLabel(address);
        addressValue.setForeground(Color.BLACK);
        addressValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addressValue.setBounds(175, 435, 280, 28);
        viewWindow.add(addressValue);

        // STATUS
        statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.decode("#737373"));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        statusLabel.setBounds(35, 470, 130, 28);
        viewWindow.add(statusLabel);

        statusValue = new JLabel(status);
        statusValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        if (status.equals("Active")) {
            statusValue.setForeground(Color.decode("#16a34a"));
        } else {
            statusValue.setForeground(Color.decode("#e53935"));
        }
        statusValue.setBounds(175, 470, 280, 28);
        viewWindow.add(statusValue);

        // DATE ENROLLED
        lblDateEnrolled = new JLabel("Date Enrolled:");
        lblDateEnrolled.setForeground(Color.decode("#737373"));
        lblDateEnrolled.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDateEnrolled.setBounds(35, 505, 130, 28);
        viewWindow.add(lblDateEnrolled);

        valDateEnrolled = new JLabel(dateEnrolled);
        valDateEnrolled.setForeground(Color.BLACK);
        valDateEnrolled.setFont(new Font("Segoe UI", Font.BOLD, 15));
        valDateEnrolled.setBounds(175, 505, 280, 28);
        viewWindow.add(valDateEnrolled);

        // SEPARATOR
        JSeparator line2 = new JSeparator();
        line2.setBounds(35, 540, 430, 2);
        line2.setForeground(new Color(220, 220, 220));
        viewWindow.add(line2);

        // ATTENDANCE SECTION TITLE
        JLabel attendanceTitle = new JLabel("Attendance Summary");
        attendanceTitle.setForeground(Color.decode("#1f87e2"));
        attendanceTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        attendanceTitle.setBounds(35, 555, 300, 30);
        viewWindow.add(attendanceTitle);

        // PRESENT
        lblPresent = new JLabel("Present:");
        lblPresent.setForeground(Color.decode("#737373"));
        lblPresent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPresent.setBounds(35, 595, 130, 28);
        viewWindow.add(lblPresent);

        valPresent = new JLabel(String.valueOf(presentCount));
        valPresent.setForeground(Color.decode("#16a34a"));
        valPresent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valPresent.setBounds(175, 595, 280, 28);
        viewWindow.add(valPresent);

        // ABSENCES
        lblAbsences = new JLabel("Absences:");
        lblAbsences.setForeground(Color.decode("#737373"));
        lblAbsences.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblAbsences.setBounds(35, 630, 130, 28);
        viewWindow.add(lblAbsences);

        valAbsences = new JLabel(String.valueOf(absentCount));
        valAbsences.setForeground(Color.decode("#e53935"));
        valAbsences.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valAbsences.setBounds(175, 630, 280, 28);
        viewWindow.add(valAbsences);

        // LATE
        lblLate = new JLabel("Late:");
        lblLate.setForeground(Color.decode("#737373"));
        lblLate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblLate.setBounds(35, 665, 130, 28);
        viewWindow.add(lblLate);

        valLate = new JLabel(String.valueOf(lateCount));
        valLate.setForeground(Color.decode("#f39c12"));
        valLate.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valLate.setBounds(175, 665, 280, 28);
        viewWindow.add(valLate);

        // CLOSE BUTTON
        closeButton = new JButton("Close");
        closeButton.setBounds(175, 715, 150, 45);
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