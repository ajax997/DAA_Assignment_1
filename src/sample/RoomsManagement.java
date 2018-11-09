package sample;

import java.util.ArrayList;

/**
 * Created by nguyennghi on 9/27/17.
 */
public class RoomsManagement {
    ArrayList<ClassRooms> rooms;
    RoomsManagement(ArrayList<ClassRooms> rooms)
    {
        this.rooms = rooms;
    }

// A504	30	PM

    String getNextMatchRoom(SubjectType subjectType, int size)
    {
        int index = 0;
        String res;
        int min = 1000;
        for(int i = 0; i< rooms.size(); i++)
        {
            ClassRooms classRooms = rooms.get(i);
            if(classRooms.getNumberOfSize() - size < min && subjectType == classRooms.getRoomType())
            {
                index = i;
                min = classRooms.getNumberOfSize();
            }
        }
        res = rooms.get(index).getName();
        rooms.remove(index);

        return res;
    }
}
