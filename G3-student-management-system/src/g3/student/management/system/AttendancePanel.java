
package g3.student.management.system;

import javax.swing.*;
import java.awt.*;

public class AttendancePanel extends JPanel {
    
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private JButton btnAddAtt;
    private ImageIcon imgDashOne;
    
    AttendancePanel(){
        
        setLayout(null);
        
        //contentPanel.setBounds(350, 0, 1570, 1084);
        
        lblTitle = new JLabel("Attendance");
        lblTitle.setBounds(45, 87, 244, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel ("Track daily student attendance"); 
        lblSubtxt.setBounds(45, 137, 354, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        btnAddAtt = new JButton ("Add attendance");
        btnAddAtt.setBounds(1273, 104, 230, 61);
        btnAddAtt.setBackground(Color.decode("#1f87e2"));
        btnAddAtt.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAddAtt.setForeground(Color.WHITE);
        add(btnAddAtt);
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
  
    }
    
}
