package Git;

import java.util.ArrayList;
import static Terminal.Color.*;

public class Repository extends Branch {
    private String repoName;
    private int curBranchIndex;
    private ArrayList<Branch> branches = new ArrayList<>();

    @Override
    public String toString() {
        return "origin "  + ANSI_BLUE + repoName + " " + ANSI_RESET + "(fetch)";
    }

    // default 생성
    public Repository() {
        curBranchIndex = -1;
    }

    public Repository(String repoName) {
        setRepoName(repoName);
        branches.add(new Branch("master"));
        // branchIndex ++ ;
    }

    private void setRepoName(String repoName) {
        this.repoName = repoName;
        System.out.println(ANSI_CYAN+"✨" + this.repoName+ANSI_RESET + " Repository has been created!!");
    }

    public String getRepoName() {
        return repoName;
    }

    // branch ${branchName} 했을떄 호출되는 함수
    public void setBranch(String branchName) {
        branches.add(new Branch(branchName));
        System.out.println(ANSI_CYAN+"✨" + branchName +ANSI_RESET+ " branch가 생성되었습니다!");
    }

    public void getBranches() {
        for (Branch branch : branches) {
            if (branch.getName().equals(branches.get(curBranchIndex).getName())) {
                // 현재 브랜치일경우
                System.out.print("* ");
                System.out.println(ANSI_GREEN+branch.getName()+ANSI_RESET);
            }else {

                System.out.println(branch.getName());
            }
        }
    }

    public Branch getBranch() {
        return branches.get(curBranchIndex);
    }

    // 현재 브랜치 이름 리턴
    public String getBranchName() {
        return branches.get(curBranchIndex).getName();
    }

    // 현재 브랜치와 같은거 리턴
    public void checkoutBranch(String branchName) {
        int i = 0;
        for (Branch branch : branches) {
            if (branch.getName().equals(branchName)) {
                // 현재 init 한 애는 얘다
                curBranchIndex = i;
                break;
            }
            i++;
        }
    }

}

