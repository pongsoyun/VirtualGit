package Git;

import java.util.ArrayList;

public class Repository {
    private String repoName;
    private int curBranchIndex;
    private ArrayList<Branch> branches = new ArrayList<>();
    // RepoName으로 생성

    // default 생성
    public Repository() {

    }

    public Repository(String repoName){
        curBranchIndex = -1;
        setRepoName(repoName);
        branches.add(new Branch("master"));
    }

    private void setRepoName(String repoName) {
        curBranchIndex++;
        this.repoName = repoName;
        System.out.println("✨" + this.repoName + " 레포가 생성되었습니다!");
    }

    public String getRepoName(){
        return repoName;
    }

    public String curBranchName() {
//        System.out.println(branches.size());
        return branches.get(curBranchIndex).getName();
    }


}

