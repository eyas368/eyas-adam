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
        print("2-Instructor");
        print("3-Client");
        String now = read();
        if(now.equals("1"))AdminPart();
        else if (now.equals("3"))
            clientPart();


    }
    private static void clientPart() throws IOException {
         String now;
         print("Choose one of the following:\n" +
                 "1- Sign up\n" +
                 "2- Sign in");
         now = read();
         label:
         while (true) {
             switch (now) {
                 case "1", "2":
                     break label;
             }
             print("Enter valid input");
             now = read();
         }
         if (now.equals("1"))
             signUpMenu();
         else{
             clients.setCurrentMenu(Clients.CLIENT_LOGIN_CODE);
             signInMenu();
         }
    }

    private static void signUpMenu() throws IOException {
        String name = "";
        String password = "";
        String program = "";
        while(clients.getCurrentMenu() == Clients.CLIENT_LOGIN_CODE) {
            print("Enter Username:");
            name = read();
            print("Enter Password:");
            password = read();
            print("Enter Program:");
            program = read();
            print(clients.clientSignUp(name, password, program));
        }
        clients.addUserToFile(name, password);
        clients.clientLogin(name, password);
        profileMenu();
    }

    private static void signInMenu() throws IOException {
        String name = "";
        String password = "";
        while (clients.getCurrentMenu() == Clients.CLIENT_LOGIN_CODE){
            print("Enter '0' to go back");
            print("Enter Username:");
            name = read();
            print("Enter '0' to go back");
            print("Enter Password:");
            password = read();
            if (name.equals("0") || password.equals("0"))
                break;
            print(clients.clientLogin(name, password));
        }
        if (name.equals("0") || password.equals("0"))
            clientPart();
        profileMenu();
    }

    private static void profileMenu() throws IOException {
        String now = "";
        while (clients.getCurrentMenu() == Clients.CLIENT_PROFILE_CODE){
            print("Choose one of the following:\n" +
                    "0- Sign out\n" +
                    "1- Show/Edit personal info\n" +
                    "2- Show Programs");
            now = read();
            if(now.equals("0") || now.equals("1") || now.equals("2"))
                break;
        }
        if(now.equals("0")){
            clients.setCurrentMenu(1);
            signInMenu();
        }
        else if (now.equals("1"))
            clientPersonalInfoMenu();

        else
            clientProgramsMenu();
    }

    private static void clientPersonalInfoMenu() throws IOException {
        ArrayList <String> info = clients.getPersonalInfo();
        print("Username: " + clients.getActiveClient());
        print("Age: " + info.get(0));
        print("Current weight: " + info.get(1));
        print("Goal weight: " + info.get(4));
        print("Current BMI: " + info.get(2));
        print("Goal BMI: " + info.get(3));
        print("Preferences: " + info.get(5));
        print("Restrictions: " + info.get(6));
        print("\n");
        print("Choose one of the following:\n" +
                "0- Go back\n" +
                "1- Edit personal info");
        String now = read();
        while(!now.equals("0") && !now.equals("1")){
            print("Enter a valid input: ");
            now = read();
        }
        if(now.equals("0")){
            profileMenu();
        }
        else{
            clients.setCurrentMenu(Clients.CLIENT_INFO_CHANGE_CODE);
            clientEditInfoMenu();
        }
    }

    private static void clientProgramsMenu() throws IOException {
        printPrograms(clients.getPrograms(null, null, false));
        String now = "";
        while (true) {
            print("Enter 0 to back");
            print("Enter 1 for no filters");
            print("Enter 2 to enroll in a program");
            print("Enter 3 to rate in a completed program");
            print("Enter {level,focus} to filter by level and focus");
            print("(if you want to filter by level only, enter {level}, focus only, enter {,focus}");
            now = read();
            if (now.equals("0") || now.equals("2") || now.equals("3")){
                break;
            }
            if (now.equals("1")){
                printPrograms(clients.getPrograms(null, null, false));
                continue;
            }
            if (now.split(",").length == 1) {
                printPrograms(clients.getPrograms(now, null, false));
                break;
            }

            if (now.charAt(0) == ','){
                printPrograms(clients.getPrograms(null, now.substring(1), false));
                break;
            }
            String[] filters = now.split(",");
            printPrograms(clients.getPrograms(filters[0], filters[1], false));
            break;
        }
        if (now.equals("0")){
            profileMenu();
        }
        else if (now.equals("3")){
            clients.setCurrentMenu(Clients.CLIENT_REVIEW_CODE);
            clientProgramRate();
        }
        else if (now.equals("2")){
            clients.setCurrentMenu(Clients.CLIENT_PROGRAMS_JOIN_CODE);
            clientEnrollmentMenu();
        }
        print("Enter 1 to enroll in a program");
        print("Enter 2 to rate a completed program");
        print("Enter anything else to go back");
        now = read();
        if (now.equals("1")){
            clients.setCurrentMenu(Clients.CLIENT_PROGRAMS_JOIN_CODE);
            clientEnrollmentMenu();
        }
        if (now.equals("2")){
            clients.setCurrentMenu(Clients.CLIENT_REVIEW_CODE);
            clientProgramRate();
        }
        else
            profileMenu();
    }

    private static void clientProgramRate() throws IOException {
        String program = "";
        String rating = "";
        String review = "";
        String suggestion = "";
        while (clients.getCurrentMenu() != Clients.CLIENT_PROFILE_CODE){
            print("Enter the name of a program");
            print("Or enter 0 to go back");
            program = read();
            if(program.equals("0"))
                break;
            print("Enter rating:");
            rating = read();
            print("Enter a review:");
            review = read();
            print("Enter a suggestion: ");
            suggestion = read();
            print(clients.writeReview(program, rating, review, suggestion, false));
        }
            profileMenu();
    }

    private static void printPrograms(ArrayList<Program> programs){
        for(Program program: programs){
            print("Program name: " + program.getTitle());
            print("Duration: " + program.getDuration());
            print("Level: " + program.getLevel());
            print("Focus on: " + program.getFocus());
            print("Price: " + program.getPrice());
            print("");
        }
    }

    private static void clientEditInfoMenu() throws IOException {
        print("Enter the following info but first");
        print("Enter 0 to go back, anything else to continue");
        String age = "";
        String w = "";
        String bmi = "";
        String gbmi = "";
        String gw = "";
        String p = "";
        String r = "";
        while (clients.getCurrentMenu() != Clients.CLIENT_PROFILE_CODE) {
            if(read().equals("0"))
                break;
            print("Age: ");
            age = read();
            print("Current weight: ");
            w = read();
            print("Goal weight: ");
            gw = read();
            print("Current BMI: ");
            bmi = read();
            print("Goal BMI: ");
            gbmi = read();
            print("Preferences: ");
            p = read();
            print("Restrictions: ");
            r = read();
            print(clients.updateValues(age, w, bmi, gbmi, gw, p, r));
            print("Enter 0 to go back, anything else to continue");
        }
        profileMenu();

    }
    private static void clientEnrollmentMenu() throws IOException {
        print("Enter the name of the program you want to enroll in: ");
        String now = read();
        while(clients.getCurrentMenu() != Clients.CLIENT_PROFILE_CODE){
            print(clients.enrollInProgram(now));
        }
        profileMenu();
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
