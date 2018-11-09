package sample;

/**
 * Created by nguyennghi on 9/14/17.
 */
public class ClassRooms {

    private String name;
    private int numberOfSize;
    private SubjectType roomType;

    public ClassRooms(String name, int numberOfSize, SubjectType subjectType) {
        this.name = name;
        this.numberOfSize = numberOfSize;
        this.roomType = subjectType;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfSize() {
        return numberOfSize;
    }

    public SubjectType getRoomType() {
        return roomType;
    }
}
