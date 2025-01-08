

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
     private int CompletionRate;
     private int  AttendanceRecord;
     private int numOfDaysAttended;
     private int numOfDaysMessed;
     private int ID;
    private String name="un named";
     private String MotivationalReminder;
     private Program program;
     private String programTitle;
     private String Notification;
     private String Announcement;
     private String clientsWithProgressPath = "src/main/resources/clients_with_progress.txt";

    public void setName(String name){this.name=name;}
    public String getName(){return name;}
    public Client(int ID,String programTitle){
        this.ID=ID;
        this.programTitle=programTitle;

    }
    public int getID() {
        return ID;
    }

    private void writeToFile(int id,int type, String value) throws IOException {

        File file = new File(clientsWithProgressPath);
        Scanner scanner = new Scanner(file);
        String curLine;
        ArrayList<String> dataToFile=new ArrayList<>();
        String toEditLine="";
        while (scanner.hasNextLine()){
            curLine = scanner.nextLine();
            String [] array =  curLine.split(",");
            if(UniversalMethods.isInteger(array[0])&&Integer.parseInt(array[0])==id){
                switch (type){
                    case 1:toEditLine=id+","+value+","+array[2]+","+array[3]+","+array[4]+","+array[5]+","+array[6]+","+array[7];break;
                    case 2:toEditLine=id+","+array[1]+","+value+","+array[3]+","+array[4]+","+array[5]+","+array[6]+","+array[7];break;
                    case 3:toEditLine=id+","+array[1]+","+array[2]+","+value+","+array[4]+","+array[5]+","+array[6]+","+array[7];break;
                    case 4:toEditLine=id+","+array[1]+","+array[2]+","+array[3]+","+value+","+array[5]+","+array[6]+","+array[7];break;
                    case 5:toEditLine=id+","+array[1]+","+array[2]+","+array[3]+","+array[4]+","+value+","+array[6]+","+array[7];break;
                    case 6:toEditLine=id+","+array[1]+","+array[2]+","+array[3]+","+array[4]+","+array[5]+","+value+","+array[7];break;
                    case 7:toEditLine=id+","+array[1]+","+array[2]+","+array[3]+","+array[4]+","+array[5]+","+array[6]+","+value;break;
                    case 8:continue;
                }
                dataToFile.add(toEditLine);
                continue;
            }
            dataToFile.add(curLine);

        }
        scanner.close();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clientsWithProgressPath))) {
            for(String line:dataToFile){
                writer.write(line);
                writer.newLine();
            }
        }

    }




    private String SearchForClientInProgress(int id) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(clientsWithProgressPath));
        String curLine = "";
        while (scanner.hasNextLine()) {
            curLine = scanner.nextLine();
            String[] array = curLine.split(",");
            if (array[0].equals(id + "")) {
                break;
            }
        }
        scanner.close();
        return curLine;

    }



    public Program getProgram(Programs programs) throws FileNotFoundException {

        String recordString =SearchForClientInProgress(this.ID);
        if(!recordString.isEmpty()){
            String[]array= recordString.split(",");
            if(array[0].equals(this.ID+"")){
                if(!array[5].equals("---")){
                    programTitle=array[5];
                    program=programs.searchForProgram(programTitle);
                    programTitle=program.getTitle();
                    return program;
                }
                else {
                    program=null;
                    return null;
                }

            }
        }

        return null;
    }
    public void setProgram(Program program) throws IOException {
        this.program=program;
        if(program!=null) {
            this.programTitle = program.title;
            writeToFile(this.ID, 5, program.title);
        }
    }



    public String getProgramTitle(){return programTitle;}




    public int getAttendanceRecord() throws FileNotFoundException {


             String recordString =SearchForClientInProgress(this.ID);
             if(!recordString.isEmpty()){
                 String[]array= recordString.split(",");
                 if(array[0].equals(this.ID+"")){
                     if(UniversalMethods.isInteger(array[1]))this.numOfDaysAttended= Integer.parseInt(array[1]);
                     else return -1;
                     if(UniversalMethods.isInteger(array[2]))this.numOfDaysMessed=Integer.parseInt(array[2]);
                     else return -1;
                     this.AttendanceRecord=(int)( 100*(double)this.numOfDaysAttended)/(this.numOfDaysMessed+this.numOfDaysAttended);
                     return this.AttendanceRecord;
                 }
             }

        return -2;

    }

    public void setAttendanceRecord(int attendanceRecord) {
        AttendanceRecord = attendanceRecord;
    }



    public int getCompletionRate() throws FileNotFoundException {


        String recordString =SearchForClientInProgress(this.ID);
        if(!recordString.isEmpty()){
            String[]array= recordString.split(",");
            if(array[0].equals(this.ID+"")){
                if(UniversalMethods.isInteger(array[3])){
                    this.CompletionRate= Integer.parseInt(array[3]);
                    return this.CompletionRate;
                }
                else return -1;

            }
        }

        return -2;
    }

    public void setCompletionRate(int completionRate) throws IOException {
        CompletionRate = completionRate;
        writeToFile(this.ID,3,completionRate+"");
    }
    public  void removeClint() throws IOException {
        writeToFile(this.ID,8,"");
    }




 


    public void setNumOfDaysAttended(int daysAttended) throws IOException {
         numOfDaysAttended=daysAttended;
        writeToFile(this.ID,1,daysAttended+"");

    }



 

    public void setNumOfDaysMessed(int daysMessed) throws IOException {
         numOfDaysMessed=daysMessed;
        writeToFile(this.ID,2,daysMessed+"");

    }




    public String getMotivationalReminder() throws FileNotFoundException {


        String recordString =SearchForClientInProgress(this.ID);
        if(!recordString.isEmpty()){
            String[]array= recordString.split(",");
            if(array[0].equals(this.ID+"")){
                if(array[4].equals("no reserve")||array[4].equals("reserve")){
                    this.MotivationalReminder= array[4];
                    return this.MotivationalReminder;
                }
                else {
                    this.MotivationalReminder=  "no reserve";
                    return "no reserve";
                }

            }
        }

        return "wrong id";
    }

    public void setMotivationalReminder(String motivationalReminder) throws IOException {
        MotivationalReminder = motivationalReminder;
        writeToFile(this.ID, 4, motivationalReminder);
    }



    public String getNotification() throws FileNotFoundException {


        String recordString =SearchForClientInProgress(this.ID);
        if(!recordString.isEmpty()){
            String[]array= recordString.split(",");
            if(array[0].equals(this.ID+"")){
                if(array[6].equals("no notification")||array[6].equals("update")||array[6].equals("remove")){
                    this.Notification= array[6];
                    return this.Notification;
                }
                else {
                    this.Notification=  "no notification";
                    return "no notification";
                }

            }
        }

        return "wrong id";
    }
    public void setNotification(String notification) throws IOException {
        this.Notification = notification;
        writeToFile(this.ID, 6, notification);
    }

    public String getAnnouncement() throws FileNotFoundException {

        String recordString =SearchForClientInProgress(this.ID);
        if(!recordString.isEmpty()){
            String[]array= recordString.split(",");
            if(array[0].equals(this.ID+"")){
                if(array[7].equals("no announcement")||array[7].split(":")[0].equals("new program")||array[7].split(":")[0].equals("new offer")){
                    this.Announcement= array[7];
                    return this.Announcement;
                }
                else {
                    this.Announcement="no announcement";
                    return this.Announcement;
                }

            }
        }

        return "wrong id";
    }

    public void setAnnouncement(String announcement) throws IOException {
        this.Announcement=announcement;
        writeToFile(this.ID,7,announcement);

    }







}

