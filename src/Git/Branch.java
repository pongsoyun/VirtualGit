package Git;

import java.util.ArrayList;

import static Terminal.Color.*;

public class Branch extends Commit {
    private String name;
    ArrayList<Commit> commits = new ArrayList<Commit>();
    FileMgr fileMgr = new FileMgr();
    int commitCnt = 0;

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


    // git log
    public String getCommitLog() {
        StringBuffer str = new StringBuffer();
        if(commits.size() == 0){
            System.out.println("nothing to commit, working tree clean! " +
                    ANSI_BLUE+"🎉Let's First Commit!" + ANSI_RESET); // 완전 클린! 첫 커밋 해달라할떄
        }
        for (Commit commit : commits) {
            str.append(ANSI_YELLOW+"commit "+(int)(Math.random()*1000000)+ANSI_RESET+"\n"); // fake commit ID
            str.append("Author: pongsoyun <thdbstjdud@gmail.com>"); // fake Author ID.
            str.append("Date:\t"+commit.getTime()+"\n"); //
            str.append("\n\t"+commit.getMsg()+"\n\n");
            str.append(commit.getFileLog()+"\n");
            str.append("\n");
        }
        return str.toString();
    }


    // git commit "msg"
    public void setCommitLog(String commitMsg) {
        if(fileMgr.getSnapShot().length()!=0){
            // 커밋 새로 만들기
            Commit commit = new Commit();
            fileMgr.updateStagingNotChanged();
            fileMgr.updateOnlyStaging();
            fileMgr.updateUntracked();

            commit.setCommit(commitMsg);
            commit.setFileLog(fileMgr.getSnapShot()); // fileLog셋팅
            commits.add(commit); // 방금 셋팅한 커밋 배열에 추가

            fileMgr.commitFile();
            commitCnt++;
        }else {
            System.out.println("nothing to commit, working tree clean! "); // 할 커밋이 없어요
        }
        fileMgr.updateStagingNotChanged();
        fileMgr.updateOnlyStaging();
        fileMgr.updateUntracked();
    }

    // new fileName
    public void newFile(String name) {
        if (fileMgr.isExist(name)) {
            System.out.println(ANSI_RED+"✨new Failed!"+ANSI_RESET+ " - The file name already exists. Please enter a different filename."); // 파일존재합니다ㅠ
        } else {
            fileMgr.setFile(name); // new = untracked(만들기)
            System.out.println("🎉new file "+ANSI_YELLOW+name+ANSI_RESET+" SUCCESS!");
        }
    }

    // touch fileName
    public void editFileStatus(String name) {
        // 순회해서 해당하는애 수정하기
        if (fileMgr.isExist(name) ) {
            File file = fileMgr.searchFile(name); // 바꾸려는 파일 찾아서 넣어주기 : StagingNotChanged -> Modified
            File newFile = fileMgr.touchFile(file);
            fileMgr.swapFile(file, newFile);
            fileMgr.updateStagingNotChanged();
            fileMgr.updateOnlyStaging();
            fileMgr.updateUntracked();
            System.out.println("🎉EDIT file "+ANSI_YELLOW+name+ANSI_RESET+" !");
        } else {
            System.out.println(ANSI_RED+"✨touch Failed!"+ANSI_RESET+ " - The file name does not exist. Please enter a valid file name."); // 그런파일없슴니다ㅠ
        }
    }

    // git status
    public void getStatus() {
    // getFiles() 에서 정렬해서 뿌려줌
        if(!fileMgr.isSnapshotsExist()){
            // add도 없고, touch도없고, commit도 없을경우 (commitCnt랑은 노상관) - snapshot이 없으면 더이상 커밋할게 없다는 뜻
            System.out.println("On branch "+getName());
            System.out.println("Your branch is ahead of 'origin/" + getName() + "' by " + commitCnt + " commits.");
            System.out.println("(use \"git push\" to publish your local commits");
            System.out.println("\nnothing to commit, working tree clean");
        }else if(commitCnt>0){
            System.out.println("On branch " + getName());
            System.out.println("Your branch is ahead of 'origin/" + getName() + "' by " + commitCnt + " commits.");
            System.out.println("(use \"git push\" to publish your local commits");
        }
        fileMgr.getFiles();
    }

    // git add fileName
    public void setAdd(String name) {
        if (fileMgr.isExist(name)) {
            File file = fileMgr.searchFile(name);
            File newFile = fileMgr.addFile(file);
            fileMgr.swapFile(file, newFile); // Untracked -> OnlyStaging
        } else {
            System.out.println(ANSI_RED+"✨add Failed!"+ANSI_RESET+ " - The file name does not exist. Please enter a valid file name."); // 그런파일없슴니다ㅠ
        }
        fileMgr.updateStagingNotChanged();
        fileMgr.updateOnlyStaging();
        fileMgr.updateUntracked();
    }

    // git add .
    public void setAddAll() {
        // Untracked 모두 찾아서 setAdd 해줘야함
        ArrayList<String> untrackedName;
        untrackedName = fileMgr.searchUntrackedFiles();
        for(String str : untrackedName){
            setAdd(str);
        }
        fileMgr.updateStagingNotChanged();
        fileMgr.updateOnlyStaging();
        fileMgr.updateUntracked();
    }

    // git push
    public void setPush() {
        // push : snapshotsBefore.setLength(0);
        // commit : snapshotsBefore = snapshot;
        fileMgr.snapshotsBefore.setLength(0);
        fileMgr.snapshotsBefore = fileMgr.snapshots;

        System.out.println(fileMgr.snapshotsBefore);
        System.out.println();
        System.out.println(fileMgr.snapshots);

    }
}