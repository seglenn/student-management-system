package g3.student.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class HomePage extends JFrame {

    ImageIcon imgSchool, imgGradient, imgGradientTwo, imgDashOne, imgDashTwo, imgPaper, imgLogoName;
    JLabel imgDashDown, imgDashUp, imgBg, imgGradOne, imgPpr, imgGradTwo, lblWelcome, lblSignIn, lblUsername, lblPass, imgNameLogo, lblSms;
    JTextField txtUsername;
    JPasswordField txtPass;
    JButton btnStart;

    HomePage() {
 
        setTitle("Student Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        imgLogoName    = new ImageIcon("images/logo-name.png");
        imgSchool      = new ImageIcon("images/school-bg.png");
        imgGradient    = new ImageIcon("images/hp-gradient.png");
        imgGradientTwo = new ImageIcon("images/hp-gradient-two.png");
        imgDashOne     = new ImageIcon("images/hp-dash-one.png");
        imgDashTwo     = new ImageIcon("images/hp-dash-two.png");
        imgPaper       = new ImageIcon("images/hp-paper.png");

        lblSms = new JLabel("Student Management System");
        lblSms.setBounds(66, 117, 426, 28);
        lblSms.setForeground(Color.decode("#b4b4b4"));
        lblSms.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblSms);

        imgNameLogo = new JLabel(imgLogoName);
        imgNameLogo.setBounds(66, 52, 264, 65);
        add(imgNameLogo);

        imgDashDown = new JLabel(imgDashOne);
        imgDashDown.setBounds(885, 594, 419, 491);
        add(imgDashDown);

        imgDashUp = new JLabel(imgDashTwo);
        imgDashUp.setBounds(1626, -75, 461, 424);
        add(imgDashUp);

        imgBg = new JLabel(imgSchool);
        imgBg.setBounds(0, 0, 886, 1085);
        add(imgBg);

        imgGradOne = new JLabel(imgGradient);
        imgGradOne.setBounds(912, 524, 1196, 972);
        add(imgGradOne);

        imgPpr = new JLabel(imgPaper);
        imgPpr.setBounds(881, 0, 206, 234);
        add(imgPpr);

        imgGradTwo = new JLabel(imgGradientTwo);
        imgGradTwo.setBounds(157, -741, 1194, 966);
        add(imgGradTwo);

        lblWelcome = new JLabel("Welcome!");
        lblWelcome.setBounds(1240, 312, 141, 32);
        lblWelcome.setForeground(Color.black);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 30));
        add(lblWelcome);

        lblSignIn = new JLabel("Sign In to access the system");
        lblSignIn.setBounds(1240, 347, 262, 22);
        lblSignIn.setForeground(Color.decode("#737373"));
        lblSignIn.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblSignIn);

        lblUsername = new JLabel("Username");
        lblUsername.setBounds(1240, 413, 95, 22);
        lblUsername.setForeground(Color.black);
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(1240, 441, 357, 53);
        txtUsername.setCaretColor(Color.BLACK);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(txtUsername);

        lblPass = new JLabel("Password");
        lblPass.setBounds(1240, 521, 89, 22);
        lblPass.setForeground(Color.black);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(1240, 549, 357, 53);
        txtPass.setCaretColor(Color.BLACK);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(txtPass);

        btnStart = new JButton("Start");
        btnStart.setBounds(1241, 628, 357, 60);
        btnStart.setFont(new Font("Segoe UI", Font.BOLD, 30));
        btnStart.setForeground(Color.WHITE);
        btnStart.setBackground(Color.decode("#1f88e4"));
        btnStart.setFocusPainted(false);
        btnStart.setBorderPainted(false);
        add(btnStart);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = txtUsername.getText().trim();
                String password = new String(txtPass.getPassword()).trim();

                if (userName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Please enter your username and password.",
                        "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Connection connection = (Connection) DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/sms_db", "root", "");

                    PreparedStatement st = (PreparedStatement) connection.prepareStatement(
                            "SELECT name, password FROM admin WHERE name = ? AND password = ?");
                    st.setString(1, userName);
                    st.setString(2, password);

                    ResultSet rs = st.executeQuery();

                    if (rs.next()) {
                        rs.close();
                        st.close();
                        connection.close();

                        dispose();
                        MainPage mainPage = new MainPage();
                        mainPage.setVisible(true);
                        JOptionPane.showMessageDialog(null, "You have successfully logged in.");

                    } else {
                        rs.close();
                        st.close();
                        connection.close();
                        JOptionPane.showMessageDialog(null,
                            "Wrong Username & Password.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                        "Database error: " + sqlException.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        HomePage hp = new HomePage();
        hp.setVisible(true);
    }
}