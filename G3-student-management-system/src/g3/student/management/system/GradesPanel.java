package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradesPanel extends JPanel {
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private JButton btnAddGrade;
    private ImageIcon imgDashOne;
    
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
        
        btnAddGrade.addActionListener(new ActionListener(){
            
            private JLabel lblAddStud, lblSubtitle, lblName, lblSubj, lblGrade;
            private JTextField tfFName, tfGrade; 
            private JComboBox cmbSubj;
            private JButton btnAdd;
            
            @Override
            public void actionPerformed (ActionEvent e) {
                
                JFrame frmAddStud = new JFrame();
                frmAddStud.setSize(904, 525);
                frmAddStud.setLayout(null);
                frmAddStud.setLocationRelativeTo(null);
                frmAddStud.setTitle("ADD NEW STUDENT");
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
                lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblName.setBounds(38, 124, 102, 22);
                frmAddStud.add(lblName);
                
                    tfFName = new JTextField();
                    tfFName.setBounds(35, 164, 842, 53);
                    frmAddStud.add(tfFName);
                   
                
                lblSubj = new JLabel ("Course/Subject");
                lblSubj.setForeground(Color.BLACK);
                lblSubj.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblSubj.setBounds(43, 231, 157, 22);
                frmAddStud.add(lblSubj);
                
                    String [] optProg = {"English", "Math", "Programing"};
                    cmbSubj = new JComboBox<>(optProg);
                    cmbSubj.setBounds(35, 259, 842, 53);
                    frmAddStud.add(cmbSubj);
                
   
                    
                lblGrade = new JLabel ("Grade");
                lblGrade.setForeground(Color.BLACK);
                lblGrade.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblGrade.setBounds(38, 315, 102, 22);
                frmAddStud.add(lblGrade);
                
                    tfGrade = new JTextField ();
                    tfGrade.setBounds(31, 343, 843, 53);
                    frmAddStud.add(tfGrade);
                    
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
                
            }
        });
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
  
    }
    
}
