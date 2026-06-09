package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.sql.*;

public class StudentsPanel extends JPanel {

    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddStud, btnViewStud, btnDeleteStud;
    ImageIcon imgDashOne;
    private JTable studTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;
    private GradesPanel gradesPanel;
    private AttendancePanel attendancePanel;

    StudentsPanel() {
        setLayout(null);

        lblTitle = new JLabel("Students");
        lblTitle.setBounds(45, 87, 185, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        lblSubtxt = new JLabel("Manage enrolled students");
        lblSubtxt.setBounds(45, 137, 297, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        btnAddStud = new JButton("Add Student");
        btnAddStud.setBounds(1100, 104, 140, 45);
        btnAddStud.setBackground(Color.decode("#1f87e2"));
        btnAddStud.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAddStud.setForeground(Color.WHITE);
        btnAddStud.setFocusPainted(false);
        btnAddStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddStud);

        btnViewStud = new JButton("View Student");
        btnViewStud.setBounds(1250, 104, 140, 45);
        btnViewStud.setBackground(Color.decode("#1f87e2"));
        btnViewStud.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnViewStud.setForeground(Color.WHITE);
        btnViewStud.setFocusPainted(false);
        btnViewStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnViewStud);

        btnDeleteStud = new JButton("Delete Student");
        btnDeleteStud.setBounds(1400, 104, 140, 45);
        btnDeleteStud.setBackground(Color.decode("#e53935"));
        btnDeleteStud.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDeleteStud.setForeground(Color.WHITE);
        btnDeleteStud.setFocusPainted(false);
        btnDeleteStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnDeleteStud);

