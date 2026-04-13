package g3.student.management.system;

import javax.swing.*;
import java.awt.*;

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
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
  
    }
    
}
