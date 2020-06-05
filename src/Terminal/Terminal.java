package Terminal;

import Git.Repository;

import java.util.ArrayList;
import java.util.Scanner;
/*

 - Terminal 의 기본 메서드를 가지고 있음
 - git의 기능을 사용하여 cli에서 git 제어가 가능하도록 함
 -

 */


public class Terminal extends Repository{
    public static Scanner scanner = new Scanner(System.in);
    public String input;
    public String[] inputArr;
    public boolean isExit;
    public String prompt; // prompt 종류 뭐로할건지. git 일지 그냥 terminal일지
    /*
    [Field]
    - public Repository[] repos;
        : Repository 여러개를 배열로 저장


    [기본 명령어]
    - run()
        : 터미널 계속 실행히면서, 명령어에 맞게 아래 함수들을 분배해줌
    - setNewRepo
        : 레포이름 생성
        > cd ${RepoName}
    - cd
        : git 프로그램에서 exit 하고 로컬 터미널사용
        > cd
    - touch(String fileName)
        : fileName에 해당하는 수정한다는 표시
        > touch ${fileName}

    [Git 명령어]
    - gitPush(String repoName)
        : repoName에 해당하는 레포지토리를 push한다. 이때 sample.txt에 해당 레포의 git log를 저장하는 함수 printGitLog()를 호출
        > git push ${repoName}
    - printGitLog(Repository repository)
        : sample.txt에 해당 레포의 git log를 저장
    - gitInit(String repoName)
        : 해당 레포이름을 만든다
        > git init ${repoName}

     */
    public ArrayList<Repository> repos = new ArrayList<>();
    // 삽입 - repos.add(Repository형 객체);
    // 순회 - for each 사용
    // 크기확인 - repos.size()
    // 제거 - repos.remove(index);

    // 새로운 터미널 생성
    public Terminal() {
        // 초기화
        input = "";
        isExit = false;
        prompt=  "> ";
    }

    public void setNewRepo(String repoName) {
        Repository newRepo = new Repository(repoName); // repoName을 가진 새로운 레포 생성
        repos.add(newRepo);
        System.out.println("✨"+repoName + " 이름의 레포가 생성되었습니다!");
    }

    public void run() {
        // 터미널을 계속 실행하기 위함. exit이 나오기 전까지 계속 실행
        do {
            // 계속 실행함. exit이 되기 전까지
            System.out.print(prompt);
            input = scanner.nextLine(); // 다음에 들어온 명령어
            // inputArr로 잘라 넣기
            inputArr = input.split(" ");
            for(int i =0 ; i<inputArr.length; i++){
                System.out.println(i + " "+ inputArr);
            }

            // 여기서 명령어에 맞게 분배해주어야함
            // 첫번째꺼로 판단함
            switch(inputArr[0]){
                case "exit":
                    isExit = true;
                    break;
                case "cd":
                    // RepoName 을 잘라야함
                    if(inputArr.length == 1){
                        // cd만 있을 경우 : git에서 나와야함
                        System.out.println("✨bye git");
                        prompt= "> ";
                    }else if(inputArr.length == 2) {
                        // cd RepoName인 경우
                        //// 혹시나 마지막에 띄어쓰기 나올경우 판단하는지 에러 핸들링 나중에 하기

                    }
                    System.out.println(input);
                    break;
                case "touch":
                    // > touch ${fileName} : 파일 수정했다고 해줘야함
                    // 현재repo.현재branch.fileName과 같은거 찾아서 modified로 상태 바꾸기(new Modified()); 로
                    System.out.println("✨파일이 수정되었습니다");
                    break;

                case "git":
                    if(inputArr.length == 2){

                    }else if(inputArr.length == 3){
                        // git init ${repoName}
                        if(inputArr[1] == "init"){
                            String newRepoName = inputArr[2];
                            setNewRepo(newRepoName); // 해당 이름으로 레포 추가함
                        }

                    }

                    prompt = "git > "; // 여기 원래 레포이름/브랜치 넣어야함 // 그럼 함수에서 호출해야할거같은데 ?

                default:
                    // 해당 명령이 없는 경우
                    System.out.println("✨command not found: " + input);
                    break;
            }

        } while (!isExit);
    }

}
