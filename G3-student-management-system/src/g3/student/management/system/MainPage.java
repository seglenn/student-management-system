package g3.student.management.system;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame{

        MainPage(){
            
            setLayout(null);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Student Management System -  Group 3");
            
            JPanel mainPanel = new JPanel ();
            mainPanel.setBackground(Color.decode("#f6f7f9"));
            // Screen size: 1920 x 1080
            
            JLabel lblTitle  = new JLabel("Student Management System");
            lblTitle.setBounds(100, 5, 547, 55);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
            lblTitle.setForeground(Color.decode("#f6f7f9"));
            add(lblTitle);
            
            JPanel navPanelH = new JPanel();
            navPanelH.setBounds(0, 0, 1920, 70);
            navPanelH.setBackground(Color.decode("#202c3c"));
            add(navPanelH);
            
            JPanel sideBarPanel = new JPanel();
            sideBarPanel.setBounds(12, 84, 328, 1060);
            sideBarPanel.setBackground(Color.decode("#202c3c"));
            add(sideBarPanel);
            
            JPanel dbPanel = new JPanel();
            dbPanel.setBounds(368, 84, 1529, 1044);
            dbPanel.setBackground(Color.decode("#ffffff"));
            add(dbPanel);
            
                JButton studButton = new JButton ("Students");
                studButton.setFont(new Font ("Segoe UI", Font.PLAIN, 30));
                studButton.setForeground(Color.decode("#b4b4b4"));
                studButton.setBounds(36, 124, 277, 61);
                studButton.setBackground(Color.decode("#1f87e2"));
                studButton.setFocusPainted(false);
                //studButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                add(studButton);


            
            
            
        }  

    
    
}
