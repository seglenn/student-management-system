package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AttendancePanel extends JPanel {

    private JLabel lblTitle, lblSubtxt, imgDisplay;
    private JButton btnAddAtt;
    private ImageIcon imgDashOne;
    private JTable attTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;

    // Stores "YYYY-NNNN - First Last" display strings; index matches studentIds list
    private JComboBox<String> cmbStudent;
    private java.util.List<Integer> studentIds = new java.util.ArrayList<>();

    AttendancePanel() {

        setLayout(null);

        lblTitle = new JLabel("Attendance");
        lblTitle.setBounds(45, 87, 244, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        lblSubtxt = new JLabel("Track daily student attendance");
        lblSubtxt.setBounds(45, 137, 354, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        btnAddAtt = new JButton("Add attendance");
        btnAddAtt.setBounds(1273, 104, 230, 61);
        btnAddAtt.setBackground(Color.decode("#1f87e2"));
        btnAddAtt.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddAtt.setForeground(Color.WHITE);
        btnAddAtt.setFocusPainted(false);
        btnAddAtt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddAtt);

        // Columns: student_number and name are JOINed from students table
        String[] columns = {"Student No.", "Name", "Course/Subject", "Status", "Date", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        attTable = new JTable(tableModel);

        attTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        attTable.setRowHeight(45);
        attTable.setSelectionBackground(Color.decode("#e3f2fd"));
        attTable.setGridColor(Color.decode("#e0e0e0"));

        JTableHeader header = attTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        scrollPane = new JScrollPane(attTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane);

        cmbStudent = new JComboBox<>();

        loadAttendanceFromDB();

        btnAddAtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOpen) {
                    openAddAttendanceFrame();
                }
            }
        });

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // JOIN attendance with students to get student_number and full name
    public void loadAttendanceFromDB() {
        tableModel.setRowCount(0);

        try {
            Connection conn = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms_db", "root", "");

            String sql = "SELECT s.student_number, " +
                         "CONCAT(s.first_name, ' ', s.last_name) AS full_name, " +
                         "a.course_subject, a.status, " +
                         "DATE_FORMAT(a.date_recorded, '%m/%d/%Y') AS date_recorded, " +
                         "a.remarks " +
                         "FROM attendance a " +
                         "JOIN students s ON a.student_id = s.student_id " +
                         "ORDER BY a.date_recorded DESC";

            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("student_number"),
                    rs.getString("full_name"),
                    rs.getString("course_subject"),
                    rs.getString("status"),
                    rs.getString("date_recorded"),
                    rs.getString("remarks")
                });
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Populates dropdown with "2026-0001 - Juan Dela Cruz"
    // and keeps a parallel studentIds list so we can insert student_id
    public void loadStudentDropdown() {
        cmbStudent.removeAllItems();
        studentIds.clear();
        cmbStudent.addItem("Select student");
        studentIds.add(-1); // placeholder for index 0

        try {
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms_db", "root", "");

            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(
                    "SELECT student_id, student_number, first_name, last_name " +
                    "FROM students ORDER BY last_name, first_name");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String display = rs.getString("student_number") + " - " +
                                 rs.getString("first_name") + " " + rs.getString("last_name");
                cmbStudent.addItem(display);
                studentIds.add(rs.getInt("student_id"));
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeStudentRows(String studentName) {
    for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
        String name = (String) tableModel.getValueAt(i, 1);
        if (name != null && name.equals(studentName)) {
            tableModel.removeRow(i);
        }
    }
}

    private void openAddAttendanceFrame() {
        isOpen = true;

        JFrame frmAddAtt = new JFrame();
        frmAddAtt.setSize(712, 600);
        frmAddAtt.setLayout(null);
        frmAddAtt.setLocationRelativeTo(null);
        frmAddAtt.setTitle("MARK ATTENDANCE");
        frmAddAtt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAddAtt.getContentPane().setBackground(Color.WHITE);

        frmAddAtt.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOpen = false;
            }
        });

        JLabel lblHead = new JLabel("Mark Attendance");
        lblHead.setForeground(Color.BLACK);
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblHead.setBounds(35, 36, 357, 39);
        frmAddAtt.add(lblHead);

        JLabel lblSub = new JLabel("Fill in the required details below");
        lblSub.setForeground(Color.decode("#737373"));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSub.setBounds(35, 70, 372, 27);
        frmAddAtt.add(lblSub);

        JLabel lblStudent = new JLabel("Student");
        lblStudent.setForeground(Color.BLACK);
        lblStudent.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStudent.setBounds(35, 115, 200, 28);
        frmAddAtt.add(lblStudent);

        loadStudentDropdown();
        cmbStudent.setBounds(35, 147, 634, 50);
        cmbStudent.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbStudent.setBackground(Color.WHITE);
        frmAddAtt.add(cmbStudent);

        JLabel lblDate = new JLabel("Date");
        lblDate.setForeground(Color.BLACK);
        lblDate.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDate.setBounds(35, 213, 100, 28);
        frmAddAtt.add(lblDate);

        String todayDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        JTextField tfDate = new JTextField(todayDate);
        tfDate.setBounds(35, 245, 300, 50);
        tfDate.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        tfDate.setEditable(false);
        tfDate.setBackground(Color.decode("#F9FAFB"));
        tfDate.setForeground(Color.decode("#737373"));
        tfDate.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        frmAddAtt.add(tfDate);

        JLabel lblDateNote = new JLabel("Auto-generated from system");
        lblDateNote.setForeground(Color.decode("#737373"));
        lblDateNote.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDateNote.setBounds(35, 298, 300, 20);
        frmAddAtt.add(lblDateNote);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStatus.setBounds(369, 213, 200, 28);
        frmAddAtt.add(lblStatus);

        String[] optStatus = {"Present", "Absent", "Late", "Excused"};
        JComboBox<String> cmbStatus = new JComboBox<>(optStatus);
        cmbStatus.setBounds(369, 245, 300, 50);
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbStatus.setBackground(Color.WHITE);
        frmAddAtt.add(cmbStatus);

        JLabel lblCourse = new JLabel("Course/Subject");
        lblCourse.setForeground(Color.BLACK);
        lblCourse.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCourse.setBounds(35, 325, 200, 28);
        frmAddAtt.add(lblCourse);

        String[] optCourse = {"Select course", "Programming 1", "Programming 2", "Mathematics", "English", "Filipino", "Science", "PE"};
        JComboBox<String> cmbCourse = new JComboBox<>(optCourse);
        cmbCourse.setBounds(35, 357, 634, 50);
        cmbCourse.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbCourse.setBackground(Color.WHITE);
        frmAddAtt.add(cmbCourse);

        JLabel lblRemarks = new JLabel("Remarks (optional)");
        lblRemarks.setForeground(Color.BLACK);
        lblRemarks.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblRemarks.setBounds(35, 423, 300, 28);
        frmAddAtt.add(lblRemarks);

        JTextField tfRemarks = new JTextField();
        tfRemarks.setBounds(35, 455, 634, 50);
        tfRemarks.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddAtt.add(tfRemarks);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 518, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddAtt.add(btnCancel);

        JButton btnMark = new JButton("Mark Attendance");
        btnMark.setBounds(504, 518, 190, 45);
        btnMark.setForeground(Color.WHITE);
        btnMark.setBackground(Color.decode("#1f89e5"));
        btnMark.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnMark.setFocusPainted(false);
        btnMark.setBorderPainted(false);
        btnMark.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddAtt.add(btnMark);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAddAtt.dispose();
            }
        });

        btnMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cmbStudent.getSelectedIndex();
                String course  = (String) cmbCourse.getSelectedItem();
                String status  = (String) cmbStatus.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                if (selectedIndex <= 0) {
                    JOptionPane.showMessageDialog(frmAddAtt, "Please select a student.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (course.equals("Select course")) {
                    JOptionPane.showMessageDialog(frmAddAtt, "Please select a course/subject.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Use the parallel list to get the actual student_id FK
                int studentId = studentIds.get(selectedIndex);

                try {
                    Connection conn = (Connection) DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/sms_db", "root", "");

                    // Insert using student_id FK — matches the DB schema
                    String sql = "INSERT INTO attendance (student_id, course_subject, status, remarks) " +
                                 "VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
                    ps.setInt(1, studentId);
                    ps.setString(2, course);
                    ps.setString(3, status);
                    ps.setString(4, remarks);
                    ps.executeUpdate();

                    ps.close();
                    conn.close();

                    loadAttendanceFromDB();
                    JOptionPane.showMessageDialog(frmAddAtt,
                        "Attendance marked for: " + cmbStudent.getSelectedItem(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddAtt.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddAtt,
                        "Error saving attendance: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmAddAtt.setVisible(true);
    }
}