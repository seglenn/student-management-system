package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GradesPanel extends JPanel {

    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddGrade;
    ImageIcon imgDashOne;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;

    private JComboBox<String> cmbStudent;
    private java.util.List<Integer> studentIds = new java.util.ArrayList<>();
    private java.util.List<String> studentNumbers = new java.util.ArrayList<>();
    private java.util.List<String> studentNames   = new java.util.ArrayList<>();

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

        btnAddGrade = new JButton("Add grade");
        btnAddGrade.setBounds(1273, 104, 230, 61);
        btnAddGrade.setBackground(Color.decode("#1f87e2"));
        btnAddGrade.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddGrade.setForeground(Color.WHITE);
        btnAddGrade.setFocusPainted(false);
        btnAddGrade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddGrade);

        String[] columns = {"Student No.", "Name", "Course/Subject", "Grade", "Equivalent", "Semester", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
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

        scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane);

        cmbStudent = new JComboBox<>();

        loadGradesFromDB();
        setupAddButtonListener();

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // Read student_number and student_name directly from grades table — no JOIN needed
    public void loadGradesFromDB() {
        tableModel.setRowCount(0);

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms_db", "root", "");

            // student_number and student_name are stored directly in grades row at insert time
            String sql = "SELECT student_number, student_name, course_subject, " +
                         "grade, equivalent, semester, remarks " +
                         "FROM grades ORDER BY date_recorded DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("student_number"),
                    rs.getString("student_name"),
                    rs.getString("course_subject"),
                    rs.getString("grade"),
                    rs.getString("equivalent"),
                    rs.getString("semester"),
                    rs.getString("remarks")
                });
            }

            rs.close(); ps.close(); conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Loads student_id, student_number, and full name into parallel lists
    public void loadStudentDropdown() {
        cmbStudent.removeAllItems();
        studentIds.clear();
        studentNumbers.clear();
        studentNames.clear();

        cmbStudent.addItem("Select student");
        studentIds.add(-1);
        studentNumbers.add("");
        studentNames.add("");

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms_db", "root", "");

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT student_id, student_number, first_name, last_name " +
                    "FROM students ORDER BY last_name, first_name");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String num  = rs.getString("student_number");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                cmbStudent.addItem(num + " - " + name);
                studentIds.add(rs.getInt("student_id"));
                studentNumbers.add(num);
                studentNames.add(name);
            }

            rs.close(); ps.close(); connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setupAddButtonListener() {
        for (ActionListener al : btnAddGrade.getActionListeners()) {
            btnAddGrade.removeActionListener(al);
        }
        btnAddGrade.addActionListener(e -> openAddGradeDialog());
    }

    private void openAddGradeDialog() {
        if (isOpen) {
            return;
        }
        isOpen = true;

        JFrame frmAddGrade = new JFrame();
        frmAddGrade.setSize(712, 645);
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
        lblCourse.setBounds(35, 213, 200, 28);
        frmAddGrade.add(lblCourse);

        String[] optCourse = {"Select course", "English", "Math", "Programming", "Science", "Filipino"};
        JComboBox<String> cmbCourse = new JComboBox<>(optCourse);
        cmbCourse.setBounds(35, 245, 634, 45);
        cmbCourse.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbCourse.setBackground(Color.WHITE);
        frmAddGrade.add(cmbCourse);

        JLabel lblGrade = new JLabel("Grade (0-100)");
        lblGrade.setForeground(Color.BLACK);
        lblGrade.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblGrade.setBounds(35, 311, 200, 28);
        frmAddGrade.add(lblGrade);

        JTextField tfGrade = new JTextField();
        tfGrade.setBounds(35, 343, 300, 45);
        tfGrade.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddGrade.add(tfGrade);

        JLabel lblSemester = new JLabel("Semester");
        lblSemester.setForeground(Color.BLACK);
        lblSemester.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSemester.setBounds(369, 311, 200, 28);
        frmAddGrade.add(lblSemester);

        String[] optSemester = {"Select", "1st Semester", "2nd Semester", "Summer"};
        JComboBox<String> cmbSemester = new JComboBox<>(optSemester);
        cmbSemester.setBounds(369, 343, 300, 45);
        cmbSemester.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cmbSemester.setBackground(Color.WHITE);
        frmAddGrade.add(cmbSemester);

        JLabel lblRemarks = new JLabel("Remarks (optional)");
        lblRemarks.setForeground(Color.BLACK);
        lblRemarks.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblRemarks.setBounds(35, 411, 200, 28);
        frmAddGrade.add(lblRemarks);

        JTextField tfRemarks = new JTextField();
        tfRemarks.setBounds(35, 443, 634, 45);
        tfRemarks.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddGrade.add(tfRemarks);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 520, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddGrade.add(btnCancel);
        btnCancel.addActionListener(e -> frmAddGrade.dispose());

        JButton btnAdd = new JButton("Add Grade");
        btnAdd.setBounds(504, 520, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddGrade.add(btnAdd);

        btnAdd.addActionListener(e -> {
            int selectedIndex = cmbStudent.getSelectedIndex();
            String course   = (String) cmbCourse.getSelectedItem();
            String grade    = tfGrade.getText().trim();
            String semester = (String) cmbSemester.getSelectedItem();
            String remarks  = tfRemarks.getText().trim();

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

            int    studentId     = studentIds.get(selectedIndex);
            String studentNumber = studentNumbers.get(selectedIndex);  // stored directly
            String studentName   = studentNames.get(selectedIndex);    // stored directly

            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/sms_db", "root", "");

                // Save student_id, student_number AND student_name so display
                // never relies on a JOIN — works even after the student is deleted
                String sql = "INSERT INTO grades " +
                             "(student_id, student_number, student_name, course_subject, grade, semester, remarks) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, studentId);
                ps.setString(2, studentNumber);
                ps.setString(3, studentName);
                ps.setString(4, course);
                ps.setBigDecimal(5, new java.math.BigDecimal(grade));
                ps.setString(6, semester);
                ps.setString(7, remarks);
                ps.executeUpdate();

                ps.close(); conn.close();

                loadGradesFromDB();
                JOptionPane.showMessageDialog(frmAddGrade,
                        "Grade added for: " + studentName, "Success", JOptionPane.INFORMATION_MESSAGE);
                frmAddGrade.dispose();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frmAddGrade,
                        "Grade must be a valid number (e.g. 85 or 92.5).", "Validation", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmAddGrade,
                        "Error saving grade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frmAddGrade.setVisible(true);
    }
}