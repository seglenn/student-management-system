import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentManager extends JFrame implements ActionListener {

    // ===== INNER CLASS (Student) =====
    class Student {
        private int id;
        private String name;
        private String course;

        public Student(int id, String name, String course) {
            this.id = id;
            this.name = name;
            this.course = course;
        }

        public int getId() {
            return id;
        }

        public String toString() {
            return id + " - " + name + " (" + course + ")";
        }
    }

    // ===== COMPONENTS =====
    JTextField txtName, txtID, txtCourse;
    JTextArea displayArea;
    JButton btnAdd, btnView, btnDelete;

    // ===== STORAGE =====
    ArrayList<Student> students = new ArrayList<>();

    public StudentManager() {
        setTitle("Student Record Manager");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        txtName = new JTextField(10);
        txtID = new JTextField(5);
        txtCourse = new JTextField(10);

        btnView = new JButton("View");
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");

        topPanel.add(new JLabel("Name:"));
        topPanel.add(txtName);
        topPanel.add(new JLabel("ID:"));
        topPanel.add(txtID);
        topPanel.add(new JLabel("Course:"));
        topPanel.add(txtCourse);
        topPanel.add(btnView);
        topPanel.add(btnAdd);
        topPanel.add(btnDelete);

        // ===== DISPLAY AREA =====
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // ===== FRAME LAYOUT =====
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON EVENTS =====
        btnAdd.addActionListener(this);
        btnView.addActionListener(this);
        btnDelete.addActionListener(this);

        setVisible(true);
    }

    // ===== REQUIRED METHODS =====

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public void deleteStudent(int studentId) {
        students.removeIf(s -> s.getId() == studentId);
    }

    public Student findStudentById(int studentId) {
        for (Student s : students) {
            if (s.getId() == studentId) {
                return s;
            }
        }
        return null;
    }

    // ===== BUTTON LOGIC =====
    public void actionPerformed(ActionEvent e) {
        String name = txtName.getText().trim();
        String idText = txtID.getText().trim();
        String course = txtCourse.getText().trim();

        if (e.getSource() == btnAdd) {
            if (!name.isEmpty() && !idText.isEmpty() && !course.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    Student s = new Student(id, name, course);
                    addStudent(s);

                    JOptionPane.showMessageDialog(this, "Student Added!");

                    txtName.setText("");
                    txtID.setText("");
                    txtCourse.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID must be a number.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Fill all fields.");
            }
        }

        if (e.getSource() == btnView) {
            displayArea.setText("");
            for (Student s : getAllStudents()) {
                displayArea.append(s.toString() + "\n");
            }
        }

        if (e.getSource() == btnDelete) {
            try {
                int id = Integer.parseInt(idText);

                Student s = findStudentById(id);
                if (s != null) {
                    deleteStudent(id);
                    JOptionPane.showMessageDialog(this, "Student Deleted!");
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found.");
                }

                txtID.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid ID.");
            }
        }
    }

    public static void main(String[] args) {
        new StudentManager();
    }
}