package sample;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nguyennghi on 9/20/17.
 */
public class AssignRoom {
    private ArrayList<ArrayList<String>> roomList;
    private ArrayList<ArrayList<String>> listTheoryRoom;
    private ArrayList<ArrayList<String>> listExerciseRoom;
    private boolean isMid;
    private String startDate;
    private ArrayList<Subject> subjects ;
    private ArrayList<Student> students ;
    private ArrayList<TestNode> listOfShift;
    ArrayList<ArrayList<String>> grouped;
    AssignRoom(ArrayList<ArrayList<String>> roomList, ArrayList<ArrayList<String>> grouped, ArrayList<Student> students,
               ArrayList<Subject> subjects, boolean isMidTest, String startDate) {
        this.roomList = roomList;
        this.startDate = startDate;
        this.isMid = isMidTest;
        this.subjects = subjects;
        this.students = students;
        this.grouped = grouped;
        listExerciseRoom = new ArrayList<>();
        listTheoryRoom = new ArrayList<>();
        Iterator<ArrayList<String>> iterator = roomList.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> tmp = iterator.next();
            if (tmp.get(2).equals("PM")) {
                listExerciseRoom.add(tmp);
                iterator.remove();
            } else {
                if (tmp.get(2).equals("LT")) {
                    listTheoryRoom.add(tmp);
                }
                iterator.remove();
            }
        }

        listExerciseRoom.sort(new CustomComparator());
        listTheoryRoom.sort(new CustomComparator());
        listOfShift = listOfShift(startDate, isMidTest);
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
    TestNode testNodeCopy(TestNode sample)
    {
        return new TestNode(sample.date, sample.shift, sample.time);
    }

    ArrayList<TestNode> finalResult() {
        ArrayList<TestNode> result = new ArrayList<>();
        int freq = listOfShift.size() / grouped.size();
        int step = 0;
        //get a summon of subject at a moment
        for (ArrayList<String> node : grouped) {
            TestNode current = listOfShift.get(freq * step);

            //create new room list
            RoomsManagement roomsManagement = new RoomsManagement(listOfClassRoom());

            //get single subject
            for (String subjectCode : node) {
                TestNode tn = testNodeCopy(current);

                Subject subject = getSubjectFromCode(subjectCode);

                    // giua ky ly thuyet
                    if (subject.getMidTestingMethol() == (SubjectType.TESTING_METHOD_LT)) {

                        int numOfStudent = subject.listOfTheoryGroup.size();
                        int mark = 0;
                        for(int i = 0; i<numOfStudent;i++)
                        {
                            int rate = subject.getStudents().size()/subject.listOfTheoryGroup.size();
                            TestNode finalTestNode = testNodeCopy(tn);
                            if(isMid)
                            finalTestNode.setDuration("45 minutes");
                            else
                                finalTestNode.setDuration("90 minutes");
                            finalTestNode.setSubjectName(subject.getSubjectName());
                            finalTestNode.setSubjectCode(subject.getSubjectCode());
                            finalTestNode.setRoomName(roomsManagement.getNextMatchRoom(SubjectType.TESTING_METHOD_LT,
                                    rate));
                           finalTestNode.setGroup(subject.listOfTheoryGroup.get(i));
                            finalTestNode.setSubGroup(subject.listOfTheoryGroup.get(i));
                            for( int j= 0;j< subject.getStudents().size(); j++)
                            {
                                if(j>mark && j<=(i+1)*rate)
                                    finalTestNode.addNewStudent(subject.getStudents().get(j));
                            }
                            mark = (i+1)*rate;
                            result.add(finalTestNode);
                        }
                    }

                    if (subject.getMidTestingMethol() == (SubjectType.TESTING_METHOD_PM)) {
                        int numOfStudent = subject.listOfExerciseGroup.size();
                        int mark = 0;
                        for(int i = 0; i<numOfStudent;i++)
                        {
                            int rate = subject.getStudents().size()/subject.listOfExerciseGroup.size();
                            TestNode finalTestNode = testNodeCopy(tn);

                            if(isMid)
                                finalTestNode.setDuration("45 minutes");
                            else
                                finalTestNode.setDuration("90 minutes");
                            finalTestNode.setSubjectName(subject.getSubjectName());
                            finalTestNode.setSubjectCode(subject.getSubjectCode());
                            finalTestNode.setRoomName(roomsManagement.getNextMatchRoom(SubjectType.TESTING_METHOD_PM,
                                    rate));
                           // finalTestNode.setGroup(subject.listOfTheoryGroup.get(i));
                            finalTestNode.setSubGroup(subject.listOfExerciseGroup.get(i));
                            for( int j= 0;j< subject.getStudents().size(); j++)
                            {
                                if(j>mark && j<=(i+1)*rate)
                                    finalTestNode.addNewStudent(subject.getStudents().get(j));
                            }
                            mark = (i+1)*rate;
                            result.add(finalTestNode);
                        }

                    }
                    if (subject.getMidTestingMethol() == (SubjectType.TESTING_METHOD_SDU)) {

                    }
                    if (subject.getMidTestingMethol() == (SubjectType.TESTING_METHOD_SP)) {

                    }

            }

            step++;
        }

        return result;
    }

