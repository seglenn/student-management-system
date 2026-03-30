import java.util.*;

class Grade {
    private int studentId;
    private String subject;
    private double grade;

    public Grade(int studentId, String subject, double grade) {
        this.studentId = studentId;
        this.subject = subject;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

class GradeManager {
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

    public void updateGrade(int studentId, double newGrade) {
        for (Grade grade : grades) {
            if (grade.getStudentId() == studentId) {
                grade.setGrade(newGrade);
            }
        }
    }

    public void deleteGrade(int studentId) {
        grades.removeIf(grade -> grade.getStudentId() == studentId);
    }
}
