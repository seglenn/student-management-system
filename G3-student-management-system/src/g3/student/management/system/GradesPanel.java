package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GradesPanel extends JPanel {
    
    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddGrade;
    ImageIcon imgDashOne;
    private JTable gradeTable;
    private DefaultTableModel tableModel; 
    private JScrollPane scrollPane;
    private boolean isOpen = false;  
    
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
        
        String[] columns = {"Date", "Student Name", "Course/Subject", "Grade", "Remarks"};
        tableModel = new DefaultTableModel(columns, 0);
        gradeTable = new JTable(tableModel);
        
        gradeTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gradeTable.setRowHeight(45);
        gradeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        gradeTable.getTableHeader().setBackground(Color.decode("#1f87e2"));
        gradeTable.getTableHeader().setForeground(Color.WHITE);
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
        
        setupAddButtonListener();
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }
    
    private void setupAddButtonListener() {
        for (ActionListener al : btnAddGrade.getActionListeners()) {
            btnAddGrade.removeActionListener(al);
        }
        btnAddGrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddGradeDialog();
            }
        });
    }
    
    private void openAddGradeDialog() {
        
        if (isOpen) {
            return;
        }
        
        isOpen = true;
        
        JFrame frmAddGrade = new JFrame();
        frmAddGrade.setSize(712, 680);
        frmAddGrade.setLayout(null);
        frmAddGrade.setLocationRelativeTo(null);
        frmAddGrade.setTitle("ADD GRADE");
        frmAddGrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAddGrade.getContentPane().setBackground(Color.WHITE);
        
        frmAddGrade.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOpen = false;
            }
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

        String[] optStudent = {"Select student", "Juan Dela Cruz", "Maria Santos", "Jose Rizal"};
        JComboBox<String> cmbStudent = new JComboBox<>(optStudent);
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
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAddGrade.dispose();
            }
        });

        JButton btnAdd = new JButton("Add Grade");
        btnAdd.setBounds(504, 520, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddGrade.add(btnAdd);
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String student = (String) cmbStudent.getSelectedItem();
                String course = (String) cmbCourse.getSelectedItem();
                String grade = tfGrade.getText().trim();
                String semester = (String) cmbSemester.getSelectedItem();
                String remarks = tfRemarks.getText().trim();

                if (student.equals("Select student") || course.equals("Select course") || grade.isEmpty() || semester.equals("Select")) {
                    JOptionPane.showMessageDialog(frmAddGrade, "Please fill in all the required information", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.util.Date today = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                String currentDate = sdf.format(today);
                
                tableModel.addRow(new Object[]{currentDate, student, course, grade, remarks});

                JOptionPane.showMessageDialog(frmAddGrade,
                        "Grade added for: " + student,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                frmAddGrade.dispose();
            }
        });
        
        frmAddGrade.setVisible(true);
    }
}