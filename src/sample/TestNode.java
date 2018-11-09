package sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nguyennghi on 9/26/17.
 */
public class TestNode {
    String date;
    int shift;
    String time;
    ArrayList<Student> students = new ArrayList<>();
    private String room;
    private String group;
    private String subGroup;
    private String subjectName;
    private String subjectCode;
    private String duration;

    TestNode(String date, int shift, String time) {
        this.date = date;
        this.shift = shift;
        this.time = time;
    }

     int getTotalNumber() {
        return students.size();
    }

     String getDate() {
        return date;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDateOfWeek() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar calendar;
        final Date date;
        try {
            date = format.parse(this.date);
            calendar = Calendar.getInstance();
            calendar.setTime(date);

            return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    void setRoomName(String room) {
        this.room = room;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    void addNewStudent(Student student) {
        students.add(student);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }
}
