import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;



public class UniversalMethods {



    public static Stack<File>tempfiles=new Stack<>();
    public static Stack<File>tempfiles1=new Stack<>();


    public static HashMap<String, File> fileMap = new HashMap<>();

    private static int index=0;
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void afterAllScenarios() {
        if(fileMap.isEmpty()){
            fileMap.put("src/main/resources/programsssTemp.txt",new File("src/main/resources/programsssTemp.txt"));
            fileMap.put("src/main/resources/instructorsTemp.txt",new File("src/main/resources/instructorsTemp.txt"));
            fileMap.put("src/main/resources/groupsTemp.txt",new File("src/main/resources/groupsTemp.txt"));
            fileMap.put("src/main/resources/clients_with_progressTemp.txt",new File("src/main/resources/clients_with_progressTemp.txt"));

        }
        UniversalMethods.returnFile("src/main/resources/programsss.txt");
        UniversalMethods.returnFile("src/main/resources/instructors.txt");
        UniversalMethods.returnFile("src/main/resources/groups.txt");
        UniversalMethods.returnFile("src/main/resources/clients_with_progress.txt");
    }


    public static void returnFile(String path){
        File originalFile = new File(path);
        String tempFilePath=path.split("\\.")[0]+="Temp"+"."+path.split("\\.")[1];
        System.out.println(tempFilePath);

        try {
            // Step 1: Copy contents from original file to temp file
            File tempFile=fileMap.get(tempFilePath);
            if(tempFile==null)System.out.println("fkhfdbfkeb");
            copyFile(tempFile, originalFile);
 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyFile(File source, File destination) throws IOException {
        // Create input and output streams
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {

            byte[] buffer = new byte[1024]; // Buffer to hold chunks of data
            int length;

            // Read from source and write to destination
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }




}
 
