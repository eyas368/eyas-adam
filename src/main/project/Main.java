import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static Scanner scanner=new Scanner(System.in);
    private static String string;

    private static Programs programs;
    private static Clients clients;
    private static Groups groups;
    private static Instructors instructors;



    static {
        try {
            clients = new Clients();
            programs = new Clients().getPrograms();
            groups=new Groups();
            instructors=new Instructors();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void print(String string){
        System.out.println(string);
    }
    private static String read(){
        return scanner.nextLine();
    }

    public static void main(String[] args) throws IOException {
        print("Choose the type of account");
        print("1-Admin");
        print("1-Instructor");
        print("3-Client");
        if(read().equals("1"))AdminPart();


     }
     public static void AdminPart() throws IOException {
        Admin admin=new Admin();
        while (true){
            print("enter Admin ID");
            if(UniversalMethods.isInteger(string=read())&& Integer.parseInt(string)==Admin.AdminID){
            break;
            }
            else print("wrong ID :(");
        }
         while (true){
             print("enter Admin password");
             if(read().equals(Admin.AdminPassword)){
                 break;
             }
             else print("wrong password :(");
         }
         print("hello Admin :)");
         print("Choose what you want to do");
         while (true) {
             print("1-See the current Clients");
             print("2-see the current Instructors");
             print("3-Generate Reports");
             print("4-add an Instructor");
             print("5-see current programs");
             print("6-get feed back about the programs");
             print("7-see Requested Articles");
             if (!(UniversalMethods.isInteger(string = read()) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 8)) {
                 print("wrong input :(");
                 continue;

             }

             if (Integer.parseInt(string) == 1) {
                 print("ID : name");
                 for (Client client : clients.getClients()) print(client.getID() + ": " + client.getName());

                 while (true) {
                     print("");
                     print("1-remove client");
                     print("2-update client");
                     print("3-go to home page");
                     if (!(UniversalMethods.isInteger(string = read()) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 4)) {
                         print("wrong input :(");
                         continue;
                     }
                     break;

                 }
                 if (string.equals("1")) {
                     print("enter ID and name for the client \"ID:Name");
                     Client client;
                     while (true) {
                         string = read();
                         if (!string.contains(":")) {
                             print("wrong input :(");
                             continue;
                         }
                         else if(!UniversalMethods.isInteger(string.split(":")[0])){
                             print("wrong input :(");
                             continue;
                         }
                         if((client=clients.searchForClient(Integer.parseInt(string.split(":")[0])))==null|| !client.getName().equals(string.split(":")[1])){
                             print("wrong input :(");
                             continue;
                         }
                         admin.deleteUserFromFile(client.getName(),true);
                         client.removeClint();
                         clients.getClients().remove(client);
                         print("removed");
                         break;

                     }
                 }
                 else if (string.equals("2")) {
                     print("enter ID and name for the client and the new password \"ID:Name:password");
                     Client client;
                     while (true) {
                         string = read();
                         if (!string.contains(":")) {
                             print("wrong input :(");
                             continue;
                         } else if (!UniversalMethods.isInteger(string.split(":")[0])) {
                             print("wrong input :(");
                             continue;
                         }
                         if ((client = clients.searchForClient(Integer.parseInt(string.split(":")[0]))) == null || !client.getName().equals(string.split(":")[1])) {
                             print("wrong input :(");
                             continue;
                         }
                         print(string = admin.updateToClient(client.getName(), string.split(":")[2]));
                         if (!string.equals("UPDATE SUCCESS: Password was updated successfully")) continue;
                         break;

                     }
                 }
                 else if (string.equals("3")) {
                     continue;
                 }
             }
             if (Integer.parseInt(string) == 2) {
                 print("ID : name");
                 for (Instructor instructor : instructors.getInstructors()) print(instructor.getInstructorID() + ": " + instructor.getName()) ;

                 while (true) {
                     print("");
                     print("1-remove instructor");
                     print("2-update instructor");
                     print("3-go to home page");
                     if (!(UniversalMethods.isInteger(string = read()) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 4)) {
                         print("wrong input :(");
                         continue;
                     }
                     break;

                 }
                 if (string.equals("1")) {
                     print("enter ID and name for the instructor \"ID:Name");
                     Instructor instructor;
                     while (true) {
                         string = read();
                         if (!string.contains(":")) {
                             print("wrong input :(");
                             continue;
                         }
                         else if(!UniversalMethods.isInteger(string.split(":")[0])){
                             print("wrong input :(");
                             continue;
                         }
                         if ((instructor = instructors.searchForInstructor(Integer.parseInt(string.split(":")[0])))==null || !instructor.getName().equals(string.split(":")[1])) {
                             print("wrong input :(");
                             continue;
                         }
                         admin.deleteUserFromFile(instructor.getName(),false);
                          instructors.getInstructors().remove(instructor);
                         print("removed");
                         break;

                     }
                 }
                 else if (string.equals("2")) {
                     print("enter ID and name for the instructor and the new password \"ID:Name:password");
                     Instructor instructor;
                     while (true) {
                         string = read();
                         if (!string.contains(":")) {
                             print("wrong input :(");
                             continue;
                         } else if (!UniversalMethods.isInteger(string.split(":")[0])) {
                             print("wrong input :(");
                             continue;
                         }
                         if ((instructor = instructors.searchForInstructor(Integer.parseInt(string.split(":")[0])))==null || !instructor.getName().equals(string.split(":")[1])) {
                             print("wrong input :(");
                             continue;
                         }
                         print(string = admin.updateToInstructor(instructor.getName(), string.split(":")[2]));
                          if (!string.equals("UPDATE SUCCESS: Password was updated successfully")) continue;
                         break;

                     }
                 }
                 else if (string.equals("3")) {
                     continue;
                 }
             }
             if(string.equals("3")){
                 while (true) {
                     print("");
                     print("1-generate the report for the clients progress");
                     print("2-generate the report for the revenue");
                     print("3-go to home page");
                     string = read();
                     if (!(UniversalMethods.isInteger(string) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 4)) {
                         print("wrong input :(");
                         continue;
                     }
                     break;
                 }
                 if(string.equals("1")){
                     admin.GenerateReports(clients,programs,0,"src/main/resources/report.pdf");
                     print("generated in src/main/resources/report.pdf");
                 }
                 if(string.equals("2")){
                     admin.GenerateReports(clients,programs,1,"src/main/resources/report.pdf");
                     print("generated in src/main/resources/report.pdf");
                 }
                 if(string.equals("3")){
                     continue;
                 }

              }

             if(string.equals("4")){
                 while (true) {
                     print("enter the instructor name and password an in the format \"name:password\" ");
                     string = read();
                     if (!string.contains(":")) {
                         print("wrong input :(");
                         continue;
                     }


                     string = instructors.instructorSignup(string.split(":")[0], string.split(":")[1]);
                     if (string.equals("SIGNUP SUCCESSFUL: Welcome")) print("Instructor added successfully :)");
                     else {
                         print("wrong input :(");
                         continue;
                     }
                     break;
                 }
             }
             else if(string.equals("5")){
                 for(Program program:programs.getPrograms())print(program.getTitle());
                 print("");
                 while (true) {
                     print("");
                     print("1-view statistics on a program");
                     print("2-go to home page");

                     if (!(UniversalMethods.isInteger(string = read()) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 3)) {
                         print("wrong input :(");
                         continue;
                     }
                     break;

                 }
                 if (string.equals("1")) {
                     print("enter program title");
                     Program program;
                     while (true) {
                         string = read();

                         if( (program=programs.searchForProgram(string))==null ){
                             print("wrong input :(");
                             continue;
                         }
                         ProgramStatistics programStatistics=admin.ViewStatisticsOnAProgram(program.getTitle(),clients,programs);
                         for(ClientStatistics clientStatistics:programStatistics.getClientStatistics()){
                             print(clientStatistics.getClientID() + " : " + clientStatistics.getAttendance()+" : "+clientStatistics.getCompletion());
                         }
                         print(programStatistics.getMessage());
                         break;

                     }
                 }

                 else if (string.equals("2")) {
                     continue;
                 }

             }
             else if(string.equals("6")){
                 while (true) {
                     print("");
                     print("1-get feed back about enrollments");
                     print("2-get feed back about price");
                     print("3-go to home page");

                     if (!(UniversalMethods.isInteger(string = read()) && Integer.parseInt(string) > 0 && Integer.parseInt(string) < 4)) {
                         print("wrong input :(");
                         continue;
                     }
                     break;
                 }
                 if(string.equals("1")){
                     ArrayList<ProgramData> programData=new ArrayList<>();
                     string= admin.ViewTheMostPopularProgramsByEnrollment("0",programData,clients,programs);
                     for (ProgramData programData1:programData){
                         print(programData1.programTitle+" : "+programData1.value);
                     }
                     print(string);
                 }
                 if(string.equals("2")){
                     ArrayList<ProgramData> programData=new ArrayList<>();
                     string= admin.ViewTheMostPopularProgramsByEnrollment("1",programData,clients,programs);
                     for (ProgramData programData1:programData){
                         print(programData1.programTitle+" : "+programData1.value);
                     }
                     print(string);
                 }
                 if(string.equals("3")){
                   continue;
                 }

             }

         }




     }


}
