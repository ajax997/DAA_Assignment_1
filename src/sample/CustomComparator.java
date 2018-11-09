package sample;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by nguyennghi on 9/20/17.
 */
public class CustomComparator implements Comparator<ArrayList<String>> {
    @Override
    public int compare(ArrayList<String> o1, ArrayList<String> o2) {
        return Integer.valueOf(o1.get(1)).compareTo(Integer.valueOf(o2.get(1)));
    }
}
