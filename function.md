✋🏻 Hi! This is Virtual git Program ✋🏻
Author : SoYun Bang, School Of Global Media, in Soongsil Univ.
AuthorID : 20162574
Contact : thdbstjdud@gmail.com
Project Name : OOP Term Project
Project Subject : Virtual Git Program

✨<- gitmoji :sparkles: - Additional Log. 실제로는 해당 Line이 등장하지 않지만, 반응을 즉각 보이기 위한 event Log.

These are common Git commands used in various situations:

start a working area
[ONLY in Terminal]
pwd          terminal running...
exit          program 종료
cd ${repoName}              ${repoName}에 해당하는 repository로 접근하며, git 작업을 할 수 있음

[ONLY in Git]
pwd          git running...
cd          해당 repository에서 빠져나와 git 작업을 할수 없음
touch ${fileName}           ${fileName}파일을 수정한다는 의미
new ${fileName}           ${fileName}파일을 생성. 이미 존재한다면 생성하지않음을 알리고 재입력 받음
git checkout ${branchName}           ${branchName}의 브랜치로 checkout함. 없다면 재입력 받음
git branch          현재 repository가 보유하고있는 branch의 목록을 리스트업. 현재 체크아웃한 브랜치 앞에는 * 가 붙음
git add ${fileName}          아직 Staging되지 않은 상태의 ${fileName}을 Staging함
git add .          아직 Staging 되지 않은 상태의 모든 파일들을 Staging함
git commit ${commitMSG}          ${commitMSG}의 내용으로 현재 Tracked 중인(Staging된 상태의) 파일들을 Commit함
git push           현재까지의 커밋로그를 ${repoName}의 폴더 안에 remote_${branchName}.txt 파일에 작성하여 저장. 현재 체크아웃한 브랜치의 것을 푸쉬함
git status           현재 파일들의 상태를 보여줌. 
git log           해당 branch의 현재까지의 커밋로그를 보여줌. commitID는 random으로 임의로 받음 
 
 
[About File]
StagingNotChanged           이전 Push 이후로 변화가 생긴 파일. NOTCHANGED, MODIFIED 상태가 해당될 것이며 NOTCHANGED는 실제 깃에서처럼 보이지 않게 설정하였음(푸쉬 이후에 변화한 파일만 표시)  
OnlyStaging           이전 Commit 이후로 변화가 생긴 파일. NEWFILE, MODIFIED 상태가 해당됨  
Untracked           새롭게 new 생성된 파일. NEWFILE 상태만 해당하므로, 파일의 Status를 보여주지 않음

[About Status]
NOTCHANGED           이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 X
MODIFIED           이전에 commit 한 히스토리 O, 이전 커밋 이후로 변경 O
NEWFILE           이전에 commit 한 히스토리 X, 새로 add 된 파일 


~~EOF~~