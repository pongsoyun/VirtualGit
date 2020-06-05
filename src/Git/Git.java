package Git;

import java.util.ArrayList;

public class Git  extends Repository{
    public ArrayList<Repository> repos = new ArrayList<>(); // 여기서 생성하고, 사용
    public int curRepoIndex; // 현재 몇번쨰 repos[] 의 인덱스인지

    public Git() {
        curRepoIndex = -1;
    }

    //////////////////////////////////////////////////////////////////////////////
    /*-- GIT 명령어 --*/
    public void setNewRepo(String repoName) {
        Repository newRepo = new Repository(repoName); // repoName을 가진 새로운 레포 생성
        repos.add(newRepo);
    }

    public void init(String newRepoName){
        curRepoIndex++;
        setNewRepo(newRepoName); // 해당 이름으로 레포 추가함
    }

    // 현재 레포이름과 같은애 찾아서 currentRepoIndex 바꿔주기
    // return : 현재 몇번째로 할지 리턴하기
    public void checkoutRepo(String repoName) {
        int i =0;
        for (Repository repo : repos) {
            if (repo.getRepoName().equals(repoName)) {
                // 현재 init 한 애는 얘다
                curRepoIndex = i;
                break;
            }
            i++;
        }
        System.out.println("현재 init한 애는 " + curRepoIndex + " 에 있는 " + repos.get(curRepoIndex).getRepoName()); //아 repos[currentRepoIndex].getRepoName()이 이렇게 되나봐
    }

    // git branch ${branchName}
    public void newBranch(String branchName){
        repos.get(curRepoIndex).setBranch(branchName);
    }

    // git checkout ${branchName}
    public void checkout(String branchName){
        repos.get(curRepoIndex).checkoutBranch(branchName);
    }


    //////////////////////////////////////////////////////////////////////////////
    /*-- prompt 셋팅 --*/
    // Terminal의 prompt를 바꾸는 부분
    public String setGitPrompt(String prompt) {
        System.out.println("=======>" + curRepoIndex);

        return repos.get(curRepoIndex).getRepoName() + "/" + repos.get(curRepoIndex).getBranchName() + "> ";
    }

}
