package Git;

import java.util.ArrayList;
import static Terminal.Color.*;
import static Terminal.FileIOMgr.*;

public class Git  extends Repository{
    public ArrayList<Repository> repos = new ArrayList<>(); // 여기서 생성하고, 사용
    public int curRepoIndex; // 현재 몇번쨰 repos[] 의 인덱스인지
    public FileMgr fileMgr = new FileMgr();

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

}
