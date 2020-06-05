package Git;

import java.util.ArrayList;

import static Terminal.Color.*;

public class Branch extends Commit {
    private String name;
    ArrayList<Commit> commits = new ArrayList<Commit>();
    FileMgr fileMgr = new FileMgr();

    @Override
    public String toString() {
        return getClass().getName();
    }

    public Branch() {
    }

    public Branch(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    /*-- function --*/
    // git log
    public void getCommitLog() {
        for (Commit commit : commits) {
            System.out.println(commit.getTime());
            System.out.println(commit.getMsg());
            System.out.println();
        }
    }


    // git commit "msg"
    public void setCommitLog(String commitMsg) {
        int index = commits.size(); // index번째에 추가할거고

        // 커밋 새로 만들기
        Commit commit = new Commit();
        commit.setCommit(commitMsg);

        commits.add(commit); // 방금 셋팅한 커밋 배열에 추가

        // 커밋 후 modified -> staging not CHanged로 바꾸기
        // 1. 모든 Modified 찾기
        // 2. StagingNotChanged 로 변경
        fileMgr.commitFile();
    }

    // new fileName
    public void newFile(String name) {
        if (fileMgr.isExist(name)) {
            // 만약에 중복되는 파일명이 있다고 하면 -> 새로 못만든다고 하기
            System.out.println("✨new 실패! - 해당 파일명이 존재합니다. 다른 파일명을 입력하세요.");
        } else {
            // 만들기
            fileMgr.setFile(name); // new = untracked
        }


        // setFile(new Untracked());
    }

    // touch fileName
    public void editFileStatus(String name) {
        // 순회해서 해당하는애 수정하기
        if(fileMgr.isExist(name)){
            // StagingNotChanged -> Modified
            File file = fileMgr.searchFile(name); // 바꾸려는 파일 찾아서 넣어주기
            File newFile = fileMgr.touchFile(file);
            fileMgr.swapFile(file, newFile); // 나중에 @Override
        }else {
            System.out.println("✨touch 실패! - 해당 파일명이 존재하지 않습니다. 다른 파일명을 입력하세요.");
        }

    }

    // git status
    // getFiles() 에서 정렬해서 보여줘야할거같음
    public void getStatus(){
        fileMgr.getFiles();
    }

    // git add fileName
    public void setAdd(String name){
        if(fileMgr.isExist(name)){
            // Untracked -> StagingNotChanged
            File file = fileMgr.searchFile(name);
            File newFile = fileMgr.addFile(file);
            fileMgr.swapFile(file, newFile);
        }else {
            System.out.println("✨add 실패! - 해당 파일명이 존재하지 않습니다. 다른 파일명을 입력하세요.");
        }
    }

    // git add .
    public void setAddAll(){
        // Untracked 모두 찾아서 setAdd 해줘야함
    }
}
