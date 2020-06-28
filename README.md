# Virtual Git Program

1. 초기 `Client` class의 main을 실행했을 경우

    Terminal 이 뜨면서 프롬프트 이래

2. 깃 레포지토리 생성, 확인, 진입

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled.png)

    `git init soongsil` soongsil이름의 깃 레포지토리를 생성

    `git init devBlog` devBlog이름의 깃 레포지토리를 생성

    `ls`생성된 모든 레포확인

    `cd soongss` 처럼 다른이름으로 접근했을 경우

    `cd soongsil` repoExample이라는 이름으로 레포지토리로 진입

    `git remote` 현재 진입한 레포의 이름을 보여줌

3. 파일을 생성

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%201.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%201.png)

    `new README` README 라는 이름으로 파일생성

    `new README`처럼 같은이름으로 또 생성하려고 하는 경우

    `new config` config라는 이름으로 파일생성

    `new contact` contact라는 이름으로 파일생성

    `ls` 파일확인

    `git status` add (X), ONLY 생성만 된 파일들

4. Staging(add)해주어 Working Directory(`Untracked`)에서 확인할 수 있게 됨

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%202.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%202.png)

    `git add README` README이름의 파일을 찾아 add

    `git add fff` fff이름의 파일이 존재하지 않을 경우

    `git status` 현재 README만 add, 나머지는 Untracked files인경우

    `git add .` 새로 생성되었으면서 아직 add되지않은 모든 파일들을

    `git status` 현재 새로운 파일이 add 되었지만 변경되지 않은 상태

5. Commit 해주어 Staging Area(`OnlyStaging`) 에서 확인할수있게 됨

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%203.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%203.png)

`git log` 아직 최초에 아무런 커밋하지 않았으므로 커밋로그 없음

`git commit "Commit Test"` "Commit Test"라는 이름으로 커밋

`git commit "RE : Commit Test"` "RE: Commit Test"라는 이름으로 커밋하려고 하지만 이전 커밋과 비교하여 변경한 내용이 없어 working tree가 clean 하다고 알림

`git status` 커밋 이후 바뀐내용이없으므로 커밋은 \${푸쉬하지않은커밋개수}개 존재하지만 바뀐 내용은 없다고 알림

— 한번 더 파일생성 → 커밋해보겠습니다 —

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%204.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%204.png)

`new OOPnote` OOPnote이름의 파일 새로 생성

`new PLnote` PLnote이름의 파일 새로 생성

`git status` 위 두개의 파일이 Working Directory(`Untracked`) 인것을 볼수있게됨

`ls` status에 보이지 않는것은 커밋이후 변경되지않았기에 보여지지 않음! 위에 master 브랜치에 1 commits가 존재함을 볼수있음. 파일이 사라진것이 아님

`touch README` 아까 커밋한 README파일을 수정

`git add OOPnote` 아까 생성한 OOPnote 파일을 add

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%205.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%205.png)

`git status` 현시점. README → MODIFIED, OOPnote → NEWFILE, PLnote → UNTRACKED

`git commit "OOPnote DOC develop"` OOPnote DOC develop 메시지가 작성된 시점의 시각, 메시지, 파일의 변경 내역을 로그에 기록.

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%206.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%206.png)

`git log` 2번의 커밋 이력이 log로 남아있음 (위가 가장 오래된 파일, 아래로 스크롤 내릴수록 최신커밋) Untracked인 상태(add되지않은 파일)는 커밋되지않기에, PLnote는 커밋로그에 찍히지 않음

6. Push 해주어 Git Repository(`StagingNotChanged`) 에서 확인할 수 있게 됩니다.

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%207.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%207.png)

`git status` 현재 깃 레포의 상태를 확인합니다. 저번 푸쉬 이후 쌓인 커밋 개수를 판단해, 0개 이상이라면 푸쉬가능한 상태가 됩니다.

`git push` 현재까지의 변경 내용을 push합니다.

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%208.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%208.png)

가상 깃 프로그램이 아니라 저의 로컬 터미널로 접근해서 확인해보면, 해당 경로에 해당 파일이 생성된 것을 확인할 수 있습니다.

