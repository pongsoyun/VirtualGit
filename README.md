# VirtualGit

-   [ ] pwd
-   [ ] ls은 나중에..
-   [x] exit
-   [x] cd
-   [x] cd \${repoName} -> remote/branch > 로 해야함
-   [ ] touch \${fileName}
-   [ ] new \${fileName}

-   [ ] git push
-   [ ] git log
-   [ ] git branch
-   [ ] git status

-   [ ] git clone \${repoName}
-   [x] git init \${repoName}
-   [ ] git push \${repoName} -> remote/repoName.txt 생성하고 현재까지 커밋로그 저장
-   [ ] git checkout \${branchName}
-   [ ] git branch \${branchName} -> repoName/branchName > 으로 변경
-   [ ] git add \${fileName}
-   [ ] git commit \${commitMSG}

//// 혹시나 마지막에 띄어쓰기 나올경우 판단하는지 에러 핸들링 나중에 하기

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
