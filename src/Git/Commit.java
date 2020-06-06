package Git;

import java.time.LocalDateTime;

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
        LocalDateTime timePoint = LocalDateTime.now();
        int month = timePoint.getMonthValue();
        int day = timePoint.getDayOfMonth();
        int hour = timePoint.getHour();
        int minute = timePoint.getMinute();
        int second = timePoint.getSecond();

        String time = month + "/" + day + "  " + hour + ":" + minute + ":" + second;
        this.time = time;
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
