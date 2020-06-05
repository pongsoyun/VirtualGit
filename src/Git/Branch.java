package Git;
import java.util.ArrayList;

import static Terminal.Color.*;

public class Branch extends Commit{
    private String name;
    ArrayList<Commit> commits = new ArrayList<Commit>();

    public Branch() {
    }

    public Branch(String name){
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


    /*-- function --*/
    // git log
    public void getCommitLog(){
        for(Commit commit : commits){
            System.out.println(commit.getTime());
            System.out.println(commit.getMsg()) ;
            System.out.println();
        }
    }


    // git commit "msg"
    public void setCommitLog(String commitMsg){
        int index = commits.size(); // index번째에 추가할거고

        // 커밋 새로 만들기
        Commit commit = new Commit();
        commit.setCommit(commitMsg);

        commits.add(commit); // 방금 셋팅한 커밋 배열에 추가
    }
}
