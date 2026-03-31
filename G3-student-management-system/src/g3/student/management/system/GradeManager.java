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
            if (grade.getStudentId() == studentId) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }
}

class Grade {
    private int studentId;
    private String subject;
    private double gradeValue;

    public Grade(int studentId, String subject, double gradeValue) {
        this.studentId = studentId;
        this.subject = subject;
        this.gradeValue = gradeValue;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getGradeValue() {
        return gradeValue;
    }
}
