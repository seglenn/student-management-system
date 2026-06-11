package g3.student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class DashboardPanel extends JPanel implements ActionListener {

    // FIELDS
    private JLabel lblTitle, lblSubtxt, imgDisplay, lblStudIcon;
    private JLabel lblAttIcon, lblTaskIcon, lblProgressPercent;
    private JLabel lblTotalStud, lblAttRate, lblTaskProg, lblAttendanceRate, lblNoStuds;
    private JPanel pnlStud, pnlAtt, pnlTask;
    private ImageIcon imgDashOne, imgStudIcon, imgAttIcon, imgTaskIcon;

    // TASK PANEL
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JScrollPane taskScrollPane;
    private ArrayList<String> taskIds;
    private JButton btnAdd, btnDelete, btnClear;
    private JTextField tfTaskInput;
    private JPanel taskPanel;

    // SCHEDULE PANEL
    private JTable scheduleTable;
    private DefaultTableModel scheduleModel;
    private JScrollPane scheduleScrollPane;
    private JButton btnSchedAdd, btnSchedDelete, btnSchedSave;
    private JPanel schedulePanel;

    public DashboardPanel() {
        setLayout(null);
        setBackground(new Color(244, 245, 247));

        taskIds = new ArrayList<>();
        listModel = new DefaultListModel<>();

        // LOAD IMAGES
        imgStudIcon = new ImageIcon("images/student-icon.png");
        imgAttIcon = new ImageIcon("images/att-icon.png");
        imgTaskIcon = new ImageIcon("images/task-icon.png");

        // ICON LABELS
        lblStudIcon = new JLabel(imgStudIcon);
        lblStudIcon.setBounds(385, 250, 89, 78);
        add(lblStudIcon);

        lblAttIcon = new JLabel(imgAttIcon);
        lblAttIcon.setBounds(891, 250, 83, 85);
        add(lblAttIcon);

        lblTaskIcon = new JLabel(imgTaskIcon);
        lblTaskIcon.setBounds(1403, 254, 77, 84);
        add(lblTaskIcon);

        // TITLE
        lblTitle = new JLabel("Dashboard");
        lblTitle.setBounds(45, 87, 230, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);

        // SUBTITLE
        lblSubtxt = new JLabel("Overview");
        lblSubtxt.setBounds(45, 137, 104, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);

        // STATS NUMBERS
        lblNoStuds = new JLabel("0");
        lblNoStuds.setBounds(67, 262, 197, 75);
        lblNoStuds.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblNoStuds.setForeground(Color.BLACK);
        add(lblNoStuds);

        lblAttendanceRate = new JLabel("0%");
        lblAttendanceRate.setBounds(576, 262, 197, 75);
        lblAttendanceRate.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblAttendanceRate.setForeground(Color.decode("#f16c56"));
        add(lblAttendanceRate);

        lblProgressPercent = new JLabel("0");
        lblProgressPercent.setBounds(1079, 262, 297, 75);
        lblProgressPercent.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblProgressPercent.setForeground(Color.decode("#16a55d"));
        add(lblProgressPercent);

        // STATS LABELS
        lblTotalStud = new JLabel("TOTAL STUDENTS");
        lblTotalStud.setBounds(69, 227, 183, 27);
        lblTotalStud.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTotalStud.setForeground(Color.decode("#737373"));
        add(lblTotalStud);

        lblAttRate = new JLabel("ATTENDANCE RATE");
        lblAttRate.setBounds(576, 227, 183, 27);
        lblAttRate.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblAttRate.setForeground(Color.decode("#737373"));
        add(lblAttRate);

        lblTaskProg = new JLabel("TASK PROGRESS");
        lblTaskProg.setBounds(1082, 227, 183, 27);
        lblTaskProg.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTaskProg.setForeground(Color.decode("#737373"));
        add(lblTaskProg);

        // CARD PANELS
        pnlStud = new JPanel();
        pnlStud.setBounds(43, 203, 464, 168);
        pnlStud.setBackground(Color.WHITE);
        pnlStud.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlStud);

        pnlAtt = new JPanel();
        pnlAtt.setBounds(547, 203, 464, 168);
        pnlAtt.setBackground(Color.WHITE);
        pnlAtt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlAtt);

        pnlTask = new JPanel();
        pnlTask.setBounds(1052, 203, 464, 168);
        pnlTask.setBackground(Color.WHITE);
        pnlTask.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(pnlTask);

        // TASK PANEL
        setupTaskPanel();

        // SCHEDULE PANEL
        setupSchedulePanel();

        // DECORATIVE IMAGE
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);

        // LOAD DATA
        loadTasksFromDB();
        loadScheduleFromDB();
        refreshStats();
    }

    // TASK PANEL SETUP
    private void setupTaskPanel() {
        taskPanel = new JPanel();
        taskPanel.setLayout(null);
        taskPanel.setBounds(45, 410, 560, 490);
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(taskPanel);

        // TITLE
        JLabel titleLabel = new JLabel("MY TASKS:");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        titleLabel.setForeground(Color.decode("#737373"));
        titleLabel.setBounds(25, 15, 200, 25);
        taskPanel.add(titleLabel);

        // BUTTONS
        btnAdd = new JButton("Add");
        btnAdd.setBounds(25, 60, 100, 40);
        btnAdd.setBackground(new Color(26, 115, 232));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(this);
        taskPanel.add(btnAdd);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(25, 115, 100, 40);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(this);
        taskPanel.add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setBounds(25, 170, 100, 40);
        btnClear.setBackground(new Color(255, 165, 0));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(this);
        taskPanel.add(btnClear);

        // INPUT FIELD
        tfTaskInput = new JTextField();
        tfTaskInput.setBounds(140, 60, 390, 40);
        tfTaskInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfTaskInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        taskPanel.add(tfTaskInput);

        // TASK LIST
        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        taskList.setSelectionBackground(new Color(26, 115, 232));
        taskList.setSelectionForeground(Color.WHITE);

        // SCROLL PANE
        taskScrollPane = new JScrollPane(taskList);
        taskScrollPane.setBounds(140, 115, 390, 350);
        taskScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        taskPanel.add(taskScrollPane);
    }

    // SCHEDULE PANEL SETUP
    private void setupSchedulePanel() {
        schedulePanel = new JPanel();
        schedulePanel.setLayout(null);
        schedulePanel.setBounds(630, 410, 860, 490);
        schedulePanel.setBackground(Color.WHITE);
        schedulePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(schedulePanel);

        // TITLE
        JLabel titleLabel = new JLabel("SCHEDULE:");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        titleLabel.setForeground(Color.decode("#737373"));
        titleLabel.setBounds(25, 15, 200, 25);
        schedulePanel.add(titleLabel);

        // BUTTONS
        btnSchedAdd = new JButton("Add");
        btnSchedAdd.setBounds(25, 60, 100, 40);
        btnSchedAdd.setBackground(new Color(26, 115, 232));
        btnSchedAdd.setForeground(Color.WHITE);
        btnSchedAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSchedAdd.setFocusPainted(false);
        btnSchedAdd.addActionListener(this);
        schedulePanel.add(btnSchedAdd);

        btnSchedDelete = new JButton("Delete");
        btnSchedDelete.setBounds(25, 115, 100, 40);
        btnSchedDelete.setBackground(Color.RED);
        btnSchedDelete.setForeground(Color.WHITE);
        btnSchedDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSchedDelete.setFocusPainted(false);
        btnSchedDelete.addActionListener(this);
        schedulePanel.add(btnSchedDelete);

        btnSchedSave = new JButton("Save");
        btnSchedSave.setBounds(25, 170, 100, 40);
        btnSchedSave.setBackground(new Color(22, 163, 74));
        btnSchedSave.setForeground(Color.WHITE);
        btnSchedSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSchedSave.setFocusPainted(false);
        btnSchedSave.addActionListener(this);
        schedulePanel.add(btnSchedSave);

        // TABLE COLUMNS
        String[] cols = {"Time", "Subject", "Section", "Room"};
        scheduleModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return true;
            }
        };

        scheduleTable = new JTable(scheduleModel);
        scheduleTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        scheduleTable.setRowHeight(38);
        scheduleTable.setSelectionBackground(new Color(26, 115, 232));
        scheduleTable.setSelectionForeground(Color.WHITE);
        scheduleTable.setGridColor(Color.decode("#e0e0e0"));

        // TABLE HEADER
        JTableHeader header = scheduleTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        // SCROLL PANE
        scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setBounds(140, 60, 695, 405);
        scheduleScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        schedulePanel.add(scheduleScrollPane);
    }

    // LOAD TASKS FROM DATABASE
    private void loadTasksFromDB() {
        listModel.clear();
        taskIds.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT task_id, task_name FROM tasks WHERE is_completed = FALSE ORDER BY created_at DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                taskIds.add(String.valueOf(rs.getInt("task_id")));
                listModel.addElement(rs.getString("task_name"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        updateProgress();
    }

    // ADD TASK TO DATABASE
    private void addTaskToDB(String task) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (task_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    taskIds.add(String.valueOf(rs.getInt(1)));
                    listModel.addElement(task);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        updateProgress();
    }

    // DELETE TASK FROM DATABASE
    private void deleteTaskFromDB(int index) {
        if (index < 0 || index >= taskIds.size()) {
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             PreparedStatement ps = conn.prepareStatement("DELETE FROM tasks WHERE task_id = ?")) {

            ps.setInt(1, Integer.parseInt(taskIds.get(index)));
            ps.executeUpdate();

            taskIds.remove(index);
            listModel.removeElementAt(index);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        updateProgress();
    }

    // CLEAR ALL TASKS
    private void clearAllTasksFromDB() {
        if (taskIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to clear.", "Empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all tasks?", "Confirm Clear", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate("DELETE FROM tasks");
                taskIds.clear();
                listModel.clear();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        updateProgress();
    }

    // LOAD SCHEDULE FROM DATABASE
    private void loadScheduleFromDB() {
        scheduleModel.setRowCount(0);

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms_db", "root", "");

            String sql = "SELECT time_slot, subject, section, room FROM schedule ORDER BY schedule_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String timeSlot = rs.getString("time_slot");
                String subject = rs.getString("subject");
                String section = rs.getString("section");
                String room = rs.getString("room");
                scheduleModel.addRow(new Object[]{timeSlot, subject, section, room});
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // SAVE SCHEDULE TO DATABASE
    private void saveScheduleToDB() {
        if (scheduleTable.isEditing()) {
            scheduleTable.getCellEditor().stopCellEditing();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "");
             Statement stmt = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (time_slot, subject, section, room) VALUES (?, ?, ?, ?)")) {

            // CLEAR EXISTING
            stmt.executeUpdate("DELETE FROM schedule");

            // INSERT CURRENT
            for (int i = 0; i < scheduleModel.getRowCount(); i++) {
                ps.setString(1, String.valueOf(scheduleModel.getValueAt(i, 0)));
                ps.setString(2, String.valueOf(scheduleModel.getValueAt(i, 1)));
                ps.setString(3, String.valueOf(scheduleModel.getValueAt(i, 2)));
                ps.setString(4, String.valueOf(scheduleModel.getValueAt(i, 3)));
                ps.addBatch();
            }

            ps.executeBatch();
            JOptionPane.showMessageDialog(this, "Schedule saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving schedule: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REFRESH STATISTICS
    public void refreshStats() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db", "root", "")) {

            // TOTAL STUDENTS
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM students")) {
                if (rs.next()) {
                    lblNoStuds.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // ATTENDANCE RATE
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total, SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present FROM attendance")) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    int present = rs.getInt("present");
                    lblAttendanceRate.setText(total > 0 ? (present * 100 / total) + "%" : "0%");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            lblNoStuds.setText("0");
            lblAttendanceRate.setText("0%");
        }
    }

    // UPDATE TASK PROGRESS
    private void updateProgress() {
        int total = listModel.getSize();
        lblProgressPercent.setText(total == 0 ? "0" : total + " pending");
    }

    // BUTTON ACTIONS
    @Override
    public void actionPerformed(ActionEvent e) {

        // ADD TASK
        if (e.getSource() == btnAdd) {
            String task = tfTaskInput.getText().trim();
            if (!task.isEmpty()) {
                addTaskToDB(task);
                tfTaskInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task.", "No Input", JOptionPane.ERROR_MESSAGE);
            }

        // DELETE TASK
        } else if (e.getSource() == btnDelete) {
            int idx = taskList.getSelectedIndex();
            if (idx != -1) {
                deleteTaskFromDB(idx);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }

        // CLEAR ALL TASKS
        } else if (e.getSource() == btnClear) {
            clearAllTasksFromDB();

        // ADD SCHEDULE ROW
        } else if (e.getSource() == btnSchedAdd) {
            scheduleModel.addRow(new Object[]{"e.g. 8:00 AM - 10:00 AM", "Subject", "Section", "Room"});
            int newRow = scheduleModel.getRowCount() - 1;
            scheduleTable.scrollRectToVisible(scheduleTable.getCellRect(newRow, 0, true));
            scheduleTable.editCellAt(newRow, 0);

        // DELETE SCHEDULE ROW
        } else if (e.getSource() == btnSchedDelete) {
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow != -1) {
                scheduleModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }

        // SAVE SCHEDULE
        } else if (e.getSource() == btnSchedSave) {
            saveScheduleToDB();
        }
    }
}