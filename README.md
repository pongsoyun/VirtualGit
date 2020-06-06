# VirtualGit

-   [x] pwd
-   [x] exit
-   [x] cd
-   [x] cd \${repoName} -> remote/branch > 로 해야함
-   [x] touch \${fileName}
-   [x] new \${fileName}
-   [x] git push
-   [x] git log
-   [x] git branch
-   [x] git status -> 스냅샷 👀예쁘게 다듬어야함. Class 순서대로 안보여짐 아직
-   [x] git remote -> @Overriding toString();

-   [x] git init \${repoName}
-   [x] git push \${repoName} -> 폴더, remote/branch.txt 생성하고 현재까지 커밋로그 저장
-   [x] git checkout \${branchName} -> repoName/branchName >
-   [x] git branch \${branchName}
-   [x] git add \${fileName}
-   [x] git add .
-   [x] git commit \${commitMSG}

## CLI UI + Test Code 필요

-   [ ] color 함수로 감싸기
-   [ ] cli color 적용
-   [ ] cli 문구 수정
-   [x] git status 스냅샷 예쁘게 다듬기
-   [ ] cli 에러핸들링 - 혹시나 마지막에 띄어쓰기 나올경우 판단하는지 에러 핸들링 나중에 하기
-   [ ] cli 다른문자입력시 안한거없는지 확인
-   [ ] Terminal , Git Package 별 터미널 다시한번 확인

## BUG

-   [ ] 현재 commitmessage 한단락아니면 안됨 수정
-   [ ] @Override 라고 하나 써놓은거있는데 참고 -> swap()
-   [x] ㅎ.. .커밋 두개이상 하면 버그남
-   [x] commitLog 스냅샷 그대로 찍는거 예외없이
-   [x] 그리고 touch하면 직므 변경이 안돼
-   [x] 커밋하고나서 snapshot에 남아있네
-   [x] New 파일인데 modified 로 올려지기도 ㅇㅇ...

# class `Repository`

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

```
public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


```
