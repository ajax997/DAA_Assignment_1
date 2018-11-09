package sample;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 9/13/17.
 */
public class Student {
    private ArrayList<Subject> subjects = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String studentCode;
    Student(String studentCode, String firstName,String lastName )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentCode = studentCode;
    }
    Student(Student sample)
    {
        this.subjects = sample.subjects;
        this.firstName = sample.firstName;
        this.lastName = sample.lastName;
        this.studentCode = sample.studentCode;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }


    public String getStudentCode() {
        return studentCode;
    }

    //
    public void addNewSubject(Subject subject)
    {
        subjects.add(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentCode != null ? studentCode.equals(student.studentCode) : student.studentCode == null;
    }


}
