package sample;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 9/13/17.
 */
public class Subject {
    private ArrayList<Student> students = new ArrayList<>();
     ArrayList<String> listOfTheoryGroup = new ArrayList<>();
     ArrayList<String> listOfExerciseGroup = new ArrayList<>();
    private String subjectName;
    private String subjectCode;
    private SubjectType midTestingMethol;
    private SubjectType finalTestingMethol;

    public Subject(String subjectName, String subjectCode, SubjectType midTestingMethod, SubjectType finalTestingMethod) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.midTestingMethol = midTestingMethod;
        this.finalTestingMethol = finalTestingMethod;

    }

    public SubjectType getMidTestingMethol() {
        return midTestingMethol;
    }

    public SubjectType getFinalTestingMethol() {
        return finalTestingMethol;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void addNewStudent(Student student)
    {
        students.add(student);
    }
    public void addNewTheoryGroup(String groupName) {
        if (listOfTheoryGroup.size()==0||(!listOfTheoryGroup.get(listOfTheoryGroup.size()-1).equals(groupName)))
            listOfTheoryGroup.add(groupName);
    }
    public void addNewExerciseGroup(String groupName) {
        if (listOfExerciseGroup.size()==0||(!listOfExerciseGroup.get(listOfExerciseGroup.size()-1).equals(groupName)&&!groupName.equals("")))
            listOfExerciseGroup.add(groupName);
    }
    public boolean checkStudentExist(String studentCode) {
        for (Student stu : students)
            if (stu.getStudentCode().equals(studentCode)) {
                return true;
            }
        return false;
    }
    public Student getStudentFromCode(String studentCode)
    {
        for(Student stu: students)
            if(stu.getStudentCode().equals(studentCode))
            {
                return stu;
            }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return subjectName.equals(subject.subjectName) && subjectCode.equals(subject.subjectCode);
    }


}
