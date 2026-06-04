
package g3.student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AttendancePanel extends JPanel {
    
    
    private JLabel lblTitle, lblSubtxt, imgDisplay; 
    private JButton btnAddAtt;
    private ImageIcon imgDashOne;
    private JTable attTable;
    private DefaultTableModel tableModel; 
    private JScrollPane scrollPane;
    
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
        
        String[] columns = {"Date", "Student Name", "Status", "Comment", "Course/Subject",};
        tableModel = new DefaultTableModel(columns, 0);
        attTable = new JTable(tableModel);
        
        attTable.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        attTable.setRowHeight(40);
        attTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        attTable.getTableHeader().setBackground(Color.decode("#1f87e2"));
        attTable.getTableHeader().setForeground(Color.WHITE);
        attTable.setSelectionBackground(Color.decode("#e3f2fd"));
        attTable.setGridColor(Color.decode("#e0e0e0"));
        
        JTableHeader header = attTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBackground(Color.decode("#1f87e2"));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));
        
        scrollPane = new JScrollPane(attTable);
        scrollPane.setBounds(45, 200, 1450, 650);
        add(scrollPane); 
        
        btnAddAtt.addActionListener (new ActionListener(){
            
            private JLabel lblComm, lblAddStud, lblSubtitle, lblName, lblDate, lblStatus;
            private JTextField tfFName, tfDate, tfComm; 
            private JComboBox cmbStatus;
            private JButton btnAdd;
            
            @Override
            public void actionPerformed (ActionEvent e){
                
                JFrame frmAddStud = new JFrame();
                frmAddStud.setSize(904, 525);
                frmAddStud.setLayout(null);
                frmAddStud.setLocationRelativeTo(null);
                frmAddStud.setTitle("ADD NEW ATTENDANCE");
                frmAddStud.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                lblAddStud = new JLabel("Add new attendance ");
                lblAddStud.setForeground(Color.BLACK);
                lblAddStud.setFont(new Font("Segoe UI", Font.BOLD, 25));
                lblAddStud.setBounds(35, 36, 357, 39);
                frmAddStud.add(lblAddStud);

                lblSubtitle = new JLabel("Fill in the required details below");
                lblSubtitle.setForeground(Color.decode("#737373"));
                lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                lblSubtitle.setBounds(35, 70, 372, 27);
                frmAddStud.add(lblSubtitle);

                lblName = new JLabel("Student Name");
                lblName.setForeground(Color.BLACK);
                lblName.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblName.setBounds(35, 124, 152, 22);
                frmAddStud.add(lblName);

                    tfFName = new JTextField();
                    tfFName.setBounds(35, 153, 842, 53);
                    tfFName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(tfFName);

                lblDate = new JLabel("Date");
                lblDate.setForeground(Color.BLACK);
                lblDate.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblDate.setBounds(35, 231, 157, 22);
                frmAddStud.add(lblDate);
                
                    tfDate = new JTextField();
                    tfDate.setBounds(35, 259, 417, 53);
                    tfDate.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(tfDate);

                lblStatus = new JLabel("Status");
                lblStatus.setForeground(Color.BLACK);
                lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblStatus.setBounds(493, 231, 102, 22);
                frmAddStud.add(lblStatus);

                    String[] optProg = {"Present", "Absent", "Late"};
                    cmbStatus = new JComboBox<>(optProg);
                    cmbStatus.setBounds(493, 259, 384, 53);
                    cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(cmbStatus);
                    
                lblComm = new JLabel("Comment (optional)");
                lblComm.setForeground(Color.BLACK);
                lblComm.setFont(new Font("Segoe UI", Font.BOLD, 20));
                lblComm.setBounds(35, 323, 231, 22);
                frmAddStud.add(lblComm);
                
                    tfComm = new JTextField();
                    tfComm.setBounds(35, 354, 843, 53);
                    tfComm.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                    frmAddStud.add(tfComm);
                

                btnAdd = new JButton ("Add");
                btnAdd.setBounds(684, 419, 190, 53);
                btnAdd.setForeground(Color.WHITE);
                btnAdd.setBackground(Color.decode("#1f89e5"));
                btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 25));
                btnAdd.setFocusPainted(false);
                btnAdd.setBorderPainted(false);
                frmAddStud.add(btnAdd);

                btnAdd.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                ;
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


