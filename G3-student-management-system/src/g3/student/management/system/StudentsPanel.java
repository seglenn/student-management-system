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
    JButton btnAddStud, btnViewStud, btnDeleteStud, btnSaveStud;
    ImageIcon imgDashOne;
    private JTable studTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;

    // REFERENCE PANELS
    private GradesPanel gradesPanel;
    private AttendancePanel attendancePanel;
    private DashboardPanel dashboardPanel;

    // SETTER METHODS
    public void setGradesPanel(GradesPanel gradesPanel) {
        this.gradesPanel = gradesPanel;
    }

    public void setAttendancePanel(AttendancePanel attendancePanel) {
        this.attendancePanel = attendancePanel;
    }

    public void setDashboardPanel(DashboardPanel dashboardPanel) {
        this.dashboardPanel = dashboardPanel;
    }

    StudentsPanel() {
        setLayout(null);

        // TITLE
        lblTitle = new JLabel("Students");
        lblTitle.setBounds(45, 87, 185, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        // SUBTITLE
        lblSubtxt = new JLabel("Manage enrolled students");
        lblSubtxt.setBounds(45, 137, 297, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        // BUTTONS
        btnAddStud = new JButton("Add Student");
        btnAddStud.setBounds(483, 104, 230, 61);
        btnAddStud.setBackground(Color.decode("#1f87e2"));
        btnAddStud.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddStud.setForeground(Color.WHITE);
        btnAddStud.setFocusPainted(false);
        btnAddStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAddStud);

        btnSaveStud = new JButton("Save Changes");
        btnSaveStud.setBounds(743, 104, 230, 61);
        btnSaveStud.setBackground(new Color(22, 163, 74));
        btnSaveStud.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnSaveStud.setForeground(Color.WHITE);
        btnSaveStud.setFocusPainted(false);
        btnSaveStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSaveStud);

        btnViewStud = new JButton("View Student");
        btnViewStud.setBounds(1003, 104, 230, 61);
        btnViewStud.setBackground(Color.decode("#1f87e2"));
        btnViewStud.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnViewStud.setForeground(Color.WHITE);
        btnViewStud.setFocusPainted(false);
        btnViewStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnViewStud);

        btnDeleteStud = new JButton("Delete Student");
        btnDeleteStud.setBounds(1263, 104, 230, 61);
        btnDeleteStud.setBackground(Color.decode("#e53935"));
        btnDeleteStud.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnDeleteStud.setForeground(Color.WHITE);
        btnDeleteStud.setFocusPainted(false);
        btnDeleteStud.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnDeleteStud);

        // TABLE COLUMNS
        String[] columns = {"Photo", "Student No.", "Name", "Email", "Program & Section", "Status", "Date Enrolled"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5; // Only Status is editable
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

        // STATUS DROPDOWN
        JComboBox<String> statusEditor = new JComboBox<>(new String[]{"Active", "Suspended", "Graduated", "Dropped", "Transferred"});
        studTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(statusEditor));

        // TABLE HEADER
        JTableHeader header = studTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        // PHOTO COLUMN WIDTH
        studTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        studTable.getColumnModel().getColumn(0).setMaxWidth(90);

        // SCROLL PANE
        scrollPane = new JScrollPane(studTable);
        scrollPane.setBounds(45, 200, 1450, 690);
        add(scrollPane);

        // LOAD DATA
        loadStudentsFromDB();

        // ADD STUDENT FUNCTION
        btnAddStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOpen) {
                    isOpen = true;
                    openAddStudentFrame();
                }
            }
        });

        // SAVE STATUS FUNCTION
        btnSaveStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStatusChanges();
            }
        });

        // VIEW STUDENT FUNCTION
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

        // DELETE STUDENT ACTION
        btnDeleteStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student first.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?\nGrades and attendance records will be kept.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    String studentNumber = (String) tableModel.getValueAt(selectedRow, 1);

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                         PreparedStatement st = connection.prepareStatement("DELETE FROM students WHERE student_number = ?")) {

                        st.setString(1, studentNumber);
                        st.executeUpdate();

                        tableModel.removeRow(selectedRow);

                        // REFRESH RELATED PANELS
                        if (gradesPanel != null) gradesPanel.loadGradesFromDB();
                        if (attendancePanel != null) attendancePanel.loadAttendanceFromDB();
                        if (dashboardPanel != null) dashboardPanel.refreshStats();

                        JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting student: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // DECORATIVE IMAGE
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // SAVE STATUS CHANGES
    private void saveStatusChanges() {
        if (studTable.isEditing()) {
            studTable.getCellEditor().stopCellEditing();
        }

        int successCount = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("UPDATE students SET status = ? WHERE student_number = ?")) {

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String studentNumber = (String) tableModel.getValueAt(i, 1);
                String status = (String) tableModel.getValueAt(i, 5);

                ps.setString(1, status);
                ps.setString(2, studentNumber);
                ps.addBatch();
                successCount++;
            }

            ps.executeBatch();
            JOptionPane.showMessageDialog(this, successCount + " student status(es) saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // LOAD STUDENTS FROM DATABASE
    private void loadStudentsFromDB() {
        tableModel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = connection.prepareStatement("SELECT student_number, first_name, last_name, email, program_section, status, photo_path, DATE_FORMAT(date_enrolled, '%m/%d/%Y') AS date_enrolled FROM students ORDER BY date_enrolled DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String studentNumber = rs.getString("student_number");
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                String email = rs.getString("email");
                String program = rs.getString("program_section");
                String status = rs.getString("status");
                String photoPath = rs.getString("photo_path");
                String dateEnrolled = rs.getString("date_enrolled");

                // LOAD AND SCALE PHOTO
                ImageIcon tablePhoto = null;
                if (photoPath != null && !photoPath.isEmpty()) {
                    try {
                        File imgFile = new File(photoPath);
                        if (imgFile.exists() && photoPath.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")) {
                            ImageIcon raw = new ImageIcon(photoPath);
                            Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                            tablePhoto = new ImageIcon(scaled);
                        }
                    } catch (Exception e) {
                        tablePhoto = null;
                    }
                }

                tableModel.addRow(new Object[]{tablePhoto, studentNumber, fullName, email, program, status, dateEnrolled});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading students: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // VALIDATE FILE
    private boolean isValidFile(File file) {
        if (file == null || !file.exists()) {
            JOptionPane.showMessageDialog(null, "File does not exist.");
            return false;
        }
        if (file.length() > 50 * 1024 * 1024) {
            JOptionPane.showMessageDialog(null, "File is too big! Please use a file smaller than 50MB.");
            return false;
        }
        String name = file.getName().toLowerCase();
        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".pdf")) {
            return true;
        }
        JOptionPane.showMessageDialog(null, "Please select a JPG, PNG, or PDF file.");
        return false;
    }

    // ADD STUDENT DIALOG
    private void openAddStudentFrame() {

        JLabel lblAddStud, lblSubtitle, lblStudNum, lblPhoto, lblStatus, lblFirstName,
               lblLastName, lblEmail, lblPhone, lblProgram, lblAddress, lblPhotoPreview;
        JTextField tfPhotoPath, tfFName, tfLName, tfEmail, tfPhone, tfAddress;
        JComboBox<String> cmbStatus, cmbProgram;
        JButton btnBrowse, btnCancel, btnAdd;

        JFrame frmAddStud = new JFrame();
        frmAddStud.setSize(712, 860);
        frmAddStud.setLayout(null);
        frmAddStud.setLocationRelativeTo(null);
        frmAddStud.setTitle("ADD NEW STUDENT");
        frmAddStud.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmAddStud.getContentPane().setBackground(Color.WHITE);

        frmAddStud.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) { isOpen = false; }
        });

        // HEADER
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

        // STUDENT NUMBER INFO
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

        // PHOTO SECTION
        lblPhoto = new JLabel("Student File");
        lblPhoto.setForeground(Color.BLACK);
        lblPhoto.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhoto.setBounds(35, 168, 250, 28);
        frmAddStud.add(lblPhoto);

        lblPhotoPreview = new JLabel();
        lblPhotoPreview.setBounds(35, 200, 80, 80);
        lblPhotoPreview.setBackground(Color.decode("#e3f2fd"));
        lblPhotoPreview.setOpaque(true);
        lblPhotoPreview.setBorder(BorderFactory.createLineBorder(Color.decode("#1f87e2"), 1));
        lblPhotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhotoPreview.setText("No File");
        lblPhotoPreview.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPhotoPreview.setForeground(Color.decode("#737373"));
        frmAddStud.add(lblPhotoPreview);

        tfPhotoPath = new JTextField("No file chosen");
        tfPhotoPath.setBounds(125, 220, 390, 45);
        tfPhotoPath.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfPhotoPath.setForeground(Color.GRAY);
        tfPhotoPath.setEditable(false);
        frmAddStud.add(tfPhotoPath);

        String[] chosenFilePath = {""};

        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(525, 220, 110, 45);
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setBackground(Color.decode("#1f87e2"));
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBrowse.setFocusPainted(false);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnBrowse);

        JLabel finalPreview = lblPhotoPreview;
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image & PDF Files", "jpg", "jpeg", "png", "pdf"));
                if (chooser.showOpenDialog(frmAddStud) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (isValidFile(file)) {
                        try {
                            chosenFilePath[0] = file.getAbsolutePath();
                            tfPhotoPath.setText(file.getName());
                            tfPhotoPath.setForeground(Color.BLACK);
                            String fn = file.getName().toLowerCase();
                            if (fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".png")) {
                                ImageIcon icon = new ImageIcon(chosenFilePath[0]);
                                Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                                finalPreview.setIcon(new ImageIcon(scaled));
                                finalPreview.setText("");
                            } else {
                                finalPreview.setIcon(null);
                                finalPreview.setText("PDF");
                                finalPreview.setFont(new Font("Segoe UI", Font.BOLD, 14));
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frmAddStud, "Could not load the file.");
                            chosenFilePath[0] = "";
                            tfPhotoPath.setText("No file chosen");
                            tfPhotoPath.setForeground(Color.GRAY);
                            finalPreview.setIcon(null);
                            finalPreview.setText("No File");
                        }
                    }
                }
            }
        });

        // STATUS
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

        // FIRST NAME
        lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFirstName.setBounds(35, 393, 200, 28);
        frmAddStud.add(lblFirstName);

        tfFName = new JTextField();
        tfFName.setBounds(35, 425, 300, 45);
        tfFName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfFName);

        // LAST NAME
        lblLastName = new JLabel("Last Name");
        lblLastName.setForeground(Color.BLACK);
        lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLastName.setBounds(369, 393, 200, 28);
        frmAddStud.add(lblLastName);

        tfLName = new JTextField();
        tfLName.setBounds(369, 425, 300, 45);
        tfLName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfLName);

        // EMAIL
        lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblEmail.setBounds(35, 486, 122, 28);
        frmAddStud.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(35, 518, 634, 45);
        tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfEmail);

        // PHONE
        lblPhone = new JLabel("Phone (11 digits)");
        lblPhone.setForeground(Color.BLACK);
        lblPhone.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhone.setBounds(35, 579, 200, 28);
        frmAddStud.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(35, 611, 300, 45);
        tfPhone.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        tfPhone.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }
        });
        frmAddStud.add(tfPhone);

        // PROGRAM
        lblProgram = new JLabel("Program & Section");
        lblProgram.setForeground(Color.BLACK);
        lblProgram.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblProgram.setBounds(369, 579, 250, 28);
        frmAddStud.add(lblProgram);

        String[] optProgram = {"BSIT 1-1", "BSIT 1-2", "BSIT 2-1", "BSIT 2-2", "DIT 1-1", "DIT 2-1"};
        cmbProgram = new JComboBox<>(optProgram);
        cmbProgram.setBounds(369, 611, 300, 45);
        cmbProgram.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(cmbProgram);

        // ADDRESS
        lblAddress = new JLabel("Address");
        lblAddress.setForeground(Color.BLACK);
        lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAddress.setBounds(35, 672, 122, 28);
        frmAddStud.add(lblAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(35, 704, 634, 45);
        tfAddress.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfAddress);

        // BUTTONS
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(384, 762, 110, 45);
        btnCancel.setForeground(Color.decode("#374151"));
        btnCancel.setBackground(new Color(243, 244, 246));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnCancel);
        btnCancel.addActionListener(e -> frmAddStud.dispose());

        btnAdd = new JButton("Add Student");
        btnAdd.setBounds(504, 762, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnAdd);

        JComboBox<String> fCmbStatus = cmbStatus;
        JComboBox<String> fCmbProgram = cmbProgram;
        JTextField fFName = tfFName;
        JTextField fLName = tfLName;
        JTextField fEmail = tfEmail;
        JTextField fPhone = tfPhone;
        JTextField fAddress = tfAddress;

        // SUBMIT ACTION
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = fFName.getText().trim();
                String lastName = fLName.getText().trim();
                String email = fEmail.getText().trim();
                String phone = fPhone.getText().trim();
                String address = fAddress.getText().trim();
                String status = (String) fCmbStatus.getSelectedItem();
                String program = (String) fCmbProgram.getSelectedItem();

                // VALIDATE REQUIRED FIELDS
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddStud, "Please fill in all required fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // VALIDATE NAME FORMAT
                if (!firstName.matches("[a-zA-Z ]+")) {
                    JOptionPane.showMessageDialog(frmAddStud, "First name must contain letters only. No numbers or special characters.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!lastName.matches("[a-zA-Z ]+")) {
                    JOptionPane.showMessageDialog(frmAddStud, "Last name must contain letters only. No numbers or special characters.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // VALIDATE EMAIL
                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(frmAddStud, "Please enter a valid email address.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // VALIDATE PHONE
                if (!phone.isEmpty() && !phone.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(frmAddStud, "Phone number must be exactly 11 digits.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // INSERT INTO DATABASE
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                     PreparedStatement ps = connection.prepareStatement("INSERT INTO students (first_name, last_name, email, phone, address, program_section, status, photo_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, email);
                    ps.setString(4, phone);
                    ps.setString(5, address);
                    ps.setString(6, program);
                    ps.setString(7, status);
                    ps.setString(8, chosenFilePath[0]);
                    ps.executeUpdate();

                    // GET GENERATED ID
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    int newStudentId = 0;
                    if (generatedKeys.next()) newStudentId = generatedKeys.getInt(1);

                    // FETCH STUDENT NUMBER AND DATE
                    try (PreparedStatement fetchPs = connection.prepareStatement("SELECT student_number, DATE_FORMAT(date_enrolled, '%m/%d/%Y') AS date_enrolled FROM students WHERE student_id = ?")) {
                        fetchPs.setInt(1, newStudentId);
                        ResultSet rs = fetchPs.executeQuery();

                        String studentNumber = "";
                        String dateEnrolled = "";
                        if (rs.next()) {
                            studentNumber = rs.getString("student_number");
                            dateEnrolled = rs.getString("date_enrolled");
                        }

                        // PREPARE PHOTO FOR TABLE
                        ImageIcon tablePhoto = null;
                        
                        // VALIDATES FILE TYPE
                        if (!chosenFilePath[0].isEmpty()) {
                            try {
                                String fn = chosenFilePath[0].toLowerCase();
                                if (fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".png")) {
                                    ImageIcon raw = new ImageIcon(chosenFilePath[0]);
                                    Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                                    tablePhoto = new ImageIcon(scaled);
                                }
                            } catch (Exception ex) { tablePhoto = null; }
                        }

                        // ADD TO TABLE
                        String fullName = firstName + " " + lastName;
                        tableModel.addRow(new Object[]{tablePhoto, studentNumber, fullName, email, program, status, dateEnrolled});

                        // REFRESH DASHBOARD
                        if (dashboardPanel != null) dashboardPanel.refreshStats();

                        JOptionPane.showMessageDialog(frmAddStud, "Student added successfully!\nStudent Number: " + studentNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
                        frmAddStud.dispose();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    
                    // DUPLICATE ENTRY 
                    if (ex.getMessage().contains("Duplicate entry")) {
                        JOptionPane.showMessageDialog(frmAddStud, "Email already exists. Please use a different email.", "Duplicate Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frmAddStud, "Error saving student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        frmAddStud.setVisible(true);
    }

    // OPEN VIEW STUDENT DIALOG
    private void openViewFrame(int row) {
        ImageIcon photoIcon = (ImageIcon) tableModel.getValueAt(row, 0);
        String studentNumber = (String) tableModel.getValueAt(row, 1);
        String name = (String) tableModel.getValueAt(row, 2);
        String email = (String) tableModel.getValueAt(row, 3);
        String program = (String) tableModel.getValueAt(row, 4);
        String status = (String) tableModel.getValueAt(row, 5);
        String dateEnrolled = (String) tableModel.getValueAt(row, 6);

        new ViewStudent(photoIcon, studentNumber, name, email, program, status, dateEnrolled);
    }
}