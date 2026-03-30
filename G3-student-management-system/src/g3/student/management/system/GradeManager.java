public class GradeManager {
    private List<Grade> grades;

    public GradeManager() {
        this.grades = new ArrayList<>();
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getAllGrades() {
        return grades;
    }

    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> studentGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.studentID() == studentId) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }
}

class Grade {
    private int studentId;
    private String subject;
    private double grade;

    public Grade(int studentId, String subject, double grade) {
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
    }

    public int studentID() {
        return studentId;
    }

    public String Subject() {
        return subject;
    }

    public double Grade() {
        return grade;
    }
}
