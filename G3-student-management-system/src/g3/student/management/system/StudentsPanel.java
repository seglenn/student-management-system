package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentsPanel extends JPanel{
    
    JLabel lblTitle, lblSubtxt, imgDisplay;
    JButton btnAddStud;
    ImageIcon imgDashOne;
    
    StudentsPanel(){
        
        setLayout(null);
        
        //contentPanel.setBounds(350, 0, 1570, 1084);
        
        lblTitle = new JLabel("Students");
        lblTitle.setBounds(45, 87, 185, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel ("Manage enrolled students"); 
        lblSubtxt.setBounds(45, 137, 297, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        btnAddStud = new JButton ("Add student");
        btnAddStud.setBounds(1273, 104, 230, 61);
        btnAddStud.setBackground(Color.decode("#1f87e2"));
        btnAddStud.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddStud.setForeground(Color.WHITE);
        add(btnAddStud);
        
        btnAddStud.addActionListener(new ActionListener(){
            
            private JLabel lblAddStud, lblSubtitle, lblFirstName, lblLastName, lblProgram, lblYear, lblEmail;
            private JTextField tfFName, tfLName, tfEmail; 
            private JComboBox cmbProg, cmbYear;
            private JButton btnAdd;
            
            @Override
            public void actionPerformed(ActionEvent e){
                
                JFrame frmAddStud = new JFrame();
                frmAddStud.setSize(904, 535);
                frmAddStud.setLayout(null);
                frmAddStud.setLocationRelativeTo(null);
                frmAddStud.setTitle("ADD NEW STUDENT");
                frmAddStud.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                lblAddStud = new JLabel("Add new student");
                lblAddStud.setForeground(Color.BLACK);
                lblAddStud.setFont(new Font("Segoe UI", Font.BOLD, 25));
                lblAddStud.setBounds(35, 36, 357, 39);
                frmAddStud.add(lblAddStud);
                
                lblSubtitle = new JLabel("Fill in the required details below");
                lblSubtitle.setForeground(Color.decode("#737373"));
                lblSubtitle.setFont(new Font ("Segoe UI", Font.PLAIN, 17));
                lblSubtitle.setBounds(35, 70, 372, 27);
                frmAddStud.add(lblSubtitle);
                
                lblFirstName = new JLabel ("First Name");
                lblFirstName.setForeground(Color.BLACK);
                lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblFirstName.setBounds(38, 124, 102, 22);
                frmAddStud.add(lblFirstName);
                
                    tfFName = new JTextField();
                    tfFName.setBounds(31, 152, 353, 53);
                    frmAddStud.add(tfFName);
                    
                lblLastName = new JLabel ("Last Name");
                lblLastName.setForeground(Color.BLACK);
                lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblLastName.setBounds(447, 124, 102, 22);
                frmAddStud.add(lblLastName);
                
                    tfLName = new JTextField ();
                    tfLName.setBounds(440, 152, 434, 53);
                    frmAddStud.add(tfLName);
                
                lblProgram = new JLabel ("Program");
                lblProgram.setForeground(Color.BLACK);
                lblProgram.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblProgram.setBounds(38, 219, 102, 22);
                frmAddStud.add(lblProgram);
                
                    String [] optProg = {"BSIT", "CPE", "PSYCH"};
                    cmbProg = new JComboBox<>(optProg);
                    cmbProg.setBounds(31, 247, 673, 53);
                    frmAddStud.add(cmbProg);
                
                lblYear = new JLabel ("Year");
                lblYear.setForeground(Color.BLACK);
                lblYear.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblYear.setBounds(728, 219, 102, 22);
                frmAddStud.add(lblYear);
                
                    String [] optYear = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
                    cmbYear = new JComboBox<>(optYear);
                    cmbYear.setBounds(720, 247, 153, 53);
                    frmAddStud.add(cmbYear);
                    
                lblEmail = new JLabel ("Email");
                lblEmail.setForeground(Color.BLACK);
                lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblEmail.setBounds(38, 315, 102, 22);
                frmAddStud.add(lblEmail);
                
                    tfEmail = new JTextField ();
                    tfEmail.setBounds(31, 343, 843, 53);
                    frmAddStud.add(tfEmail);
                    
                btnAdd = new JButton ("Add");
                btnAdd.setBounds(684, 429, 190, 53);
                btnAdd.setForeground(Color.WHITE);
                btnAdd.setBackground(Color.decode("#1f89e5"));
                btnAdd.setFocusPainted(false);
                btnAdd.setBorderPainted(false);
                frmAddStud.add(btnAdd);
                
                    btnAdd.addActionListener (new ActionListener(){
                        
                        @Override
                        public void actionPerformed (ActionEvent e){
                            
                            
                            
                        };
                    });
                
                    
                frmAddStud.setVisible(true);
                
            };
            
        });
        
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
  
    }
    
}
