package Git;

import Terminal.FileIOMgr;

import java.util.ArrayList;
import static Terminal.Color.*;
import static Terminal.FileIOMgr.*;

public class Git  extends Repository{
    public ArrayList<Repository> repos = new ArrayList<>(); // 여기서 생성하고, 사용
    public int curRepoIndex; // 현재 몇번쨰 repos[] 의 인덱스인지

    @Override
    public String toString() {
        return "Current Repository >> origin "  + ANSI_BLUE
                + repos.get(curRepoIndex).getRepoName() + " " + ANSI_RESET + "(fetch)";
    }

    public Git() {
        curRepoIndex = -1;
    }

    public void setNewRepo(String repoName) {
        Repository newRepo = new Repository(repoName); // repoName을 가진 새로운 레포 생성
        repos.add(newRepo);
    }

    public void init(String newRepoName){
        curRepoIndex++;
        setNewRepo(newRepoName); // 해당 이름으로 레포 추가함
    }

    // zsh 에서 ls
    public String getRepos() {
        StringBuffer result = new StringBuffer();
        for(Repository repo: repos){
            result.append(repo.getRepoName()+"\t");
        }
        return result.toString();
    }

    // 현재 레포이름과 같은애 찾아서 currentRepoIndex 바꿔주기
    // return : 현재 몇번째로 할지 리턴하기
    public boolean checkoutRepo(String repoName) {
        int i =0;
        boolean isExist = false; // true : 성공! false : 실패
        for (Repository repo : repos) {
            if (repo.getRepoName().equals(repoName)) {
                // 현재 init 한 애는 얘다
                curRepoIndex = i;
                isExist = true;
                break;
            }
            i++;
        }
        if(isExist)
            System.out.println("✨Current Repository : "+ANSI_CYAN + repos.get(curRepoIndex).getRepoName()+ANSI_RESET);
        else
            System.out.println(ANSI_RED+"The repository does not exist."+ANSI_RESET);

        return isExist;
    }

    // git branch ${branchName}
    public void newBranch(String branchName){
        repos.get(curRepoIndex).setBranch(branchName);
    }

    // git checkout ${branchName}
    public void checkout(String branchName){
        repos.get(curRepoIndex).checkoutBranch(branchName);
    }

    // git branch
    public void getBranchList(){
        repos.get(curRepoIndex).getBranches();
    }

    // git log
    public void getLog() {
        Branch branch = repos.get(curRepoIndex).getBranch();
        System.out.println(branch.getCommitLog());
    }

    // git remote
    public void gitRemote(){
        System.out.println(this.toString());
    }

    // git push
    public void gitPush() {
        Branch branch = repos.get(curRepoIndex).getBranch();
        String contents = branch.getCommitLog();
        // 폴더 : 레포 이름, 파일 : 레포_branch 이름
        makeFile(repos.get(curRepoIndex).getRepoName(), repos.get(curRepoIndex).getBranchName(), contents);
        branch.setPush();
    }

    // git commit "msg"
    public void commit(String commitMsg) {
        Branch branch = repos.get(curRepoIndex).getBranch();
        branch.setCommitLog(commitMsg);
    }

    // new fileName
    public void setNewFile(String name) {
        Branch branch = repos.get(curRepoIndex).getBranch();
        branch.newFile(name);
    }

    // touch fileName
    public void setTouchFile(String name){
        Branch branch = repos.get(curRepoIndex).getBranch();
        branch.editFileStatus(name);
    }

    // git status
    public void getBranchStatus(){
        Branch branch = repos.get(curRepoIndex).getBranch();
        branch.getStatus();
    }

    // git add fileName, git add .
    public void gitAdd(String name){
        Branch branch = repos.get(curRepoIndex).getBranch();
        if(name.equals(".")){
            branch.setAddAll();
        }else{
            branch.setAdd(name);
        }

    }

    // prompt 셋팅
    public String setGitPrompt(String prompt) {
        return repos.get(curRepoIndex).getRepoName() + "/" + repos.get(curRepoIndex).getBranchName() + "> ";
    }


    // git help
    public void gitHelp() {
        FileIOMgr.readFile();
        // 파일읽기?
//        System.out.print("✋\uD83C\uDFFB Hi! This is Virtual git Program ✋\uD83C\uDFFB\n" +
//                "Author : SoYun Bang, School Of Global Media, in Soongsil Univ.\n" +
//                "AuthorID : 20162574\n" +
//                "Contact : thdbstjdud@gmail.com\n" +
//                "Project Name : OOP Term Project\n" +
//                "Project Subject : Virtual Git Program\n" +
//                "\n" +
//                "✨<- gitmoji :sparkles: - Additional Log. 실제로는 해당 Line이 등장하지 않지만, 반응을 즉각 보이기 위한 event Log.\n" +
//                "\n" +
//                "These are common Git commands used in various situations:\n" +
//                "\n" +
//                "start a working area\n" +
//                "[ONLY in Terminal]\n" +
//                "pwd          terminal running...\n" +
//                "exit          program 종료\n" +
//                "cd ${repoName}              ${repoName}에 해당하는 repository로 접근하며, git 작업을 할 수 있음\n" +
//                "\n" +
//                "[ONLY in Git]\n" +
//                "pwd          git running...\n" +
//                "cd          해당 repository에서 빠져나와 git 작업을 할수 없음\n" +
//                "touch ${fileName}           ${fileName}파일을 수정한다는 의미\n" +
//                "new ${fileName}           ${fileName}파일을 생성. 이미 존재한다면 생성하지않음을 알리고 재입력 받음\n" +
//                "git checkout ${branchName}           ${branchName}의 브랜치로 checkout함. 없다면 재입력 받음\n" +
//                "git branch          현재 repository가 보유하고있는 branch의 목록을 리스트업. 현재 체크아웃한 브랜치 앞에는 * 가 붙음\n" +
//                "git add ${fileName}          아직 Staging되지 않은 상태의 ${fileName}을 Staging함\n" +
//                "git add .          아직 Staging 되지 않은 상태의 모든 파일들을 Staging함\n" +
//                "git commit ${commitMSG}          ${commitMSG}의 내용으로 현재 Tracked 중인(Staging된 상태의) 파일들을 Commit함\n" +
//                "git push           현재까지의 커밋로그를 ${repoName}의 폴더 안에 remote_${branchName}.txt 파일에 작성하여 저장. 현재 체크아웃한 브랜치의 것을 푸쉬함\n" +
//                "git status           현재 파일들의 상태를 보여줌. \n" +
//                "git log           해당 branch의 현재까지의 커밋로그를 보여줌. commitID는 random으로 임의로 받음 \n" +
//                " \n" +
//                " \n" +
//                "[About File]\n" +
//                "StagingNotChanged           이전 Push 이후로 변화가 생긴 파일. NOTCHANGED, MODIFIED 상태가 해당될 것이며 NOTCHANGED는 실제 깃에서처럼 보이지 않게 설정하였음(푸쉬 이후에 변화한 파일만 표시)  \n" +
//                "OnlyStaging           이전 Commit 이후로 변화가 생긴 파일. NEWFILE, MODIFIED 상태가 해당됨  \n" +
//                "Untracked           새롭게 new 생성된 파일. NEWFILE 상태만 해당하므로, 파일의 Status를 보여주지 않음\n" +
//                "\n" +
//                "[About Status]\n" +
//                "NOTCHANGED           이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 X\n" +
//                "MODIFIED           이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 O\n" +
//                "NEWFILE           이전에 commit 한 히스토리 X, 새로 add 된 파일 \n" +
//                "\n" +
//                "\n" +
//                "🥰 Virtual Git ");
    }
}
