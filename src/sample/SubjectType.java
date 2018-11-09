package sample;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nguyennghi on 9/13/17.
 */
public enum SubjectType {
    //computer room
   TESTING_METHOD_PM
    //theory room
   ,TESTING_METHOD_LT
    //swimming pool
    ,TESTING_METHOD_SP
    //stadium
   ,TESTING_METHOD_SDU;
    //check that subject is a skill subject
    public static boolean isSkillSubject(String subjectCode)
    {
        ArrayList<String> skill = new ArrayList<>();
        Collections.addAll(skill,"302209","302208","302203","302210","302211");

        return  skill.contains(subjectCode);
    }
    public static boolean isSportSubject(String subjectCode)
    {
        ArrayList<String> sport  = new ArrayList<>();
        Collections.addAll(sport,"D01101","D01102","D01103","D01104","D01105","D01106","D01201","D01202","D01203","D01204","D01205","D01206");
        return sport.contains(subjectCode);
    }
    public static boolean isGeneralSubject(String subjectCode)
    {
        ArrayList<String> general =new ArrayList<>();
        Collections.addAll(general,"D02030","D02028","D02029","301001","301002","301003","302053");
        return general.contains(subjectCode);
    }

    
}
