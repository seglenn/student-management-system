package g3.student.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame{

    HomePage(){
        
        setTitle("Student Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        ImageIcon imgSchool = new ImageIcon("images/school-bg.png");
        ImageIcon imgGradient = new ImageIcon ("images/hp-gradient.png");
        ImageIcon imgGradientTwo = new ImageIcon ("images/hp-gradient-two.png");
        ImageIcon imgDashOne = new ImageIcon ("images/hp-dash-one.png");
        ImageIcon imgDashTwo = new ImageIcon ("images/hp-dash-two.png");
        ImageIcon imgPaper = new ImageIcon ("images/hp-paper.png");
        
        JLabel imgDashDown = new JLabel (imgDashOne);
        imgDashDown.setBounds(885, 594, 419, 491);
        add(imgDashDown);
        
        JLabel imgDashUp = new JLabel (imgDashTwo);
        imgDashUp.setBounds(1626, -75, 461, 424);
        add(imgDashUp);
        
        JLabel imgBg = new JLabel(imgSchool);
        imgBg.setBounds(0, 0, 886, 1085);
        add(imgBg);
        
        JLabel imgGradOne = new JLabel(imgGradient);
        imgGradOne.setBounds(912, 524, 1196, 972);
        add(imgGradOne);
        
        JLabel imgPpr = new JLabel (imgPaper);
        imgPpr.setBounds(881, 0, 206, 234);
        add(imgPpr);
        
        JLabel imgGradTwo = new JLabel (imgGradientTwo);
        imgGradTwo.setBounds(157, -741, 1194, 966);
        add(imgGradTwo);
        
        JLabel lblWelcome = new JLabel ("Welcome!");
        lblWelcome.setBounds(1240, 312, 141, 32);
        lblWelcome.setForeground(Color.black);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblWelcome);
        
        JLabel lblSignIn = new JLabel ("Sign In to access the system");
        lblSignIn.setBounds(1240, 347, 262, 22);
        lblSignIn.setForeground(Color.decode("#737373"));
        lblSignIn.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblSignIn);
        
        
        JLabel lblUsername = new JLabel ("Username");
        lblUsername.setBounds(1248, 413, 95, 22);
        lblUsername.setForeground(Color.black);
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblUsername);
        
        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(1240, 441, 357, 53);
        add(txtUsername);
        
        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(1248, 521, 89, 22);
        lblPass.setForeground(Color.black);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblPass);
               
        JTextField txtPass = new JTextField();
        txtPass.setBounds(1240, 549, 357, 53);
        add(txtPass);
        
        JButton btnStart = new JButton("Start"); 
        btnStart.setBounds(1241, 628, 357, 53);
        add(btnStart);
        
        btnStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                dispose();
                
                MainPage homepage = new MainPage();
                homepage.setVisible(true);
                
            };
            
        });
        
        setVisible(true);
        
    }
    
    public static void main(String[] args) {
       
        HomePage hp = new HomePage();
        hp.setVisible(true);
        
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;
//
//        System.out.println("Full screen size is: " + screenWidth + " x " + screenHeight);
//        // Example output: 1920 x 1080
        
    }
    
}
