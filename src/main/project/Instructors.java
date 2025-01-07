import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.ArrayList;


public class Instructors {


    static String SUCCESS_SIGNUP_MESSAGE = "SIGNUP SUCCESSFUL: Welcome";
    static String FAILED_SIGNUP_USERNAME_MESSAGE = "SIGNUP UNSUCCESSFUL: Username already used, try another unique username";
    static String FAILED_SIGNUP_PASSWORD_MESSAGE = "SIGNUP UNSUCCESSFUL: Password should be at least 8 characters long";
    private ArrayList<Instructor> instructors;

    public String instructorSignup(String username, String password){
        if(instructorExists(username))
            return FAILED_SIGNUP_USERNAME_MESSAGE;
        if(passwordInvalid(password))
            return FAILED_SIGNUP_PASSWORD_MESSAGE;
        addInstructorToFile(username, password);
        return SUCCESS_SIGNUP_MESSAGE;
    }

    public static boolean instructorExists(String username) {
        boolean exists = false;
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/instructors.txt"));
            String curLine;
            while (scanner.hasNextLine()) {
                curLine = scanner.nextLine();
                String[] array = curLine.split(",");
                if (array[0].equals(username))
                    exists = true;
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return exists;
    }

    public static boolean passwordInvalid(String password){
        return !(password.length() >= 8);
    }


    public static void addInstructorToFile(String username, String password){
        String string = username + "," + password + "\n";
        Scanner scanner=new Scanner("src/main/resources/instructors.txt");
        ArrayList<String> list=new ArrayList<>();
        while (scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/instructors.txt"))) {
            for(String string1:list){
                writer.write(string1);
                writer.newLine();
            }
            writer.write(string+","+list.size()+1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public static void removeInstructor(String username){
        StringBuilder string = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/instructors.txt"));
            String curLine;
            while (scanner.hasNextLine()) {
                curLine = scanner.nextLine();
                String[] array = curLine.split(",");
                if (array[0].equals(username)){
                    continue;
                }
                string.append(curLine);
                string.append("\n");
            }
            Files.write(Paths.get("src/main/resources/instructors.txt"), string.toString().getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Instructors() throws FileNotFoundException {
        Scanner scanner=new Scanner(new File("src/main/resources/instructors.txt"));
        instructors=new ArrayList<>();
        String curLine;
        while (scanner.hasNextLine()){
            curLine=scanner.nextLine();
             String[]array=curLine.split(",");
               if(UniversalMethods.isInteger(array[2])){
                 Instructor instructor=new Instructor(Integer.parseInt(array[2]));
                 instructors.add(instructor);
             }
        }
    }
    public Instructor searchForInstructor(int ID){
        for (Instructor instructor:instructors)if(instructor.getInstructorID()==ID)return instructor;
        return null;

    }
 }
