package sample;

import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguyennghi on 9/14/17.
 */
 class Scheduling {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<String> studentsUnion = new ArrayList<>();
    private ArrayList<String> subjectsUnion = new ArrayList<>();
    private ArrayList<ArrayList<String>> registeredList;
    private ArrayList<ArrayList<String>> roomList;
    private String exportLocation;
    private HashMap<Integer,String> subjectMapping = new HashMap<>();
    private boolean isMiddleTest;
    private ArrayList<ClassRooms> listRooms;
    private String startDate;
    private  ArrayList<TestNode> finalResult;
    private ArrayList<ArrayList<Integer>> grouped;

    Scheduling(ArrayList<ArrayList<String>> registeredList, ArrayList<ArrayList<String>> roomList, boolean isMiddleTest, String startDate, String exportPath) {
        this.startDate = startDate;
        this.exportLocation = exportPath;
        this.registeredList = registeredList;
        this.roomList = roomList;
        this.isMiddleTest = isMiddleTest;
    }

    public ArrayList<String> getStudentsUnion() {
        return studentsUnion;
    }

    public void setStudentsUnion(ArrayList<String> studentsUnion) {
        this.studentsUnion = studentsUnion;
    }

    public ArrayList<String> getSubjectsUnion() {
        return subjectsUnion;
    }

    public ArrayList<ClassRooms> getListRooms() {
        return listRooms;
    }

    public void setSubjectsUnion(ArrayList<String> subjectsUnion) {
        this.subjectsUnion = subjectsUnion;
    }

    public ArrayList<ArrayList<String>> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<ArrayList<String>> roomList) {
        this.roomList = roomList;
    }

    public ArrayList<TestNode> getFinalResult() {
        return finalResult;
    }


    private boolean checkStudentExist(String studentCode)
    {
        return studentsUnion.contains(studentCode);
    }

    private Subject getSubjectFromCode(String code)
    {
        for (Subject sub: subjects)
        {
            if(sub.getSubjectCode().equals(code))
                return sub;
        }
        return null;
    }

    private Student getStudentFromCode(String code)
    {
        for(Student stu: students) {
            if (stu.getStudentCode().equals(code))
                return stu;
        }
        return null;
    }

    String insertData() {
        int t = 1;
        try {
            for (ArrayList<String> line : registeredList) {
            t++;
                String subjectCode = line.get(0);
                String subjectName = line.get(1);
                String theoryGroup = line.get(2);
                String exerciseGroup = line.get(3);
                String studentCode = line.get(5);
                String firstName = line.get(7);
                String lastName = line.get(6);

                //Skip Do An subjects
                if (subjectName.startsWith("Đồ án"))
                    continue;
                if (!subjectsUnion.contains(subjectCode)) {
                    Subject subject;

                    if ((exerciseGroup.trim().equals("") || SubjectType.isSkillSubject(subjectCode)) && !subjectName.startsWith("Thực hành")) //mon ki nang
                        subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_LT, SubjectType.TESTING_METHOD_LT);
                    else {

                        if (subjectName.startsWith("Thực hành"))
                            subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_PM, SubjectType.TESTING_METHOD_PM);
                        else {
                            if (subjectCode.equals("D01001")) { //boi
                                subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_LT, SubjectType.TESTING_METHOD_SP);
                            } else {
                                if (SubjectType.isSportSubject(subjectCode)) { //mon the thao
                                    subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_SDU, SubjectType.TESTING_METHOD_SDU);
                                } else {
                                    if (SubjectType.isGeneralSubject(subjectCode)) { //mon nham lon
                                        subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_PM, SubjectType.TESTING_METHOD_PM);
                                    } else {
                                        subject = new Subject(subjectName, subjectCode, SubjectType.TESTING_METHOD_PM, SubjectType.TESTING_METHOD_LT);
                                    }
                                }

                            }
                        }

                    }

                    subjects.add(subject);


                    if (!subjectsUnion.contains(subjectCode))
                        subjectsUnion.add(subjectCode);

                    subject.addNewTheoryGroup(theoryGroup);
                    subject.addNewExerciseGroup(exerciseGroup);

                    if (!checkStudentExist(studentCode)) {
                        Student tmpStudent = new Student(studentCode, firstName, lastName);
                        students.add(tmpStudent);
                        studentsUnion.add(studentCode);
                        getStudentFromCode(studentCode).addNewSubject(subject);
                        subject.addNewStudent(getStudentFromCode(studentCode));

                    } else {
                        getStudentFromCode(studentCode).addNewSubject(subject);
                    }
                } else {

                    Subject subjectTemp = getSubjectFromCode(subjectCode);
                    subjectTemp.addNewExerciseGroup(exerciseGroup);
                    subjectTemp.addNewTheoryGroup(theoryGroup);
                    if (checkStudentExist(studentCode)) {
                        getStudentFromCode(studentCode).addNewSubject(subjectTemp);
                        subjectTemp.addNewStudent(getStudentFromCode(studentCode));
                    } else {
                        Student student = new Student(studentCode, firstName, lastName);
                        students.add(student);
                        if (!studentsUnion.contains(studentCode))
                            studentsUnion.add(studentCode);
                        student.addNewSubject(subjectTemp);
                        subjectTemp.addNewStudent(student);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(t);
            return "Error while trying to insert data to subject class";
        }
        //registeredList.clear();
        return "Adding data successfully";

    }

    ArrayList<ArrayList<String >> coverSub(ArrayList<ArrayList<Integer>> grouped, HashMap<Integer, String> subjectMapping)
    {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for(ArrayList<Integer> tmp: grouped)
        {
            ArrayList<String> unit = new ArrayList<>();
            for (Integer t : tmp)
            {
                unit.add(subjectMapping.get(t));
            }
            result.add(unit);
        }
        return result;
    }

    String buildGraph()
    {
        HashMap<String,Integer> subjectTable = new HashMap<>();
        int count = 0;
        for(String s : subjectsUnion)
        {
            subjectTable.put(s, count);
            subjectMapping.put(count,s);
            count++;

        }

        try{
            GraphColoring graphColoring = new GraphColoring(subjectsUnion.size());
            for(Student stu: students)
            {
                ArrayList<Subject> subs = stu.getSubjects();
//                for (Subject sub : subs) {
//                    graphColoring.addEdge(subjectTable.get(subs.get(0).getSubjectCode()), subjectTable.get(sub.getSubjectCode()));
//                }
                for(int i = 0; i< subs.size(); i++)

                {
                    for(int j = i; j < subs.size(); j++)
                    {
                        graphColoring.addEdge(subjectTable.get(subs.get(i).getSubjectCode()), subjectTable.get(subs.get(j).getSubjectCode()));
                    }
                }
            }
           grouped = graphColoring.greedyColoring();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Error while trying to build the graph " + e.getMessage();
        }
        return "The graph has been built";
    }

    String assignRoom()
    {
        try{
            AssignRoom assignRoom = new AssignRoom(roomList, coverSub(grouped,subjectMapping), students, subjects,isMiddleTest, startDate);
            listRooms = assignRoom.listOfClassRoom();
            finalResult = assignRoom.finalResult();
            XlsxFileAccess.writeDate(finalResult, exportLocation,isMiddleTest );
           // System.out.print(assignRoom.getNumberOfExerciseRoom() +"  "+ assignRoom.getNumberOfTheoryRoom());
        }
        catch (Exception e){
            e.printStackTrace();
            return  e.getLocalizedMessage();
        }
        return "Assign room successfully, Files has been exported";
    }
}