    ArrayList<ClassRooms> listOfClassRoom()
    {
        ArrayList<ClassRooms> result = new ArrayList<>();
        for (ArrayList<String> tmp : listTheoryRoom)
        {
            result.add(new ClassRooms(tmp.get(0),Integer.parseInt(tmp.get(1)),SubjectType.TESTING_METHOD_LT));
        }
        for (ArrayList<String> tmp : listExerciseRoom)
        {
            result.add(new ClassRooms(tmp.get(0),Integer.parseInt(tmp.get(1)),SubjectType.TESTING_METHOD_PM));
        }
        for (int i = 1; i<= 10; i++)
        {
            result.add(new ClassRooms("BT"+String.valueOf(i),Integer.MAX_VALUE, SubjectType.TESTING_METHOD_SDU));
        }
        return result;
    }

    ArrayList<TestNode> listOfShift(String startDate, boolean isMid)
    {
        ArrayList<TestNode> result= new ArrayList<>();
        String date = startDate;
        if(isMid) {
            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= 10; j++) {
                    result.add(new TestNode(date, j, getTimeFromShift(j, isMid)));
                }
                date = getNextDate(date);
            }
        }
        else
        {
            for (int i = 1; i <= 14; i++) {
                if(i != 7) {
                    for (int j = 1; j <= 4; j++) {
                        {
                            result.add(new TestNode(date, j, getTimeFromShift(j, isMid)));
                        }
                    }
                }
                date = getNextDate(date);
            }
        }
        return result;
    }

    String getTimeFromShift(int shift, boolean isMid) {
        if (isMid) {
            switch (shift) {
                case 1:
                    return "8:00";
                case 2:
                    return "9:00";
                case 3:
                    return "10:00";
                case 4:
                    return "11:00";
                case 5:
                    return "13:00";
                case 6:
                    return "14:00";
                case 7:
                    return "15:00";
                case 8:
                    return "16:00";
                case 9:
                    return "18:00";
                case 10:
                    return "19:00";
            }
        } else {
            switch (shift) {
                case 1:
                    return "8:00";
                case 2:
                    return "9:45";
                case 3:
                    return "13:00";
                case 4:
                    return "15:45";
            }
        }
        return "";
    }

    private static String getNextDate(String  curDate) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar calendar;
        final Date date;
        try {
           date  = format.parse(curDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return format.format(calendar.getTime());
        }
        catch (Exception e)
        {e.printStackTrace();}
        return "";
    }

    public int getNumberOfExerciseRoom(){

        return listExerciseRoom.size();
    }
    public int getNumberOfTheoryRoom(){
        return listTheoryRoom.size();
    }

}
