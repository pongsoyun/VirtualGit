package Terminal;

import Git.*;

import java.util.ArrayList;
import java.util.Scanner;

import static Terminal.Color.*;
/*

 - Terminal 의 기본 메서드를 가지고 있음
 - git의 기능을 사용하여 cli에서 git 제어가 가능하도록 함
 -

 */


public class Terminal extends Git {
    public static Scanner scanner = new Scanner(System.in);
    public String input; // User input
    public String[] inputArr; // User input tokenizer
    public boolean isExit; // exit(1)
    public String prompt; // prompt 종류 뭐로할건지. git 일지 그냥 terminal일지 -> 여기서 관리해야함
//    super것 사용 : Repos, curRepoIndex

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
                        if (checkoutRepo(inputArr[1]))
                            prompt = setGitPrompt(prompt);
                    }
                    break;
                case "touch":
                    // > touch ${fileName} : 파일 수정했다고 해줘야함
                    // 현재repo.현재branch.fileName과 같은거 찾아서 modified로 상태 바꾸기(new Modified()); 로
                    if (!prompt.equals("> ")) {
                        if (inputArr.length == 2) {
                            setTouchFile(inputArr[1]);
                        } else {
                            System.out.println("✨혹시 touch fileName을 쓰려고 하지않았나요? param이 2개라구요!");
                        }
                    }else {
                        System.out.println("✨이런 명령어 없어. ( 사실 지금 못써 ) ");
                    }
                    break;
                case "new":
                    // new ${fileName}
                    if (!prompt.equals("> ")) {
                        // git 상태 아닌경우에는 못쓰는 명령어
                        if (inputArr.length == 2)
                            setNewFile(inputArr[1]);
                        else
                            System.out.println("✨혹시 new fileName을 쓰려고 하지않았나요? param이 2개라구요!");
                    } else {
                        System.out.println("✨이런 명령어 없어. ( 사실 지금 못써 ) ");
                    }
                    break;
                case "git":
                    if (inputArr.length == 2) {
                        switch (inputArr[1]) {
                            case "branch":
                                getBranchList();
                                break;
                            case "log":
                                getLog();
                                break;
                            case "status":
                                getBranchStatus();
                                break;
                            case "push":
                                gitPush();
                                break;
                            default:
                                break;
                        }
                    } else if (inputArr.length == 3) {
                        switch (inputArr[1]) {
                            case "init":
                                // git init ${repoName}
                                init(inputArr[2]);
                                break;
                            case "branch":
                                // git branch ${branchName}
                                newBranch(inputArr[2]);
                                break;
                            case "checkout":
                                // git checkout ${branchName}
                                checkout(inputArr[2]);
                                prompt = setGitPrompt(prompt);
                                break;
                            case "commit":
                                // git commit "${commitMsg}"
                                // String.substring(3,6) :앞에서 4~6까지만 갖기
                                // 끝까지 순회하면서 없을때까지 String에 더해서
                                if (inputArr[2].charAt(0) == '\"' && inputArr[2].charAt(inputArr[2].length() - 1) == '\"') {
                                    // 맨마지막 확인해야함 !! -1 되는ㄷ지 🔥
                                    String commitMsg;
                                    commitMsg = inputArr[2].substring(1, inputArr[2].length() - 1); // ""자르기
                                    commit(commitMsg);
                                } else {
                                    System.out.println("너 commit 하고싶은거냐? 따옴표 제대로써 임마");
                                }
                                break;
                            case "add":
                                // git add fileName
                                gitAdd(inputArr[2]);
                                break;
                            default:
                                break;
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
