package g3.student.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;

public class DashboardPanel extends JPanel {
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private ImageIcon imgDashOne;
    private TodoPanel todoPanel; 
    private JLabel lblProgressPercent, lblTotalStud, lblAttRate, lblTaskProg, lblAttendanceRate, lblNoStuds, lblSched; 
    private JPanel pnlStud, pnlAtt, pnlTask, pnlSched;
    
    public DashboardPanel() {
        
        setLayout(null);
        setBackground(new Color(244, 245, 247)); 
        
        lblTitle = new JLabel("Dashboard");
        lblTitle.setBounds(45, 87, 230, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel("Overview"); 
        lblSubtxt.setBounds(45, 137, 104, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        lblNoStuds = new JLabel("0"); 
        lblNoStuds.setBounds(67, 262, 197, 75); 
        lblNoStuds.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblNoStuds.setForeground(Color.decode("#000000")); 
        add(lblNoStuds);

        lblProgressPercent = new JLabel("0%"); 
        lblProgressPercent.setBounds(1079, 262, 197, 75); 
        lblProgressPercent.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblProgressPercent.setForeground(Color.decode("#16a55d")); 
        add(lblProgressPercent);
        
        lblAttendanceRate = new JLabel("0%"); 
        lblAttendanceRate.setBounds(576, 262, 197, 75); 
        lblAttendanceRate.setFont(new Font("Segoe UI", Font.BOLD, 53));
        lblAttendanceRate.setForeground(Color.decode("#f16c56")); 
        add(lblAttendanceRate);
        
        todoPanel = new TodoPanel();
        todoPanel.setBounds(45, 410, 560, 490); 
        add(todoPanel);
        
        
        
        lblTotalStud = new JLabel ("TOTAL STUDENTS");
        lblTotalStud.setBounds (69, 227, 183, 27);
        lblTotalStud.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTotalStud.setForeground (Color.decode("#737373")); 
        add(lblTotalStud);
        
            pnlStud = new JPanel ();
            pnlStud.setBounds(43, 203, 464, 168);
            pnlStud.setBackground(Color.decode("#ffffff"));
            pnlStud.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(pnlStud);
        
        lblAttRate = new JLabel ("ATTENDANCE RATE");
        lblAttRate.setBounds (576, 227, 183, 27);
        lblAttRate.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblAttRate.setForeground (Color.decode("#737373")); 
        add(lblAttRate);
        
            pnlAtt = new JPanel ();
            pnlAtt.setBounds(547, 203, 464, 168);
            pnlAtt.setBackground(Color.decode("#ffffff"));
            pnlAtt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(pnlAtt);
        
        lblTaskProg = new JLabel ("TASK PROGRESS");
        lblTaskProg.setBounds (1082, 227, 183, 27);
        lblTaskProg.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblTaskProg.setForeground (Color.decode("#737373")); 
        add(lblTaskProg);
        
            pnlTask = new JPanel ();
            pnlTask.setBounds(1052, 203, 464, 168);
            pnlTask.setBackground(Color.decode("#ffffff"));
            pnlTask.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(pnlTask);
            
        lblSched = new JLabel ("SCHEDULE");
        lblSched.setBounds (700, 441, 116, 27);
        lblSched.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblSched.setForeground (Color.decode("#737373")); 
        add(lblSched);
        
            pnlSched = new JPanel ();
            pnlSched.setBounds(653, 410, 865, 478);
            pnlSched.setBackground(Color.decode("#ffffff"));
            pnlSched.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(pnlSched);
            
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
        

        updateProgress(); 
        
        
    }

    public void updateProgress() {
        if (todoPanel == null || todoPanel.taskListContainer == null) return;
        
        int totalTasks = 0;
        int completedTasks = 0;
        
        java.awt.Component[] components = todoPanel.taskListContainer.getComponents();
        for (java.awt.Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel row = (JPanel) comp;
                for (java.awt.Component child : row.getComponents()) {
                    if (child instanceof JCheckBox) {
                        totalTasks++;
                        if (((JCheckBox) child).isSelected()) {
                            completedTasks++;
                        }
                    }
                }
            }
        }
        
        int percentage = (totalTasks == 0) ? 0 : (int) (((double) completedTasks / totalTasks) * 100);
        lblProgressPercent.setText(percentage + "%");
    }

    private class TodoPanel extends JPanel {
        private JPanel taskListContainer;
        private JTextField taskInputField;
        private JButton addButton;
        private JLabel titleLabel;

        private final Color BLUE_THEME = new Color(26, 115, 232);
        private final Color TEXT_GRAY = new Color(115, 115, 115);
        private final Color PANEL_BG = Color.decode("#ffffff");

        public TodoPanel() {
            this.setBackground(PANEL_BG);
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            this.setLayout(null);

            JLabel titleLabel = new JLabel("MY TASKS:");
            titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
            titleLabel.setForeground (Color.decode("#737373")); 
            titleLabel.setBounds(25, 15, 200, 25);
            this.add(titleLabel);
            
            

            taskInputField = new JTextField();
            taskInputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            taskInputField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            taskInputField.setBounds(15, 50, 440, 40);
            
            taskInputField.setText("Add new task here...");
            taskInputField.setForeground(Color.LIGHT_GRAY);
            taskInputField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (taskInputField.getText().equals("Add new task here...")) {
                        taskInputField.setText("");
                        taskInputField.setForeground(Color.BLACK);
                    }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (taskInputField.getText().isEmpty()) {
                        taskInputField.setText("Add new task here...");
                        taskInputField.setForeground(Color.LIGHT_GRAY);
                    }
                }
            });
            this.add(taskInputField);

