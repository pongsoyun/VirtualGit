package Git;

public class Repository {
    private String repoName;
    // RepoName으로 생성

    // default 생성
    public Repository() {

    }
    public Repository(String repoName){
        setRepoName();
    }

    private void setRepoName() {
        this.repoName = repoName;
        System.out.println("repoName : " + repoName + " 이 생성되었습니다!");
    }

    public String getRepoName(){
        return repoName;
    }
}

