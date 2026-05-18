package g3.student.management.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private ImageIcon imgDashOne;
    private TodoPanel todoPanel; // Idinagdag na variable para sa To-Do List
    
    public DashboardPanel() {
        
        setLayout(null);
        setBackground(new Color(244, 245, 247)); // Bagay na background para lumitaw ang white cards
        
        // ContentPanel reference sizes: bounds(350, 0, 1570, 1084)
        
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
        
        // --- DITO INILAGAY ANG TO-DO LIST (MY TASKS) PANEL ---
        todoPanel = new TodoPanel();
        // In-adjust ang bounds para saktong pumwesto sa kaliwang bahagi, katapat ng image sa kanan
        todoPanel.setBounds(45, 410, 560, 490); 
        add(todoPanel);
        // -----------------------------------------------------
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
    }

    // Inner Class para sa Todo Panel para malinis ang pagka-arkitekto ng code mo
    private class TodoPanel extends JPanel {
        private JPanel taskListContainer;
        private JTextField taskInputField;
        private JButton addButton;

        private final Color BLUE_THEME = new Color(26, 115, 232);
        private final Color TEXT_GRAY = new Color(115, 115, 115);
        private final Color PANEL_BG = Color.WHITE;

        public TodoPanel() {
            this.setBackground(PANEL_BG);
            this.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
            this.setLayout(new BorderLayout(10, 10));

            // 1. Header (Icon at Title)
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
            headerPanel.setBackground(PANEL_BG);
            
            JLabel titleLabel = new JLabel("MY TASKS:");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titleLabel.setForeground(TEXT_GRAY);
            headerPanel.add(titleLabel);
            this.add(headerPanel, BorderLayout.NORTH);

            // 2. Middle (Scrollable Task List)
            taskListContainer = new JPanel();
            taskListContainer.setBackground(PANEL_BG);
            taskListContainer.setLayout(new GridBagLayout()); 
            
            JScrollPane scrollPane = new JScrollPane(taskListContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            this.add(scrollPane, BorderLayout.CENTER);

            // 3. Bottom (Input text field at Add [ + ] button)
            JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
            inputPanel.setBackground(PANEL_BG);
            inputPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

            taskInputField = new JTextField();
            taskInputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            taskInputField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 214, 219), 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            
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

            addButton = new JButton("+");
            addButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
            addButton.setBackground(BLUE_THEME);
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            addButton.setBorder(BorderFactory.createEmptyBorder(5, 18, 5, 18));

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

            inputPanel.add(taskInputField, BorderLayout.CENTER);
            inputPanel.add(addButton, BorderLayout.EAST);
            this.add(inputPanel, BorderLayout.SOUTH);

            // Default Tasks base sa Canva Design
            addTaskRow("Create a PPT presentation for History subject");
            addTaskRow("Add grades to students in English class");
        }

        private void addTaskRow(String taskText) {
            JPanel rowPanel = new JPanel(new BorderLayout(5, 0));
            rowPanel.setBackground(PANEL_BG);
            rowPanel.setBorder(new EmptyBorder(6, 15, 6, 15));

            JCheckBox checkBox = new JCheckBox(taskText);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            checkBox.setBackground(PANEL_BG);
            checkBox.setFocusPainted(false);

            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    checkBox.setForeground(Color.LIGHT_GRAY);
                } else {
                    checkBox.setForeground(Color.BLACK);
                }
            });

            JButton deleteButton = new JButton("✕");
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            deleteButton.setForeground(Color.RED);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setFocusPainted(false);
            deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            deleteButton.addActionListener(e -> {
                taskListContainer.remove(rowPanel);
                revalidateLayout();
            });

            rowPanel.add(checkBox, BorderLayout.CENTER);
            rowPanel.add(deleteButton, BorderLayout.EAST);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = taskListContainer.getComponentCount();
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTHWEST;

            taskListContainer.add(rowPanel, gbc);
            revalidateLayout();
        }

        private void revalidateLayout() {
            taskListContainer.revalidate();
            taskListContainer.repaint();
        }
    }
}
