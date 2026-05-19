package g3.student.management.system;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel{
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private ImageIcon imgDashOne;
    
    DashboardPanel(){
        
        setLayout(null);
        
        //contentPanel.setBounds(350, 0, 1570, 1084);
        
        lblTitle = new JLabel("Dashboard");
        lblTitle.setBounds(45, 87, 230, 47);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(lblTitle);
        
        lblSubtxt = new JLabel ("Overview"); 
        lblSubtxt.setBounds(45, 137, 104, 27);
        lblSubtxt.setForeground(Color.decode("#737373"));
        lblSubtxt.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        add(lblSubtxt);
        
        imgDashOne = new ImageIcon("images/hp-dash-one-v2.png");
        
        imgDisplay = new JLabel(imgDashOne);
        imgDisplay.setBounds(1153, 589, 417, 491);
        add(imgDisplay);
        
        
        
        
    }
    
    
}