            addButton = new JButton("+");
            addButton.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            addButton.setBackground(BLUE_THEME);
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            addButton.setBounds(465, 50, 80, 40);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String taskText = taskInputField.getText().trim();
                    if (!taskText.isEmpty() && !taskText.equals("Add new task here...")) {
                        addTaskRow(taskText);
                        taskInputField.setText("");
                        taskInputField.requestFocus();
                    }
                }
            });
            this.add(addButton);

            taskListContainer = new JPanel();
            taskListContainer.setBackground(PANEL_BG);
            taskListContainer.setLayout(null); 
            
            JScrollPane scrollPane = new JScrollPane(taskListContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setBounds(15, 105, 530, 370);
            this.add(scrollPane);

            
            
            java.awt.Component[] comps = taskListContainer.getComponents();
            if (comps.length > 0 && comps[1] instanceof JPanel) {
                JPanel firstRow = (JPanel) comps[1];
                for (java.awt.Component child : firstRow.getComponents()) {
                    if (child instanceof JCheckBox) {
                        JCheckBox cb = (JCheckBox) child;
                        cb.setSelected(true);
                        cb.setForeground(Color.LIGHT_GRAY);
                        Font baseFont = cb.getFont();
                        Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
                        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                        cb.setFont(new Font(attributes));
                    }
                }
            }
        }

        private void addTaskRow(String taskText) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(null);
            rowPanel.setBackground(PANEL_BG);
            rowPanel.setSize(510, 40);

            JCheckBox checkBox = new JCheckBox(taskText);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            checkBox.setBackground(PANEL_BG);
            checkBox.setFocusPainted(false);
            checkBox.setIconTextGap(50);
            checkBox.setBounds(20, 0, 370, 40);

            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    checkBox.setForeground(Color.LIGHT_GRAY);
                    Font baseFont = checkBox.getFont();
                    Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
                    attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                    checkBox.setFont(new Font(attributes));
                } else {
                    checkBox.setForeground(Color.BLACK);
                    Font baseFont = checkBox.getFont();
                    Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
                    attributes.put(TextAttribute.STRIKETHROUGH, false);
                    checkBox.setFont(new Font(attributes));
                }
                updateProgress(); 
            });

            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBackground(Color.RED);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setBounds(400, 0, 100, 40);

            deleteButton.addActionListener(e -> {
                taskListContainer.remove(rowPanel);
                rearrangeTasks();
                revalidateLayout();
                updateProgress(); 
            });

            rowPanel.add(checkBox);
            rowPanel.add(deleteButton);

            java.awt.Component[] components = taskListContainer.getComponents();
            for (java.awt.Component comp : components) {
                comp.setLocation(comp.getX(), comp.getY() + 40);
            }

            rowPanel.setLocation(0, 0);
            taskListContainer.add(rowPanel);
            
            taskListContainer.setPreferredSize(new Dimension(510, (components.length + 1) * 40));
            
            revalidateLayout();
            updateProgress(); 
        }

        private void rearrangeTasks() {
            java.awt.Component[] components = taskListContainer.getComponents();
            int y = 0;
            for (int i = components.length - 1; i >= 0; i--) {
                components[i].setLocation(0, y);
                y += 40;
            }
            taskListContainer.setPreferredSize(new Dimension(510, components.length * 40));
        }

        private void revalidateLayout() {
            taskListContainer.revalidate();
            taskListContainer.repaint();
        }
        
        
        
        
    }
}


