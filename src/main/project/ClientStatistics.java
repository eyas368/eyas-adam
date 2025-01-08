public class ClientStatistics {
    private int clientID;
    private int completion;
    private int attendance;
    public ClientStatistics(int clientID,int completion,int attendance){
        this.attendance=attendance;
        this.completion=completion;
        this.clientID=clientID;
    }
    @Override
    public boolean equals(Object o){
        if(this.clientID!= ( (ClientStatistics)o).clientID)return false;
        if(this.completion!= ( (ClientStatistics)o).completion)return false;
        if(this.attendance!= ( (ClientStatistics)o).attendance)return false;

        return true;
    }
    public int getClientID(){return clientID;}
    public int getCompletion(){return completion;}
    public int getAttendance(){return attendance;}

}