        String[] columns = {"Photo", "Student No.", "Name", "Email", "Program & Section", "Status", "Date Enrolled"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int col) {
                if (col == 0) return ImageIcon.class;
                return String.class;
            }
        };

        studTable = new JTable(tableModel);
        studTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        studTable.setRowHeight(55);
        studTable.setSelectionBackground(Color.decode("#e3f2fd"));
        studTable.setGridColor(Color.decode("#e0e0e0"));

        JTableHeader header = studTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        studTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        studTable.getColumnModel().getColumn(0).setMaxWidth(90);

        scrollPane = new JScrollPane(studTable);
        scrollPane.setBounds(45, 200, 1450, 690);
        add(scrollPane);

        loadStudentsFromDB();

        btnAddStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOpen) {
                    isOpen = true;
                    openAddStudentFrame();
                }
            }
        });

        btnViewStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student first.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                openViewFrame(selectedRow);
            }
        });

        btnDeleteStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student first.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this student?\nGrades and attendance records will be kept.",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    String studentNumber = (String) tableModel.getValueAt(selectedRow, 1);

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/sms_db", "root", "");
                        PreparedStatement st = connection.prepareStatement(
                                "DELETE FROM students WHERE student_number = ?");
                        st.setString(1, studentNumber);
                        st.executeUpdate();
                        st.close();
                        connection.close();

                        tableModel.removeRow(selectedRow);

                        JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting student: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    public void setGradesPanel(GradesPanel gradesPanel) {
        this.gradesPanel = gradesPanel;
    }

    public void setAttendancePanel(AttendancePanel attendancePanel) {
        this.attendancePanel = attendancePanel;
    }

    private void loadStudentsFromDB() {
        tableModel.setRowCount(0);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT student_number, first_name, last_name, email, phone, " +
                    "program_section, status, photo_path, " +
                    "DATE_FORMAT(date_enrolled, '%m/%d/%Y') AS date_enrolled " +
                    "FROM students ORDER BY date_enrolled DESC");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String studentNumber = rs.getString("student_number");
                String fullName      = rs.getString("first_name") + " " + rs.getString("last_name");
                String email         = rs.getString("email");
                String program       = rs.getString("program_section");
                String status        = rs.getString("status");
                String photoPath     = rs.getString("photo_path");
                String dateEnrolled  = rs.getString("date_enrolled");

                ImageIcon tablePhoto = null;
                if (photoPath != null && !photoPath.isEmpty()) {
                    ImageIcon raw = new ImageIcon(photoPath);
                    Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                    tablePhoto = new ImageIcon(scaled);
                }

                tableModel.addRow(new Object[]{tablePhoto, studentNumber, fullName, email, program, status, dateEnrolled});
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading students: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== GENERATE STUDENT NUMBER (Java side - no wasted numbers) =====
    private String generateStudentNumber() {
        int currentYear = java.time.Year.now().getValue();
        String prefix = currentYear + "-";
        
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sms_db", "root", "");
            
            String sql = "SELECT student_number FROM students " +
                         "WHERE student_number LIKE ? " +
                         "ORDER BY student_number DESC LIMIT 1";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            
            int lastNumber = 0;
            if (rs.next()) {
                String lastStudentNumber = rs.getString("student_number");
                String[] parts = lastStudentNumber.split("-");
                if (parts.length == 2) {
                    lastNumber = Integer.parseInt(parts[1]);
                }
            }
            
            rs.close();
            ps.close();
            conn.close();
            
            int nextNumber = lastNumber + 1;
            return String.format("%s%04d", prefix, nextNumber);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return prefix + "0001";
        }
    }

    private void openAddStudentFrame() {

        JLabel lblAddStud, lblSubtitle, lblStudNum, lblPhoto, lblStatus, lblFirstName, lblLastName, lblEmail, lblPhone, lblProgram, lblAddress;
        JTextField tfPhotoPath, tfFName, tfLName, tfEmail, tfPhone, tfAddress;
        JComboBox<String> cmbStatus, cmbProgram;
        JButton btnBrowse, btnCancel, btnAdd;
        JLabel lblPhotoPreview;

        JFrame frmAddStud = new JFrame();
        frmAddStud.setSize(712, 820);
        frmAddStud.setLayout(null);
        frmAddStud.setLocationRelativeTo(null);
        frmAddStud.setTitle("ADD NEW STUDENT");
        frmAddStud.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAddStud.getContentPane().setBackground(Color.WHITE);

        frmAddStud.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isOpen = false;
            }
        });

        lblAddStud = new JLabel("Add new student");
        lblAddStud.setForeground(Color.BLACK);
        lblAddStud.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblAddStud.setBounds(35, 36, 357, 39);
        frmAddStud.add(lblAddStud);

        lblSubtitle = new JLabel("Fill in the required details below");
        lblSubtitle.setForeground(Color.decode("#737373"));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSubtitle.setBounds(35, 70, 372, 27);
        frmAddStud.add(lblSubtitle);

        lblStudNum = new JLabel("Student number will be auto-generated (e.g. 2026-0001)");
        lblStudNum.setForeground(Color.decode("#737373"));
        lblStudNum.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStudNum.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        lblStudNum.setBackground(Color.decode("#F9FAFB"));
        lblStudNum.setOpaque(true);
        lblStudNum.setBounds(35, 112, 634, 40);
        frmAddStud.add(lblStudNum);

        lblPhoto = new JLabel("Student Photo");
        lblPhoto.setForeground(Color.BLACK);
        lblPhoto.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhoto.setBounds(35, 168, 200, 28);
        frmAddStud.add(lblPhoto);

        lblPhotoPreview = new JLabel();
        lblPhotoPreview.setBounds(35, 200, 80, 80);
        lblPhotoPreview.setBackground(Color.decode("#e3f2fd"));
        lblPhotoPreview.setOpaque(true);
        lblPhotoPreview.setBorder(BorderFactory.createLineBorder(Color.decode("#1f87e2"), 1));
        lblPhotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhotoPreview.setText("No Photo");
        lblPhotoPreview.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPhotoPreview.setForeground(Color.decode("#737373"));
        frmAddStud.add(lblPhotoPreview);

        tfPhotoPath = new JTextField("No file chosen");
        tfPhotoPath.setBounds(125, 220, 390, 45);
        tfPhotoPath.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfPhotoPath.setForeground(Color.GRAY);
        tfPhotoPath.setEditable(false);
        frmAddStud.add(tfPhotoPath);

        String[] chosenImagePath = {""};

        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(525, 220, 110, 45);
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setBackground(Color.decode("#1f87e2"));
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBrowse.setFocusPainted(false);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnBrowse);

        JLabel finalLblPhotoPreview = lblPhotoPreview;
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
                int result = chooser.showOpenDialog(frmAddStud);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    chosenImagePath[0] = file.getAbsolutePath();
                    tfPhotoPath.setText(file.getName());
                    tfPhotoPath.setForeground(Color.BLACK);
                    ImageIcon icon = new ImageIcon(chosenImagePath[0]);
                    Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    finalLblPhotoPreview.setIcon(new ImageIcon(scaled));
                    finalLblPhotoPreview.setText("");
                }
            }
        });

        lblStatus = new JLabel("Status");
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStatus.setBounds(35, 300, 122, 28);
        frmAddStud.add(lblStatus);

        String[] optStatus = {"Active", "Suspended", "Graduated", "Dropped", "Transferred"};
        cmbStatus = new JComboBox<>(optStatus);
        cmbStatus.setBounds(35, 332, 200, 45);
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(cmbStatus);

        lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFirstName.setBounds(35, 393, 200, 28);
        frmAddStud.add(lblFirstName);

        tfFName = new JTextField();
        tfFName.setBounds(35, 425, 300, 45);
        tfFName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfFName);

        lblLastName = new JLabel("Last Name");
        lblLastName.setForeground(Color.BLACK);
        lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLastName.setBounds(369, 393, 200, 28);
        frmAddStud.add(lblLastName);

        tfLName = new JTextField();
        tfLName.setBounds(369, 425, 300, 45);
        tfLName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfLName);

        lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblEmail.setBounds(35, 486, 122, 28);
        frmAddStud.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(35, 518, 634, 45);
        tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfEmail);

        lblPhone = new JLabel("Phone");
        lblPhone.setForeground(Color.BLACK);
        lblPhone.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhone.setBounds(35, 579, 122, 28);
        frmAddStud.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(35, 611, 300, 45);
        tfPhone.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfPhone);

        lblProgram = new JLabel("Program & Section");
        lblProgram.setForeground(Color.BLACK);
        lblProgram.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblProgram.setBounds(369, 579, 250, 28);
        frmAddStud.add(lblProgram);

        String[] optProgram = {"BSIT - 1A", "BSIT - 1B", "BSIT - 2A", "BSIT - 2B", "CS - 1A", "CS - 2A", "DIT - 1A"};
        cmbProgram = new JComboBox<>(optProgram);
        cmbProgram.setBounds(369, 611, 300, 45);
        cmbProgram.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(cmbProgram);

        lblAddress = new JLabel("Address");
        lblAddress.setForeground(Color.BLACK);
        lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAddress.setBounds(35, 672, 122, 28);
        frmAddStud.add(lblAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(35, 704, 634, 45);
        tfAddress.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfAddress);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 762, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnCancel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAddStud.dispose();
            }
        });

        btnAdd = new JButton("Add Student");
        btnAdd.setBounds(504, 762, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnAdd);

        JComboBox<String> finalCmbStatus  = cmbStatus;
        JComboBox<String> finalCmbProgram = cmbProgram;
        JTextField finalTfFName   = tfFName;
        JTextField finalTfLName   = tfLName;
        JTextField finalTfEmail   = tfEmail;
        JTextField finalTfPhone   = tfPhone;
        JTextField finalTfAddress = tfAddress;

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = finalTfFName.getText().trim();
                String lastName  = finalTfLName.getText().trim();
                String email     = finalTfEmail.getText().trim();
                String phone     = finalTfPhone.getText().trim();
                String address   = finalTfAddress.getText().trim();
                String status    = (String) finalCmbStatus.getSelectedItem();
                String program   = (String) finalCmbProgram.getSelectedItem();

                // ===== VALIDATION FIRST (NO NUMBER GENERATED YET) =====
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddStud, 
                        "Please fill in First Name, Last Name, and Email.", 
                        "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(frmAddStud, 
                        "Please enter a valid email address.", 
                        "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // ===== ONLY GENERATE NUMBER AFTER VALIDATION PASSES =====
                String studentNumber = generateStudentNumber();

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");

                    // Insert with generated student number (no TEMP needed)
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO students (student_number, first_name, last_name, email, phone, address, program_section, status, photo_path) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, studentNumber);
                    ps.setString(2, firstName);
                    ps.setString(3, lastName);
                    ps.setString(4, email);
                    ps.setString(5, phone);
                    ps.setString(6, address);
                    ps.setString(7, program);
                    ps.setString(8, status);
                    ps.setString(9, chosenImagePath[0]);
                    ps.executeUpdate();

                    ps.close();
                    connection.close();

                    ImageIcon tablePhoto = null;
                    if (!chosenImagePath[0].isEmpty()) {
                        ImageIcon raw = new ImageIcon(chosenImagePath[0]);
                        Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                        tablePhoto = new ImageIcon(scaled);
                    }

                    String fullName = firstName + " " + lastName;
                    java.util.Date today = new java.util.Date();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                    String dateEnrolled = sdf.format(today);
                    
                    tableModel.addRow(new Object[]{tablePhoto, studentNumber, fullName, email, program, status, dateEnrolled});

                    JOptionPane.showMessageDialog(frmAddStud, 
                        "Student added successfully!\nStudent Number: " + studentNumber, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmAddStud.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    if (ex.getMessage().contains("Duplicate entry")) {
                        JOptionPane.showMessageDialog(frmAddStud, 
                            "Student number already exists. Please try again.", 
                            "Duplicate Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frmAddStud, 
                            "Error saving student: " + ex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        frmAddStud.setVisible(true);
    }

    private void openViewFrame(int row) {
        ImageIcon photoIcon  = (ImageIcon) tableModel.getValueAt(row, 0);
        String studentNumber = (String) tableModel.getValueAt(row, 1);
        String name          = (String) tableModel.getValueAt(row, 2);
        String email         = (String) tableModel.getValueAt(row, 3);
        String program       = (String) tableModel.getValueAt(row, 4);
        String status        = (String) tableModel.getValueAt(row, 5);
        String dateEnrolled  = (String) tableModel.getValueAt(row, 6);

        new ViewStudent(photoIcon, studentNumber, name, email, program, status, dateEnrolled);
    }
}