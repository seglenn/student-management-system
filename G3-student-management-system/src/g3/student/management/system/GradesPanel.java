package g3.student.management.system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradesPanel extends JPanel {
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private JButton btnAddGrade;
    private ImageIcon imgDashOne;
    private JTable gradeTable;
    private DefaultTableModel tableModel; 
    private JScrollPane scrollPane;
    
    GradesPanel(){
        setLayout(null);
        
        //contentPanel.setBounds(350, 0, 1570, 1084);
        
        lblTitle = new JLabel("Grades");
        lblTitle.setBounds(45, 87, 149, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel ("Track students grade records"); 
        lblSubtxt.setBounds(45, 137, 321, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        btnAddGrade = new JButton ("Add grade");
        btnAddGrade.setBounds(1273, 104, 230, 61);
        btnAddGrade.setBackground(Color.decode("#1f87e2"));
        btnAddGrade.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddGrade.setForeground(Color.WHITE);
        add(btnAddGrade);
        
        String[] columns = {"Date", "Student Name", "Course/Subject", "Grade",  "Remarks",};
        tableModel = new DefaultTableModel(columns, 0);
        gradeTable = new JTable(tableModel);
        
        // Style the table
        gradeTable.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        gradeTable.setRowHeight(40);
        gradeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        gradeTable.getTableHeader().setBackground(Color.decode("#1f87e2"));
        gradeTable.getTableHeader().setForeground(Color.WHITE);
        gradeTable.setSelectionBackground(Color.decode("#e3f2fd"));
        gradeTable.setGridColor(Color.decode("#e0e0e0"));
        
        JTableHeader header = gradeTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));
        
        scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane); 
        
        btnAddGrade.addActionListener(new ActionListener(){
            
            private JLabel lblAddStud, lblSubtitle, lblName, lblSubj, lblGrade;
            private JTextField tfName, tfGrade; 
            private JComboBox cmbSubj;
            private JButton btnAdd;
            
            @Override
            public void actionPerformed (ActionEvent e) {
                
                JFrame frmAddStud = new JFrame();
                frmAddStud.setSize(904, 525);
                frmAddStud.setLayout(null);
                frmAddStud.setLocationRelativeTo(null);
                frmAddStud.setTitle("ADD NEW GRADE");
                frmAddStud.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                lblAddStud = new JLabel("Add new grade");
                lblAddStud.setForeground(Color.BLACK);
                lblAddStud.setFont(new Font("Segoe UI", Font.BOLD, 25));
                lblAddStud.setBounds(35, 36, 357, 39);
                frmAddStud.add(lblAddStud);
                
                lblSubtitle = new JLabel("Fill in the required details below");
                lblSubtitle.setForeground(Color.decode("#737373"));
                lblSubtitle.setFont(new Font ("Segoe UI", Font.PLAIN, 17));
                lblSubtitle.setBounds(35, 70, 372, 27);
                frmAddStud.add(lblSubtitle);
                
                lblName = new JLabel ("Student Name");
                lblName.setForeground(Color.BLACK);
                lblName.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblName.setBounds(32, 124, 152, 22);
                frmAddStud.add(lblName);
                
                    tfName = new JTextField();
                    tfName.setBounds(32, 155, 842, 53);
                    tfName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(tfName);
                   
                
                lblSubj = new JLabel ("Course/Subject");
                lblSubj.setForeground(Color.BLACK);
                lblSubj.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblSubj.setBounds(32, 221, 157, 22);
                frmAddStud.add(lblSubj);
                
                    String [] optProg = {"English", "Math", "Programing"};
                    cmbSubj = new JComboBox<>(optProg);
                    cmbSubj.setBounds(32, 250, 842, 53);
                    cmbSubj.setFont (new Font("Segoe UI", Font.BOLD, 20));
                    frmAddStud.add(cmbSubj); 
                    
                lblGrade = new JLabel ("Grade");
                lblGrade.setForeground(Color.BLACK);
                lblGrade.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblGrade.setBounds(32, 315, 102, 22);
                frmAddStud.add(lblGrade);
                
                    tfGrade = new JTextField ();
                    tfGrade.setBounds(32, 343, 843, 53);
                    tfGrade.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(tfGrade);
                    
                btnAdd = new JButton ("Add");
                btnAdd.setBounds(684, 419, 190, 53);
                btnAdd.setForeground(Color.WHITE);
                btnAdd.setBackground(Color.decode("#1f89e5"));
                btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 25));
                btnAdd.setFocusPainted(false);
                btnAdd.setBorderPainted(false);
                frmAddStud.add(btnAdd);
                
                    btnAdd.addActionListener (new ActionListener(){
                        
                        @Override
                        public void actionPerformed (ActionEvent e){
                            
                            
                            
                        };
                    });
                
                    
                frmAddStud.setVisible(true);
                
            }
        });
        
        
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
  
    }
    
    
}


