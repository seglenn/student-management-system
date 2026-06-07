package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class StudentsPanel extends JPanel {

    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddStud, btnViewStud, btnDeleteStud;
    ImageIcon imgDashOne;
    private JTable studTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private boolean isOpen = false;

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

        // ===== BUTTONS AT THE TOP =====
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

        // ===== TABLE SETUP =====
        String[] columns = {"Photo", "Name", "Email", "Program & Section", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int col) {
                if (col == 0) {
                    return ImageIcon.class;
                }
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

        studTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        studTable.getColumnModel().getColumn(0).setMaxWidth(60);

        scrollPane = new JScrollPane(studTable);
        scrollPane.setBounds(45, 160, 1450, 690);
        add(scrollPane);

        // ===== BUTTON ACTIONS =====
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

                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);

     
    }

 

    private void openAddStudentFrame() {
        
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

        JLabel lblAddStud = new JLabel("Add new student");
        lblAddStud.setForeground(Color.BLACK);
        lblAddStud.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblAddStud.setBounds(35, 36, 357, 39);
        frmAddStud.add(lblAddStud);

        JLabel lblSubtitle = new JLabel("Fill in the required details below");
        lblSubtitle.setForeground(Color.decode("#737373"));
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSubtitle.setBounds(35, 70, 372, 27);
        frmAddStud.add(lblSubtitle);

        JLabel lblStudNum = new JLabel("Student number will be auto-generated (e.g. 2026-0001)");
        lblStudNum.setForeground(Color.decode("#737373"));
        lblStudNum.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStudNum.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        lblStudNum.setBackground(Color.decode("#F9FAFB"));
        lblStudNum.setOpaque(true);
        lblStudNum.setBounds(35, 112, 634, 40);
        frmAddStud.add(lblStudNum);

        // Photo Section
        JLabel lblPhoto = new JLabel("Student Photo");
        lblPhoto.setForeground(Color.BLACK);
        lblPhoto.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhoto.setBounds(35, 168, 200, 28);
        frmAddStud.add(lblPhoto);

        JLabel lblPhotoPreview = new JLabel();
        lblPhotoPreview.setBounds(35, 200, 80, 80);
        lblPhotoPreview.setBackground(Color.decode("#e3f2fd"));
        lblPhotoPreview.setOpaque(true);
        lblPhotoPreview.setBorder(BorderFactory.createLineBorder(Color.decode("#1f87e2"), 1));
        lblPhotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblPhotoPreview.setText("No Photo");
        lblPhotoPreview.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPhotoPreview.setForeground(Color.decode("#737373"));
        frmAddStud.add(lblPhotoPreview);

        JTextField tfPhotoPath = new JTextField("No file chosen");
        tfPhotoPath.setBounds(125, 220, 390, 45);
        tfPhotoPath.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfPhotoPath.setForeground(Color.GRAY);
        tfPhotoPath.setEditable(false);
        frmAddStud.add(tfPhotoPath);

        String[] chosenImagePath = {""};

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(525, 220, 110, 45);
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setBackground(Color.decode("#1f87e2"));
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBrowse.setFocusPainted(false);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnBrowse);

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
                    lblPhotoPreview.setIcon(new ImageIcon(scaled));
                    lblPhotoPreview.setText("");
                }
            }
        });

        // Status
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStatus.setBounds(35, 300, 122, 28);
        frmAddStud.add(lblStatus);

        String[] optStatus = {"Active", "Suspended", "Graduated", "Dropped", "Transferred"};
        JComboBox<String> cmbStatus = new JComboBox<>(optStatus);
        cmbStatus.setBounds(35, 332, 200, 45);
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(cmbStatus);

        // First Name
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFirstName.setBounds(35, 393, 200, 28);
        frmAddStud.add(lblFirstName);

        JTextField tfFName = new JTextField();
        tfFName.setBounds(35, 425, 300, 45);
        tfFName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfFName);

        // Last Name
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setForeground(Color.BLACK);
        lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLastName.setBounds(369, 393, 200, 28);
        frmAddStud.add(lblLastName);

        JTextField tfLName = new JTextField();
        tfLName.setBounds(369, 425, 300, 45);
        tfLName.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfLName);

        // Email
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblEmail.setBounds(35, 486, 122, 28);
        frmAddStud.add(lblEmail);

        JTextField tfEmail = new JTextField();
        tfEmail.setBounds(35, 518, 634, 45);
        tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfEmail);

        // Phone
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setForeground(Color.BLACK);
        lblPhone.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPhone.setBounds(35, 579, 122, 28);
        frmAddStud.add(lblPhone);

        JTextField tfPhone = new JTextField();
        tfPhone.setBounds(35, 611, 300, 45);
        tfPhone.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfPhone);

        // Program & Section
        JLabel lblProgram = new JLabel("Program & Section");
        lblProgram.setForeground(Color.BLACK);
        lblProgram.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblProgram.setBounds(369, 579, 250, 28);
        frmAddStud.add(lblProgram);

        String[] optProgram = {"BSIT - 1A", "BSIT - 1B", "BSIT - 2A", "BSIT - 2B", "CS - 1A", "CS - 2A", "DIT - 1A"};
        JComboBox<String> cmbProgram = new JComboBox<>(optProgram);
        cmbProgram.setBounds(369, 611, 300, 45);
        cmbProgram.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(cmbProgram);

        // Address
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setForeground(Color.BLACK);
        lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAddress.setBounds(35, 672, 122, 28);
        frmAddStud.add(lblAddress);

        JTextField tfAddress = new JTextField();
        tfAddress.setBounds(35, 704, 634, 45);
        tfAddress.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        frmAddStud.add(tfAddress);

        // Buttons
        JButton btnCancel = new JButton("Cancel");
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

        JButton btnAdd = new JButton("Add Student");
        btnAdd.setBounds(504, 762, 160, 45);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.decode("#1f89e5"));
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frmAddStud.add(btnAdd);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = tfFName.getText().trim();
                String lastName = tfLName.getText().trim();
                String email = tfEmail.getText().trim();
                String phone = tfPhone.getText().trim();
                String address = tfAddress.getText().trim();
                String status = (String) cmbStatus.getSelectedItem();
                String program = (String) cmbProgram.getSelectedItem();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAddStud, "Please fill in all the information.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String fullName = firstName + " " + lastName;

                ImageIcon tablePhoto = null;
                if (!chosenImagePath[0].isEmpty()) {
                    ImageIcon raw = new ImageIcon(chosenImagePath[0]);
                    Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                    tablePhoto = new ImageIcon(scaled);
                }

                tableModel.addRow(new Object[]{tablePhoto, fullName, email, program, status});

                JOptionPane.showMessageDialog(frmAddStud, "Student added: " + fullName, "Success", JOptionPane.INFORMATION_MESSAGE);
                frmAddStud.dispose();
            }
        });

        frmAddStud.setVisible(true);
    }

    private void openViewFrame(int row) {

        ImageIcon photoIcon = (ImageIcon) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        String email = (String) tableModel.getValueAt(row, 2);
        String program = (String) tableModel.getValueAt(row, 3);
        String status = (String) tableModel.getValueAt(row, 4);

        JFrame viewWindow = new JFrame();
        viewWindow.setSize(460, 530);
        viewWindow.setLayout(null);
        viewWindow.setLocationRelativeTo(null);
        viewWindow.setTitle("STUDENT DETAILS");
        viewWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewWindow.getContentPane().setBackground(Color.WHITE);

        // Photo Section
        JLabel photoLabel = new JLabel();
        photoLabel.setBounds(155, 30, 150, 150);
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

        // Title Section
        JLabel titleLabel = new JLabel("Student Details");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(35, 200, 300, 35);
        viewWindow.add(titleLabel);

        JSeparator separatorLine = new JSeparator();
        separatorLine.setBounds(35, 240, 390, 2);
        separatorLine.setForeground(new Color(220, 220, 220));
        viewWindow.add(separatorLine);

        // Student Information
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(Color.decode("#737373"));
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setBounds(35, 255, 130, 28);
        viewWindow.add(nameLabel);

        JLabel nameValue = new JLabel(name);
        nameValue.setForeground(Color.BLACK);
        nameValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameValue.setBounds(175, 255, 240, 28);
        viewWindow.add(nameValue);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#737373"));
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailLabel.setBounds(35, 295, 130, 28);
        viewWindow.add(emailLabel);

        JLabel emailValue = new JLabel(email);
        emailValue.setForeground(Color.BLACK);
        emailValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        emailValue.setBounds(175, 295, 240, 28);
        viewWindow.add(emailValue);

        JLabel programLabel = new JLabel("Program:");
        programLabel.setForeground(Color.decode("#737373"));
        programLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        programLabel.setBounds(35, 335, 130, 28);
        viewWindow.add(programLabel);

        JLabel programValue = new JLabel(program);
        programValue.setForeground(Color.BLACK);
        programValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        programValue.setBounds(175, 335, 240, 28);
        viewWindow.add(programValue);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.decode("#737373"));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setBounds(35, 375, 130, 28);
        viewWindow.add(statusLabel);

        JLabel statusValue = new JLabel(status);
        statusValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        if (status.equals("Active")) {
            statusValue.setForeground(Color.decode("#16a34a"));
        } else {
            statusValue.setForeground(Color.decode("#e53935"));
        }
        statusValue.setBounds(175, 375, 240, 28);
        viewWindow.add(statusValue);

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(155, 440, 150, 45);
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