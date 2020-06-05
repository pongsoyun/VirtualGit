package Terminal;

import Git.Repository;

import java.util.ArrayList;
import java.util.Scanner;
/*

 - Terminal 의 기본 메서드를 가지고 있음
 - git의 기능을 사용하여 cli에서 git 제어가 가능하도록 함
 -

 */


public class Terminal extends Repository {
    public static Scanner scanner = new Scanner(System.in);
    public String input;
    public String[] inputArr;
    public boolean isExit;
    public String prompt; // prompt 종류 뭐로할건지. git 일지 그냥 terminal일지
    public ArrayList<Repository> repos = new ArrayList<>();
    public int curRepoIndex; // 현재 몇번쨰 repos[] 의 인덱스인지

    // 삽입 - repos.add(Repository형 객체);
    // 순회 - for each 사용
    // 크기확인 - repos.size()
    // 제거 - repos.remove(index);

    // 새로운 터미널 생성
    public Terminal() {
        // 초기화
        input = "";
        isExit = false;
        prompt = "> ";
        curRepoIndex = 0;
    }

    public void setNewRepo(String repoName) {
        Repository newRepo = new Repository(repoName); // repoName을 가진 새로운 레포 생성
        repos.add(newRepo);
    }

    public void checkoutRepo(String repoName) {
        // 현재 레포이름과 같은애 찾아서 currentRepoIndex 바꿔주기
        int i = 0;
        for (Repository repo : repos) {
            if (repo.getRepoName() == repoName) {
                // 현재 init 한 애는 얘다
                curRepoIndex = i;
            }
            i++;
        }
        System.out.println("현재 init한 애는 " + curRepoIndex + " 에 있는 " + repos.get(curRepoIndex).getRepoName()); //아 repos[currentRepoIndex].getRepoName()이 이렇게 되나봐
    }

    public void setGitPrompt() {
        prompt = repos.get(curRepoIndex).getRepoName() + "/" + repos.get(curRepoIndex).curBranchName() + "> ";
    }

    public void run() {
        // 터미널을 계속 실행하기 위함. exit이 나오기 전까지 계속 실행
        do {
            // 계속 실행함. exit이 되기 전까지
            System.out.print(prompt);
            input = scanner.nextLine(); // 다음에 들어온 명령어
            // inputArr로 잘라 넣기
            inputArr = input.split(" ");
            System.out.print("🔥" + inputArr.length);
//            이떄 애기들 개행문자까지 들어간다
//            for (int i = 0; i < inputArr.length; i++) {
//                System.out.println(i + " " + inputArr);
//            }

            // 여기서 명령어에 맞게 분배해주어야함
            // 첫번째꺼로 판단함
            switch (inputArr[0]) {
                case "exit":
                    isExit = true;
                    break;
                case "pwd":
                    if (prompt == "> ") {
                        System.out.println("✨terminal running...");
                    } else {
                        System.out.println("✨git running...");
                    }
                    break;
                case "cd":
                    // RepoName 을 잘라야함
                    if (inputArr.length == 1) {
                        // cd만 있을 경우 : git에서 나와야함
                        System.out.println("✨bye git");
                        prompt = "> ";
                    } else if (inputArr.length == 2) {
                        // cd Reponame -> remote/branch > 로 해야함
                        checkoutRepo(inputArr[1]);
                        setGitPrompt();
                    }
                    System.out.println("input : " + input);
                    break;
                case "touch":
                    // > touch ${fileName} : 파일 수정했다고 해줘야함
                    // 현재repo.현재branch.fileName과 같은거 찾아서 modified로 상태 바꾸기(new Modified()); 로
                    System.out.println("✨파일이 수정되었습니다");
                    break;

                case "git":
                    if (inputArr.length == 2) {
                        prompt = "git > "; // 여기 원래 레포이름/브랜치 넣어야함 // 그럼 함수에서 호출해야할거같은데 ?
                    } else if (inputArr.length == 3) {
                        // git init ${repoName}
                        if (inputArr[1].equals("init")) {
                            String newRepoName = inputArr[2];
                            setNewRepo(newRepoName); // 해당 이름으로 레포 추가함
                        }

                    }



                default:
//                    // 해당 명령이 없는 경우
//                    System.out.println("✨command not found: " + input);
//                    break;
            }

        } while (!isExit);
    }

}
