public class ProgramData {
    public String programTitle;
    public int value;
    public ProgramData(String programTitle,int value){
        this.programTitle=programTitle;
        this.value=value;
    }
    @Override
    public boolean equals(Object programData1) {
        if (!(programData1 instanceof ProgramData)) {
            return false;
        }
        ProgramData programDatatemp = (ProgramData) programData1;
        return this.programTitle.equals(programDatatemp.programTitle) && this.value == programDatatemp.value;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
