package Git;

import java.util.ArrayList;

public class Repository {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    private String repoName;
    private int curBranchIndex;
    private ArrayList<Branch> branches = new ArrayList<>();
    // RepoName으로 생성

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
        System.out.println("✨" + this.repoName + " 레포가 생성되었습니다!");
    }

    public String getRepoName() {
        return repoName;
    }

    // branch ${branchName} 했을떄 호출되는 함수
    public void setBranch(String branchName) {
        branches.add(new Branch(branchName));
        System.out.println("✨" + branchName + " branch가 생성되었습니다!");
        System.out.println("--------branch size : " + branches.size());
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

    // 현재 브랜치 이름 리턴
    public String getBranchName() {
        System.out.println("branch index : " + curBranchIndex);
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
        System.out.println("현재 init한 애는 " + curBranchIndex + " 에 있는 " + branches.get(curBranchIndex).getName());
    }

}

