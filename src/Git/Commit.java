package Git;

import java.util.Calendar;

public class Commit {
    String msg;
    String time;
    String fileLog;

    public Commit() {
        // Default
    }

    public void setCommit(String msg){
        setMsg(msg);
        setTime();
        System.out.println("✨SUCCESS commit add! : "+this.msg);
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    private void setTime() {
        Calendar time = Calendar.getInstance();
        this.time = time.getTime().toString();
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return this.time;
    }

    public void setFileLog(String snapshots) {
        this.fileLog = snapshots;
    }

    public String getFileLog(){
        return this.fileLog;
    }

}
