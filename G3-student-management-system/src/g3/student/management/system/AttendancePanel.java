package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendancePanel extends JPanel {

    // UI COMPONENTS
    private JLabel lblTitle, lblSubtxt, imgDisplay;
    private JButton btnAddAtt, btnSaveAtt, btnDeleteAtt;
    private ImageIcon imgDashOne;
    private JTable attTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;
    private JComboBox<String> cmbStudent;
    
    // DATA LISTS
    private List<Integer> studentIds = new ArrayList<>();
    private List<String> studentNumbers = new ArrayList<>();
    private List<String> studentNames = new ArrayList<>();
    private List<Integer> attendanceIds = new ArrayList<>();
    
    // REFERENCE PANEL
    private DashboardPanel dashboardPanel;

    public void setDashboardPanel(DashboardPanel dashboardPanel) {
        this.dashboardPanel = dashboardPanel;
    }

    AttendancePanel() {
        setLayout(null);

        // TITLE
        lblTitle = new JLabel("Attendance");
        lblTitle.setBounds(45, 87, 244, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        // SUBTITLE
        lblSubtxt = new JLabel("Track daily student attendance");
        lblSubtxt.setBounds(45, 137, 354, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        // BUTTONS
        btnAddAtt = new JButton("Add Attendance");
        btnAddAtt.setBounds(743, 104, 230, 61);
        btnAddAtt.setBackground(Color.decode("#1f87e2"));
        btnAddAtt.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddAtt.setForeground(Color.WHITE);
        btnAddAtt.setFocusPainted(false);
        btnAddAtt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddAtt);

        btnSaveAtt = new JButton("Save Changes");
        btnSaveAtt.setBounds(1003, 104, 230, 61);
        btnSaveAtt.setBackground(new Color(22, 163, 74));
        btnSaveAtt.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnSaveAtt.setForeground(Color.WHITE);
        btnSaveAtt.setFocusPainted(false);
        btnSaveAtt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSaveAtt);

        btnDeleteAtt = new JButton("Delete Attendance");
        btnDeleteAtt.setBounds(1263, 104, 230, 61);
        btnDeleteAtt.setBackground(Color.decode("#e53935"));
        btnDeleteAtt.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnDeleteAtt.setForeground(Color.WHITE);
        btnDeleteAtt.setFocusPainted(false);
        btnDeleteAtt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnDeleteAtt);

        // TABLE COLUMNS
        String[] columns = {"Student No.", "Name", "Course/Subject", "Status", "Date", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 2 || col == 3 || col == 5;
            }
        };

        attTable = new JTable(tableModel);
        attTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        attTable.setRowHeight(45);
        attTable.setSelectionBackground(Color.decode("#e3f2fd"));
        attTable.setGridColor(Color.decode("#e0e0e0"));

        // STATUS DROPDOWN
        JComboBox<String> statusEditor = new JComboBox<>(new String[]{"Present", "Absent", "Late", "Excused"});
        attTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusEditor));

        // TABLE HEADER
        JTableHeader header = attTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        // SCROLL PANE
        scrollPane = new JScrollPane(attTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane);

        cmbStudent = new JComboBox<>();

        // LOAD DATA
        loadAttendanceFromDB();
        setupButtonListeners();

        // DECORATIVE IMAGE
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // LOAD ATTENDANCE FROM DATABASE
    public void loadAttendanceFromDB() {
        tableModel.setRowCount(0);
        attendanceIds.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT attendance_id, student_number, student_name, course_subject, status, DATE_FORMAT(date_recorded, '%m/%d/%Y') AS date_recorded, remarks FROM attendance ORDER BY date_recorded DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                attendanceIds.add(rs.getInt("attendance_id"));
                tableModel.addRow(new Object[]{
                    rs.getString("student_number"),
                    rs.getString("student_name"),
                    rs.getString("course_subject"),
                    rs.getString("status"),
                    rs.getString("date_recorded"),
                    rs.getString("remarks")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // SAVE ATTENDANCE TO DATABASE
    private void saveAttendanceToDB() {
        if (attTable.isEditing()) {
            attTable.getCellEditor().stopCellEditing();
        }

        int successCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
            conn.setAutoCommit(false);

            String sql = "UPDATE attendance SET course_subject = ?, status = ?, remarks = ? WHERE attendance_id = ?";
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (i >= attendanceIds.size()) {
                    continue;
                }

                String course = String.valueOf(tableModel.getValueAt(i, 2));
                String status = String.valueOf(tableModel.getValueAt(i, 3));
                String remarks = String.valueOf(tableModel.getValueAt(i, 5));
                int attId = attendanceIds.get(i);

                // VALIDATE STATUS
                if (!status.equals("Present") && !status.equals("Absent") && !status.equals("Late") && !status.equals("Excused")) {
                    JOptionPane.showMessageDialog(this, "Row " + (i + 1) + ": Status must be Present, Absent, Late, or Excused.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    if (conn != null) {
                        conn.rollback();
                    }
                    return;
                }

                ps.setString(1, course);
                ps.setString(2, status);
                ps.setString(3, remarks);
                ps.setInt(4, attId);
                ps.addBatch();
                successCount++;
            }

            if (successCount > 0) {
                int[] results = ps.executeBatch();

                boolean allSuccessful = true;
                for (int result : results) {
                    if (result == Statement.EXECUTE_FAILED) {
                        allSuccessful = false;
                        break;
                    }
                }

                if (allSuccessful) {
                    conn.commit();
                    
                    if (dashboardPanel != null) {
                        dashboardPanel.refreshStats();
                    }

                    JOptionPane.showMessageDialog(this, successCount + " attendance record(s) saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Some updates failed. Changes have been rolled back.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "No changes to save.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            
            try {
                if (conn != null) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Error saving attendance. Changes rolled back: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            
        } finally {
            
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);  
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // DELETE SELECTED ATTENDANCE
    private void deleteSelectedAttendance() {
        int selectedRow = attTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this attendance record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION){
            return;
        }

        if (selectedRow < attendanceIds.size()) {
            int attId = attendanceIds.get(selectedRow);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM attendance WHERE attendance_id = ?")) {

                ps.setInt(1, attId);
                ps.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        attendanceIds.remove(selectedRow);
        tableModel.removeRow(selectedRow);

        if (dashboardPanel != null) {
            dashboardPanel.refreshStats();
        }
    }

    // BUTTON LISTENERS 
    private void setupButtonListeners() {
        btnAddAtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOpen){
                    openAddAttendanceFrame();
                }
            }
        });

        btnSaveAtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAttendanceToDB();
            }
        });

        btnDeleteAtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedAttendance();
            }
        });
    }

    // LOAD STUDENT DROPDOWN
    public void loadStudentDropdown() {
        cmbStudent.removeAllItems();
        studentIds.clear();
        studentNumbers.clear();
        studentNames.clear();

        cmbStudent.addItem("Select student");
        studentIds.add(-1);
        studentNumbers.add("");
        studentNames.add("");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = connection.prepareStatement("SELECT student_id, student_number, first_name, last_name FROM students ORDER BY last_name, first_name");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String num = rs.getString("student_number");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                cmbStudent.addItem(num + " - " + name);
                studentIds.add(rs.getInt("student_id"));
                studentNumbers.add(num);
                studentNames.add(name);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ADD ATTENDANCE DIALOG
    private void openAddAttendanceFrame() {
        isOpen = true;

        JFrame frmAddAtt = new JFrame();
        frmAddAtt.setSize(732, 620);
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

        // HEADER
        JLabel lblHead = new JLabel("Mark Attendance");
        lblHead.setForeground(Color.BLACK);
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblHead.setBounds(35, 36, 357, 39);
        frmAddAtt.add(lblHead);

        // SUBTITLE
        JLabel lblSub = new JLabel("Fill in the required details below");
        lblSub.setForeground(Color.decode("#737373"));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSub.setBounds(35, 70, 372, 27);
        frmAddAtt.add(lblSub);

        // STUDENT SELECTION
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

        // DATE FIELD
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

        // STATUS SELECTION
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

        // COURSE SELECTION
        JLabel lblCourse = new JLabel("Course/Subject");
        lblCourse.setForeground(Color.BLACK);
        lblCourse.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCourse.setBounds(35, 325, 200, 28);
        frmAddAtt.add(lblCourse);

        String[] optCourse = {"Select course", "OOP - IT001", "ITP - IT002", "HCI - IT003", "NetAd - IT004", "OS - IT005"};
        JComboBox<String> cmbCourse = new JComboBox<>(optCourse);
        cmbCourse.setBounds(35, 357, 634, 50);
        cmbCourse.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbCourse.setBackground(Color.WHITE);
        frmAddAtt.add(cmbCourse);

        // REMARKS
        JLabel lblRemarks = new JLabel("Remarks (optional)");
        lblRemarks.setForeground(Color.BLACK);
        lblRemarks.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblRemarks.setBounds(35, 423, 300, 28);
        frmAddAtt.add(lblRemarks);

        JTextField tfRemarks = new JTextField();
        tfRemarks.setBounds(35, 455, 634, 50);
        tfRemarks.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddAtt.add(tfRemarks);

        // BUTTONS
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 518, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddAtt.add(btnCancel);
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAddAtt.dispose();
            }
        });

        JButton btnMark = new JButton("Mark Attendance");
        btnMark.setBounds(504, 518, 190, 45);
        btnMark.setForeground(Color.WHITE);
        btnMark.setBackground(Color.decode("#1f89e5"));
        btnMark.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnMark.setFocusPainted(false);
        btnMark.setBorderPainted(false);
        btnMark.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddAtt.add(btnMark);

        // SUBMIT ACTION
        btnMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cmbStudent.getSelectedIndex();
                String course = (String) cmbCourse.getSelectedItem();
                String status = (String) cmbStatus.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                // VALIDATE STUDENT
                if (selectedIndex <= 0) {
                    JOptionPane.showMessageDialog(frmAddAtt, "Please select a student.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // VALIDATE COURSE
                if (course.equals("Select course")) {
                    JOptionPane.showMessageDialog(frmAddAtt, "Please select a course/subject.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int studentId = studentIds.get(selectedIndex);
                String studentNumber = studentNumbers.get(selectedIndex);
                String studentName = studentNames.get(selectedIndex);

                // INSERT INTO DATABASE
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                     PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance (student_id, student_number, student_name, course_subject, status, remarks) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, studentId);
                    ps.setString(2, studentNumber);
                    ps.setString(3, studentName);
                    ps.setString(4, course);
                    ps.setString(5, status);
                    ps.setString(6, remarks);
                    ps.executeUpdate();

                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            attendanceIds.add(0, generatedKeys.getInt(1));
                        }
                    }

                    String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                    tableModel.insertRow(0, new Object[]{
                        studentNumber, studentName, course, status, today, remarks
                    });

                    if (dashboardPanel != null) {
                        dashboardPanel.refreshStats();
                    }

                    JOptionPane.showMessageDialog(frmAddAtt, "Attendance marked for: " + studentName, "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddAtt.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddAtt, "Error saving attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmAddAtt.setVisible(true);
    }
}