7. 깃 프로그램을 종료하고, 터미널로 돌아옵니다.

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%209.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%209.png)

8. 다른 레포지토리로 가서 해당작업을 똑같이 해봅니다

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2010.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2010.png)

다른레포지토리로 접근해 같은 과정을 반복합니다.

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2011.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2011.png)

해당 커밋 로그가 찍힌 파일을 확인합니다.

9. 다시 soongsil 레포지토리로 와서 해당작업이 남아있는지 확인합니다

![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2012.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2012.png)

아까 작업한 내역이 남아있음을 확인할 수 있습니다.

추가. 잘못된 입력

1. 터미널 환경에서 잘못된 입력, 유사한 입력

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2013.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2013.png)

    터미널 환경에서 잘못된 입력을 했을 경우 로그를 찍어줍니다. 특히나 터미널에서는 깃에 접근하지 않았다고 판단하므로, 깃 관련 명령어도 모두 잘못된 입력으로 간주합니다.

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2014.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2014.png)

    터미널 환경에서 유사한 입력을 했을 경우 로그를 찍어줍니다

2. 깃환경에서 잘못된 입력

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2015.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2015.png)

    깃 환경에서 잘못된 입력을 했을 경우 로그를 찍어줍니다

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2016.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2016.png)

    해당 Command는 맞았지만, 인자의 개수가 틀렸을 경우 로그를 찍어줍니다.

    ![Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2017.png](Virtual%20Git%20Program%20c12e76ed7e01493381587a5a7a9a79f0/Untitled%2017.png)

    해당 Command는 맞았지만, 인자의 형식이 틀렸을 경우 로그를 찍어줍니다.

## 3. Source Code 구조

가상깃인만큼 깃의 원리는 차용하지만 구동방식이 100% 동일하지는 않음

### 입력 상태

-   Terminal

    `git init 레포이름` 하지 않고 기본 터미널이 실행되고 있을 때를 의미합니다. prompt가 `>`로 보여집니다

-   Git

    `git init 레포이름`하여 깃 프로그램이 실행되고있을 때를 뜻합니다. prompt가 `레포이름/현재브랜치>`로 보여집니다.

### Prompt

터미널의 프롬프트를 구현합니다. 2가지 상태가 있습니다.

-   `>`

    Client클래스의 main함수를 실행하면 처음에 보여지는 기본(zsh) 터미널입니다. git init을 하지 않았을 경우

-   `레포이름/현재브랜치>`

    현재 `init`한 브랜치의 레포지토리 내에서, 현재 checkout한 브랜치의 이름을 보여줍니다. default는 master 입니다.

### Section(Stage)

깃 플로우에서 이전 스냅샷으로부터 변경된 부분을 비교하기 위해, 파일의 상태유형별로 클래스를 분리합니다. 각각의 클래스로 구현하고, 그 Working Directory(`Untracked`), Staging Area(`OnlyStaging`), Git Repository(`StagingNotChanged`)들을 FileMgr이 관리합니다.

-   `StagingNotChanged`

    GIt Repository에 해당하는 파일들이 해당하는 상태 클래스

    이전 Push 이후로 변화가 생긴 파일

    NOTCHANGED, MODIFIED 상태가 해당될 것이며 NOTCHANGED는 실제 깃에서처럼 보이지 않게 설정하였음(푸쉬 이후에 변화한 파일만 표시)

-   `OnlyStaging`

    Staging Area

    이전에 commit 한 히스토리 O

    MODIFIED, NEWFILE 상태가 해당됨

-   `Untracked`

    Working Directory

    이전에 commit 한 히스토리 X, 새로 add 된 파일

### File Status LifeCycle

실제 Git에서는 4가지 상태(untracked, tracked - unmodified, tracked - modified, tracked - staged)로 표현하지만, 제 프로젝트에서는 파일의 상태를 아래의 3가지로 표현합니다.

-   `NOTCHANGED`

    이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 X

-   `MODIFIED`

    이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 O

-   `NEWFILE`

    이전에 commit 한 히스토리 X, 새로 add 된 파일
