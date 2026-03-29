import java.util.*;

class Grade {
    private int studentId;
    private double grade;

    public Grade(int studentId, double grade) {
        this.studentId = studentId;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getGrade() {
        return grade;
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
}
