package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class GradesPanel extends JPanel {

    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddGrade, btnSaveGrade, btnDeleteGrade;
    ImageIcon imgDashOne;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;

    private JComboBox<String> cmbStudent;
    private List<Integer> studentIds = new ArrayList<>();
    private List<String> studentNumbers = new ArrayList<>();
    private List<String> studentNames = new ArrayList<>();
    private List<Integer> gradeIds = new ArrayList<>();

    GradesPanel() {
        setLayout(null);

        lblTitle = new JLabel("Grades");
        lblTitle.setBounds(45, 87, 149, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        lblSubtxt = new JLabel("Track students grade records");
        lblSubtxt.setBounds(45, 137, 321, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        // BUTTONS
        btnAddGrade = new JButton("Add Grade");
        btnAddGrade.setBounds(743, 104, 230, 61);
        btnAddGrade.setBackground(Color.decode("#1f87e2"));
        btnAddGrade.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddGrade.setForeground(Color.WHITE);
        btnAddGrade.setFocusPainted(false);
        btnAddGrade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddGrade);

        btnSaveGrade = new JButton("Save Changes");
        btnSaveGrade.setBounds(1003, 104, 230, 61);
        btnSaveGrade.setBackground(new Color(22, 163, 74));
        btnSaveGrade.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnSaveGrade.setForeground(Color.WHITE);
        btnSaveGrade.setFocusPainted(false);
        btnSaveGrade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSaveGrade);

        btnDeleteGrade = new JButton("Delete Grade");
        btnDeleteGrade.setBounds(1263, 104, 230, 61);
        btnDeleteGrade.setBackground(Color.decode("#e53935"));
        btnDeleteGrade.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnDeleteGrade.setForeground(Color.WHITE);
        btnDeleteGrade.setFocusPainted(false);
        btnDeleteGrade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnDeleteGrade);

        // TABLE COLUMNS
        String[] columns = {"Student No.", "Name", "Course/Subject", "Grade", "Status", "Semester", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 0 && col != 1 && col != 4;
            }
        };

        gradeTable = new JTable(tableModel);
        gradeTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gradeTable.setRowHeight(45);
        gradeTable.setSelectionBackground(Color.decode("#e3f2fd"));
        gradeTable.setGridColor(Color.decode("#e0e0e0"));

        JTableHeader header = gradeTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        // Auto-update Status when Grade is edited
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 3) {
                int row = e.getFirstRow();
                Object gradeVal = tableModel.getValueAt(row, 3);
                if (gradeVal != null) {
                    try {
                        double g = Double.parseDouble(gradeVal.toString().trim());
                        tableModel.setValueAt(g >= 75 ? "Passed" : "Failed", row, 4);
                    } catch (NumberFormatException ex) {
                        tableModel.setValueAt("", row, 4);
                    }
                }
            }
        });

        scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane);

        cmbStudent = new JComboBox<>();

        loadGradesFromDB();
        setupButtonListeners();

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // COMPUTE STATUS FROM GRADE
    private String computeEquivalent(String gradeStr) {
        try {
            double g = Double.parseDouble(gradeStr.trim());
            return g >= 75 ? "Passed" : "Failed";
        } catch (NumberFormatException ex) {
            return "";
        }
    }

    // LOAD GRADES FROM DATABASE
    public void loadGradesFromDB() {
        tableModel.setRowCount(0);
        gradeIds.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT grade_id, student_number, student_name, course_subject, grade, equivalent, semester, remarks FROM grades ORDER BY date_recorded DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                gradeIds.add(rs.getInt("grade_id"));
                String gradeVal = rs.getString("grade");
                String equivalent = computeEquivalent(gradeVal != null ? gradeVal : "");
                tableModel.addRow(new Object[]{
                    rs.getString("student_number"),
                    rs.getString("student_name"),
                    rs.getString("course_subject"),
                    gradeVal,
                    equivalent,
                    rs.getString("semester"),
                    rs.getString("remarks")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // SAVE GRADES TO DATABASE
    private void saveGradesToDB() {
        if (gradeTable.isEditing()) {
            gradeTable.getCellEditor().stopCellEditing();
        }

        int successCount = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
            conn.setAutoCommit(false);

            String sql = "UPDATE grades SET course_subject = ?, grade = ?, equivalent = ?, semester = ?, remarks = ? WHERE grade_id = ?";
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (i >= gradeIds.size()) continue;

                String course = String.valueOf(tableModel.getValueAt(i, 2));
                String gradeValue = String.valueOf(tableModel.getValueAt(i, 3));
                String semester = String.valueOf(tableModel.getValueAt(i, 5));
                String remarks = String.valueOf(tableModel.getValueAt(i, 6));
                int gradeId = gradeIds.get(i);

                double gradeNum;
                try {
                    gradeNum = Double.parseDouble(gradeValue);
                    if (gradeNum < 0 || gradeNum > 100) {
                        JOptionPane.showMessageDialog(this, "Row " + (i + 1) + ": Grade must be between 0 and 100.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                        conn.rollback();
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Row " + (i + 1) + ": Grade must be a valid number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    conn.rollback();
                    return;
                }

                String equivalent = gradeNum >= 75 ? "Passed" : "Failed";
                tableModel.setValueAt(equivalent, i, 4);

                ps.setString(1, course);
                ps.setBigDecimal(2, new java.math.BigDecimal(gradeValue));
                ps.setString(3, equivalent);
                ps.setString(4, semester);
                ps.setString(5, remarks);
                ps.setInt(6, gradeId);
                ps.addBatch();
                successCount++;
            }

            if (successCount > 0) {
                ps.executeBatch();
                conn.commit();
                JOptionPane.showMessageDialog(this, successCount + " grade record(s) saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No changes to save.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error saving grades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // DELETE SELECTED GRADE
    private void deleteSelectedGrade() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this grade record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (selectedRow < gradeIds.size()) {
            int gradeId = gradeIds.get(selectedRow);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM grades WHERE grade_id = ?")) {

                ps.setInt(1, gradeId);
                ps.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting grade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        gradeIds.remove(selectedRow);
        tableModel.removeRow(selectedRow);
    }

    // BUTTON LISTENERS
    private void setupButtonListeners() {
        btnAddGrade.addActionListener(e -> {
            if (!isOpen) openAddGradeDialog();
        });
        btnSaveGrade.addActionListener(e -> saveGradesToDB());
        btnDeleteGrade.addActionListener(e -> deleteSelectedGrade());
    }

    // STUDENT DROPDOWN
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

    // ADD GRADE PANEL
    private void openAddGradeDialog() {
        isOpen = true;

        JFrame frmAddGrade = new JFrame();
        frmAddGrade.setSize(712, 620);
        frmAddGrade.setLayout(null);
        frmAddGrade.setLocationRelativeTo(null);
        frmAddGrade.setTitle("ADD GRADE");
        frmAddGrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAddGrade.getContentPane().setBackground(Color.WHITE);

        frmAddGrade.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) { isOpen = false; }
        });

        JLabel lblAddGrade = new JLabel("Add Grade");
        lblAddGrade.setForeground(Color.BLACK);
        lblAddGrade.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblAddGrade.setBounds(35, 36, 357, 39);
        frmAddGrade.add(lblAddGrade);

        JLabel lblSubtitle = new JLabel("Fill in the required details below");
        lblSubtitle.setForeground(Color.decode("#737373"));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSubtitle.setBounds(35, 70, 372, 27);
        frmAddGrade.add(lblSubtitle);

        JLabel lblStudent = new JLabel("Student");
        lblStudent.setForeground(Color.BLACK);
        lblStudent.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStudent.setBounds(35, 115, 200, 28);
        frmAddGrade.add(lblStudent);

        loadStudentDropdown();
        cmbStudent.setBounds(35, 147, 634, 45);
        cmbStudent.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbStudent.setBackground(Color.WHITE);
        frmAddGrade.add(cmbStudent);

        JLabel lblCourse = new JLabel("Course");
        lblCourse.setForeground(Color.BLACK);
        lblCourse.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCourse.setBounds(35, 207, 200, 28);
        frmAddGrade.add(lblCourse);

        String[] optCourse = {"Select course", "OOP - IT001", "ITP - IT002", "HCI - IT003", "NetAd - IT004", "OS - IT005"};
        JComboBox<String> cmbCourse = new JComboBox<>(optCourse);
        cmbCourse.setBounds(35, 239, 634, 45);
        cmbCourse.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbCourse.setBackground(Color.WHITE);
        frmAddGrade.add(cmbCourse);

        JLabel lblGrade = new JLabel("Grade (0-100)");
        lblGrade.setForeground(Color.BLACK);
        lblGrade.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblGrade.setBounds(35, 299, 200, 28);
        frmAddGrade.add(lblGrade);

        JTextField tfGrade = new JTextField();
        tfGrade.setBounds(35, 331, 300, 45);
        tfGrade.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddGrade.add(tfGrade);

        JLabel lblSemester = new JLabel("Semester");
        lblSemester.setForeground(Color.BLACK);
        lblSemester.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSemester.setBounds(369, 299, 200, 28);
        frmAddGrade.add(lblSemester);

        String[] optSemester = {"Select", "1st Semester", "2nd Semester", "Summer"};
        JComboBox<String> cmbSemester = new JComboBox<>(optSemester);
        cmbSemester.setBounds(369, 331, 300, 45);
        cmbSemester.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbSemester.setBackground(Color.WHITE);
        frmAddGrade.add(cmbSemester);

        JLabel lblRemarks = new JLabel("Remarks (optional)");
        lblRemarks.setForeground(Color.BLACK);
        lblRemarks.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblRemarks.setBounds(35, 391, 200, 28);
        frmAddGrade.add(lblRemarks);

        JTextField tfRemarks = new JTextField();
        tfRemarks.setBounds(35, 423, 634, 45);
        tfRemarks.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddGrade.add(tfRemarks);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 490, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddGrade.add(btnCancel);
        btnCancel.addActionListener(e -> frmAddGrade.dispose());

        JButton btnAdd = new JButton("Add Grade");
        btnAdd.setBounds(504, 490, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddGrade.add(btnAdd);

        //ADD GRADE FUNCTION
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cmbStudent.getSelectedIndex();
                String course = (String) cmbCourse.getSelectedItem();
                String grade = tfGrade.getText().trim();
                String semester = (String) cmbSemester.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                if (selectedIndex <= 0) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Please select a student.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (course.equals("Select course")) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Please select a course.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (grade.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Please enter a grade.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (semester.equals("Select")) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Please select a semester.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double gradeNum;
                try {
                    gradeNum = Double.parseDouble(grade);
                    if (gradeNum < 0 || gradeNum > 100) {
                        JOptionPane.showMessageDialog(frmAddGrade, "Grade must be between 0 and 100.", "Validation", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Grade must be a valid number.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String equivalent = gradeNum >= 75 ? "Passed" : "Failed";

                int studentId = studentIds.get(selectedIndex);
                String studentNumber = studentNumbers.get(selectedIndex);
                String studentName = studentNames.get(selectedIndex);

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", ""); PreparedStatement ps = conn.prepareStatement("INSERT INTO grades (student_id, student_number, student_name, course_subject, grade, equivalent, semester, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, studentId);
                    ps.setString(2, studentNumber);
                    ps.setString(3, studentName);
                    ps.setString(4, course);
                    ps.setBigDecimal(5, new java.math.BigDecimal(grade));
                    ps.setString(6, equivalent);
                    ps.setString(7, semester);
                    ps.setString(8, remarks);
                    ps.executeUpdate();

                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            gradeIds.add(0, generatedKeys.getInt(1));
                        }
                    }

                    tableModel.insertRow(0, new Object[]{
                        studentNumber, studentName, course, grade, equivalent, semester, remarks
                    });

                    JOptionPane.showMessageDialog(frmAddGrade, "Grade added for: " + studentName, "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddGrade.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmAddGrade, "Error saving grade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmAddGrade.setVisible(true);
    }
}