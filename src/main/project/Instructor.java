
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Instructor {
    private String InstructorName;
    private String InstructorPass;
    private int instructorID;

    public Instructor(String instructorName,String instructorPass, int instructorID){

        this.instructorID=instructorID;
        this.InstructorPass=instructorPass;
        this.InstructorName=instructorName;
    }
    public int getInstructorID(){return instructorID;}
    public   String getName(){return InstructorName;}
    public String getInstructorPass(){return InstructorPass;}



}

