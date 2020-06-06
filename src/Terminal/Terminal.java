package Terminal;

import Git.*;

import java.util.ArrayList;
import java.util.Scanner;

import static Terminal.Color.*;
/*
 - Terminal 의 기본 메서드를 가지고 있음
 - git의 기능을 사용하여 cli에서 git 제어가 가능하도록 함
 */

public class Terminal extends Git {
    public static Scanner scanner = new Scanner(System.in);
    public String input; // User input
    public String[] inputArr; // User input tokenizer
    public boolean isExit; // exit(1)
    public String prompt; // prompt 종류 뭐로할건지. git 일지 그냥 terminal일지 -> 여기서 관리해야함

    @Override
    public String toString() {
        // 해당 잘못된 사용자 입력을 넣어 알려줌
        String str = "";
        for (int j = 0; j < inputArr.length; j++) {
            str += inputArr[j];
            str += " ";
        }
        return str;
    }

    // 새로운 터미널 생성, 초기화
    public Terminal() {
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
            inputArr = input.split(" ");
            boolean isExist = false;

            switch (inputArr[0]) {
                case "exit":
                    System.out.println("✨bye zsh...");
                    isExit = true;
                    break;
                case "ls":
                    // git에서 사용시 파일 목록, zsh에서 사용시 레포 목록
                     isExist = true;
                     if(inputArr.length==1){
                         if(prompt == "> "){
                             if(getRepos().length()==0){
                                 System.out.println("None git Repository. Let's " + ANSI_BLUE + "git init REPOSITORYNAME "+ANSI_RESET + "!");
                             }else {
                                 System.out.println(getRepos());
                             }
                         }
                         else
                         {
                             if(getLS().length() ==0)
                             {
                                 System.out.println("None files. Let's " + ANSI_BLUE + "new FILENAME"+ANSI_RESET + "!");
                             }else{
                                 System.out.println(getLS());
                             }
                         }
                     }else {
                         System.out.println("zsh: \'" + this.toString() + "\' is not a zsh command.\n\n" +
                                 "The most similar command is\n\t\t" + ANSI_YELLOW +
                                 "ls" + ANSI_RESET);
                     }
                     break;
                case "pwd":
                    // 현재 git init하여 사용하고있는 상태인지 알려줌
                    isExist = true;
                    if (prompt == "> ") {
                        System.out.println("✨terminal running...");
                    } else {
                        System.out.println("✨git running...");
                    }
                    break;
                case "cd":
                    isExist = true;
                    if (inputArr.length == 1) {
                        // cd만 있을 경우 : git에서 나와야함
                        System.out.println("✨bye git");
                        prompt = "> ";
                    } else if (inputArr.length == 2) {
                        // cd Reponame -> remote/branch > 로 변경하고, 해당 레포로 이동
                        if (checkoutRepo(inputArr[1]))
                            prompt = setGitPrompt(prompt);
                    }
                    break;
                case "touch":
                    isExist = true;
                    //  touch ${fileName} : 파일 수정했다고 해줘야함
                    if (!prompt.equals("> ")) {
                        if (inputArr.length == 2) {
                            setTouchFile(inputArr[1]);
                        } else {
                            System.out.println("zsh: \'" + this.toString() + "\' is not a zsh command.\n\n" +
                                    "The most similar command is\n\t\t" + ANSI_YELLOW +
                                    "touch FILENAME" + ANSI_RESET);
                        }
                    } else {
                        System.out.println("zsh: here is not allowed. ONLY in " + ANSI_CYAN + "running git" + ANSI_RESET);
                    }
                    break;
                case "new":
                    isExist = true;
                    // new ${fileName}
                    if (!prompt.equals("> ")) {
                        if (inputArr.length == 2)
                            setNewFile(inputArr[1]);
                        else
                            System.out.println("zsh: \'" + this.toString() + "\' is not a zsh command.\n\n" +
                                    "The most similar command is\n\t\t" + ANSI_YELLOW +
                                    "new FILENAME" + ANSI_RESET);
                    } else {
                        System.out.println("zsh: here is not allowed. ONLY in " + ANSI_CYAN + "running git" + ANSI_RESET);
                    }
                    break;

                ////////////////////////git 명령어 ////////////////////
                case "git":
                    if (prompt.equals("> ")) {
                        if (inputArr[1].equals("init")) {
                            isExist = true;
                            // git init repo
                            init(inputArr[2]);
                            break;
                        } else {
                            isExist = true;
                            System.out.println("✨zsh: command not found: " + ANSI_CYAN + this.toString() + ANSI_RESET);
                            break;

                        }
                        // git이 아니면 사용불가
                    }
                    switch (inputArr[1]) {
                        case "help":
                            // git help
                            isExist = true;
                            if(inputArr.length == 2){
                                gitHelp();
                            }else {
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git help" + ANSI_RESET);
                            }
                            break;
                        case "branch":
                            // git branch ${branchName}
                            isExist = true;
                            if (inputArr.length == 3)
                                newBranch(inputArr[2]);
                            else if (inputArr.length == 2) {
                                getBranchList();
                            } else {
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git branch BRANCHNAME" + ANSI_RESET);
                            }
                            break;
                        case "checkout":
                            // git checkout ${branchName}
                            isExist = true;
                            if (inputArr.length == 3) {
                                checkout(inputArr[2]);
                                prompt = setGitPrompt(prompt);
                            } else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git checkout BRANCHNAME" + ANSI_RESET);
                            break;
                        case "commit":
                            // git commit "${commitMsg}"
                            isExist = true;
                            if (inputArr.length == 3) {
                                if (inputArr[2].charAt(0) == '\"' && inputArr[2].charAt(inputArr[2].length() - 1) == '\"') {
                                    // 맨마지막 확인해야함 !! -1 되는ㄷ지 🔥
                                    String commitMsg = "";
                                    commitMsg = inputArr[2].substring(1, inputArr[2].length() - 1); // ""자르기
                                    commit(commitMsg);
                                } else {
                                    System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                            "The most similar command is\n\t\t" + ANSI_YELLOW +
                                            "git commit \"COMMITMESSAGE\"" + ANSI_RESET);
                                }

                            } else if (inputArr.length > 3) {
                                String commitMsg = "";
                                for (int j = 2; j < inputArr.length; j++) {
                                    commitMsg += inputArr[j];
                                    commitMsg += " ";
                                }
                                commitMsg = commitMsg.substring(1, commitMsg.length() - 2); // ""자르기
                                commit(commitMsg);
                            } else {
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git commit \"COMMITMESSAGE\"" + ANSI_RESET);
                            }
                            break;
                        case "add":
                            isExist = true;
                            if (inputArr.length == 3)
                                gitAdd(inputArr[2]);
                            else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git add FILENAME" + ANSI_RESET);
                            break;
                        case "log":
                            isExist = true;
                            if (inputArr.length == 2)
                                getLog();
                            else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git log" + ANSI_RESET);
                            break;
                        case "status":
                            isExist = true;
                            if (inputArr.length == 2)
                                getBranchStatus();
                            else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git status" + ANSI_RESET);
                            break;
                        case "push":
                            isExist = true;
                            if (inputArr.length == 2)
                                gitPush();
                            else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git push" + ANSI_RESET);
                            break;
                        case "remote":
                            isExist = true;
                            if (inputArr.length == 2)
                                gitRemote();
                            else
                                System.out.println("git: \'" + this.toString() + "\' is not a git command. See 'git help'.\n\n" +
                                        "The most similar command is\n\t\t" + ANSI_YELLOW +
                                        "git remote" + ANSI_RESET);
                            break;
                        default:
                            break;
                    }
                default:
                    // 해당 명령이 없는 경우
                    if (!isExist)
                        System.out.println("zsh: command not found: " + ANSI_CYAN + this.toString() + ANSI_RESET);
                    break;
            }
        } while (!isExit);
    }

}
