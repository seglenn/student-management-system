package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        String[] columns = {"Date", "Student Name", "Status", "Remarks", "Course/Subject"};
        tableModel = new DefaultTableModel(columns, 0);
        attTable = new JTable(tableModel);

        attTable.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        attTable.setRowHeight(40);
        attTable.setSelectionBackground(Color.decode("#e3f2fd"));
        attTable.setGridColor(Color.decode("#e0e0e0"));

        JTableHeader header = attTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        scrollPane = new JScrollPane(attTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane);

        btnAddAtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frmAddAtt = new JFrame();
                frmAddAtt.setSize(712, 500);
                frmAddAtt.setLayout(null);
                frmAddAtt.setLocationRelativeTo(null);
                frmAddAtt.setTitle("MARK ATTENDANCE");
                frmAddAtt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frmAddAtt.getContentPane().setBackground(Color.WHITE);

                // ── Title ──────────────────────────────────────────────
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

                // ── Student ────────────────────────────────────────────
                JLabel lblStudent = new JLabel("Student");
                lblStudent.setForeground(Color.BLACK);
                lblStudent.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblStudent.setBounds(35, 115, 200, 28);
                frmAddAtt.add(lblStudent);

                String[] optStudent = {"Select student", "Juan Dela Cruz", "Maria Santos", "Jose Rizal"};
                JComboBox<String> cmbStudent = new JComboBox<>(optStudent);
                cmbStudent.setBounds(35, 147, 634, 50);
                cmbStudent.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                cmbStudent.setBackground(Color.WHITE);
                frmAddAtt.add(cmbStudent);

                // ── Date (auto from system) ────────────────────────────
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

                // ── Status ─────────────────────────────────────────────
                JLabel lblStatus = new JLabel("Status");
                lblStatus.setForeground(Color.BLACK);
                lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblStatus.setBounds(369, 213, 200, 28);
                frmAddAtt.add(lblStatus);

                String[] optStatus = {"Present", "Absent", "Late"};
                JComboBox<String> cmbStatus = new JComboBox<>(optStatus);
                cmbStatus.setBounds(369, 245, 300, 50);
                cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                cmbStatus.setBackground(Color.WHITE);
                frmAddAtt.add(cmbStatus);

                // ── Remarks ────────────────────────────────────────────
                JLabel lblRemarks = new JLabel("Remarks (optional)");
                lblRemarks.setForeground(Color.BLACK);
                lblRemarks.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblRemarks.setBounds(35, 325, 300, 28);
                frmAddAtt.add(lblRemarks);

                JTextField tfRemarks = new JTextField();
                tfRemarks.setBounds(35, 357, 634, 50);
                tfRemarks.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                frmAddAtt.add(tfRemarks);

                // ── Buttons ────────────────────────────────────────────
                JButton btnCancel = new JButton("Cancel");
                btnCancel.setBounds(384, 420, 110, 45);
                btnCancel.setForeground(Color.decode("#374151"));
                btnCancel.setBackground(new Color(243, 244, 246));
                btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                btnCancel.setFocusPainted(false);
                btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                frmAddAtt.add(btnCancel);

                JButton btnMark = new JButton("Mark Attendance");
                btnMark.setBounds(504, 420, 190, 45);
                btnMark.setForeground(Color.WHITE);
                btnMark.setBackground(Color.decode("#1f89e5"));
                btnMark.setFont(new Font("Segoe UI", Font.BOLD, 17));
                btnMark.setFocusPainted(false);
                btnMark.setBorderPainted(false);
                btnMark.setCursor(new Cursor(Cursor.HAND_CURSOR));
                frmAddAtt.add(btnMark);

                // ── Cancel action ──────────────────────────────────────
                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frmAddAtt.dispose();
                    }
                });

                // ── Mark Attendance action ─────────────────────────────
                btnMark.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String student = (String) cmbStudent.getSelectedItem();
                        String date    = tfDate.getText();
                        String status  = (String) cmbStatus.getSelectedItem();
                        String remarks = tfRemarks.getText().trim();

                        if (student.equals("Select student")) {
                            JOptionPane.showMessageDialog(frmAddAtt, "Please select a student.", "Validation", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        tableModel.addRow(new Object[]{date, student, status, remarks, ""});
                        JOptionPane.showMessageDialog(frmAddAtt, "Attendance marked for: " + student, "Success", JOptionPane.INFORMATION_MESSAGE);
                        frmAddAtt.dispose();
                    }
                });

                frmAddAtt.setVisible(true);
            }
        });

        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }
}