import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }
    public void addStudent(Student student) {
        students.add(student);
    }
    public ArrayList<Student> getAllStudents() {
        return students;
    }
    public void deleteStudent(int studentId) {
        students.removeIf(s -> s.getStudentId() == studentId);
    }
    public Student findStudentById(int studentId) {
        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }
        return null;
    }
}
