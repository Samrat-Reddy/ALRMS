package models;

/**
 * models.Student.java
 * Represents a models.Student who can borrow books from the library.
 * Used in models.IssueRecord to track who borrowed which book.
 */
public class Student {
    private int studentId;
    private String name;
    private String email;
    private String department;

    public Student(int studentId, String name, String email, String department) {
        this.studentId  = studentId;
        this.name       = name;
        this.email      = email;
        this.department = department;
    }

    // Getters
    public int    getStudentId()   { return studentId; }
    public String getName()        { return name; }
    public String getEmail()       { return email; }
    public String getDepartment()  { return department; }

    // Setters
    public void setName(String name)               { this.name = name; }
    public void setEmail(String email)             { this.email = email; }
    public void setDepartment(String department)   { this.department = department; }

    @Override
    public String toString() {
        return String.format(
            "[StudentID: %d | Name: %-20s | Email: %-25s | Dept: %s]",
            studentId, name, email, department
        );
    }
}
