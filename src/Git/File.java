package Git;

import java.util.ArrayList;

import static Git.FileMgr.*;
import static Terminal.Color.*;

class FileMgr {
    enum Status {NEWFILE, MODIFIED, NOTCHANGED}

    ArrayList<File> files = new ArrayList<File>();
    StringBuffer snapshotsBefore = new StringBuffer();
    StringBuffer snapshots = new StringBuffer();

    @Override
    public String toString() {
        return toString();
    }

    public boolean isSnapshotsExist() {
        if (snapshots.length() == 0)
            return false;
        else
            return true;
    }


    // new file
    public void setFile(String name) {
        // new = untracked
        File file = new Untracked();
        file.setFile(name);
        files.add(file);
        System.out.println("✨" + file.getFileName() + " 이 생성되었습니다! " + file.getClass() + " toString은 : " + file.toString());
    }

    public boolean isExist(String name) {
        for (File file : files) {
            if (name.equals(file.getFileName())) {
                return true;
            }
        }
        return false;
    }

    // 해당 이름 파일찾기
    public File searchFile(String name) {
        for (File file : files) {
            if (name.equals(file.getFileName())) {
                return file;
            }
        }
        System.out.println("해당 파일 이름 없음. 엥 ? 여기서 나오면 안되는데 ");
        return null;
    }

    public ArrayList<String> searchUntrackedFiles() {
        ArrayList<String> str = new ArrayList<String>();
        for (File file : files) {
            if (file instanceof Untracked) {
                str.add(file.getFileName());
            }
        }
        return str;
    }

    public void getClean() {
        snapshots.setLength(0); // 초기화
        // 요 안에서도 같은거있는지 확인하고 없애야함ㅊㅊ -> 그냥 다시 넣어주기
        StringBuffer newSnapShot = new StringBuffer();
        for (File file : files) {
            if ((file instanceof StagingNotChanged)) {
                // 스테이징인데 수정된거잇다면 또더하기 그러니까-> 걔 빼고 append 할거임
                // StagingNotChanged 모두
                // OnlyStaging && NEWFILE
                newSnapShot.append(ANSI_GREEN + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
            } else if (file instanceof OnlyStaging && file.getStatus().equals(Status.NEWFILE)) {
                newSnapShot.append(ANSI_GREEN + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
            }
        }
        snapshotsBefore.setLength(0);
        ;
        snapshotsBefore.append(newSnapShot);
        System.out.println("snapshots 초기화 됐을까? " + snapshots.length());
    }

    // 모든 파일 알려주기
    public void getFiles() {
        getAllStagingNotChanged();
        getAllOnlyStaging();
        getAllUntracked();

        System.out.println("==========================");
        System.out.println("snapshot:" + snapshots);
        System.out.println("snapshotsBefore: " + snapshotsBefore);
        System.out.println("==========================");
    }


    public StringBuffer compareSnapShots(StringBuffer snapshots) {
        // commit 이력이 존재하는 파일명찾아서 넣어야함
        StringBuffer compareSnapShots = new StringBuffer();
        int fileIndex = snapshotsBefore.indexOf(""); // fileName이 있는 index 찾기
        return compareSnapShots;
    }

    public void getAllStagingNotChanged() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        boolean isChanged = false; // 만약에 아까 푸쉬한애가 변경되었다면


        // 이전에 커밋이력이 없다면 변경존재한다고 하고 밑에서 변경할거임
        // push하면 snapshotsBefore = 0으로만들어줘야함
        if (snapshotsBefore.length() != 0)
            isExist = true;

        // 이전했던 커밋이력이 있다면 변경할건데
        if (isExist) {
            str.append("Changes to be committed:\n\n");
            for (File file : files) {
                /*
               1. 지금 커밋한애들중에, 아까 푸쉬한애(snapShotBefore)가 있나 ? 찾아보자
                 */
                if (file.getStatus().equals(Status.MODIFIED)) {
                    // 만약 해당이름의 파일이 존재한다면
                    if (snapshotsBefore.toString().contains(file.getFileName())) {
                        isChanged = true;
                        file.setStatus(Status.MODIFIED);
                        str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                    }
                } else if ((file instanceof StagingNotChanged)) {
                    // 해당 파일이 지금 변경되지 않았는데, 아까 푸쉬했을 경우
                    str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);

                }
            }


            /////////////////////////////////////////////////////////

            if (isChanged) {
                System.out.println(str);
                snapshotsBefore = str;
            } else {
                System.out.println(snapshotsBefore);
            }
        } else {
            // cleaned 뭐 뽑아야되는거아님?
            System.out.println("바뀐거없다");
        }

    }

    public void getAllOnlyStaging() {
        boolean isExist = false;
        snapshots.setLength(0);// 푸쉬하기직전까지 업데이트 하다가, 푸쉬하고 Before로 보낸다
        StringBuffer str = new StringBuffer();
        str.append("Changes not staged for commit:\n\n");
        str.append("\t(use \"git add <file>...\" to update what will be committed)\n");
        str.append("\t(use \"git checkout -- <file>...\" to discard changes in working directory)\n\n");
        for (File file : files) {
            // 사실 얘.. OnlyStaging만이 아니라 위에 push된것중에도 수정된거 다 골라야되는거아냐 ?
            if (file instanceof StagingNotChanged) {
                if (file.getStatus().equals(Status.MODIFIED)) {
                    isExist = true;
                    snapshots.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                    str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                }
            }
            if (file instanceof OnlyStaging) {
                isExist = true;
                snapshots.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
            }
        }
        if (isExist)
            System.out.print(str);
    }

    public void updateStagingNotChanged() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        boolean isChanged = false; // 만약에 아까 푸쉬한애가 변경되었다면


        // 이전에 커밋이력이 없다면 변경존재한다고 하고 밑에서 변경할거임
        // push하면 snapshotsBefore = 0으로만들어줘야함
        if (snapshotsBefore.length() != 0)
            isExist = true;

        // 이전했던 커밋이력이 있다면 변경할건데
        if (isExist) {
            for (File file : files) {
                if (file.getStatus().equals(Status.MODIFIED)) {
                    // 만약 해당이름의 파일이 존재한다면
                    if (snapshotsBefore.toString().contains(file.getFileName())) {
                        isChanged = true;
                        file.setStatus(Status.MODIFIED);
                        str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                    }
                } else if ((file instanceof StagingNotChanged)) {
                    // 해당 파일이 지금 변경되지 않았는데, 아까 푸쉬했을 경우
                    str.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                }
            }

            /////////////////////////////////////////////////////////

            if (isChanged) {
                snapshotsBefore = str;
            } else {
                System.out.println(snapshotsBefore);
            }
        } else {
            System.out.println("바뀐거없다");
        }
    }

    public void updateOnlyStaging() {
        snapshots.setLength(0);// 푸쉬하기직전까지 업데이트 하다가, 푸쉬하고 Before로 보낸다
        StringBuffer str = new StringBuffer();
        for (File file : files) {
            if (file instanceof StagingNotChanged) {
                if (file.getStatus().equals(Status.MODIFIED)) {
                    snapshots.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
                }
            }
            if (file instanceof OnlyStaging) {
                snapshots.append(ANSI_RED + "\t\t\t" + file.getStatus().toString() + ":\t" + file.getFileName() + "\n" + ANSI_RESET);
            }
        }
    }

    public void updateUntracked() {
        StringBuffer str = new StringBuffer();
        for (File file : files) {
            if (file instanceof Untracked) {
                str.append(ANSI_RED + "\t\t\t" + file.getFileName() + "\n" + ANSI_RESET);
            }
        }
    }

    public void getAllUntracked() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        str.append("Untracked files:\n\n");
        str.append("\t(use \"git add <file>...\" to include in what will be committed)\n\n");
        for (File file : files) {
            if (file instanceof Untracked) {
                isExist = true;
                str.append(ANSI_RED + "\t\t\t" + file.getFileName() + "\n" + ANSI_RESET);
            }
        }
        if (isExist)
            System.out.print(str);
    }


    // touch fileName
    // StagingNotChanged -> Modified 가 아니라 modified: 아니면 new file: 로!!!!
    public File touchFile(File file) {
        file.setStatus(Status.MODIFIED);
        System.out.println("수정됩니다 MODIFIED가 되어야정상" + file.getStatus());
        return file;
    }

    // git add fileName
    // Untracked -> OnlyStaging
    public File addFile(File file) {
        if (file instanceof Untracked) {
            File stagingFile = new OnlyStaging();
            stagingFile.setFile(file.getFileName());
            file = stagingFile;
            System.out.println("✨" + stagingFile.getFileName() + " 이 Staging 되었습니다");
        } else {
            System.out.println("✨더이상 Add 할 파일이 없어요");
        }
        snapshots.append(" "); // update 하는걸 만들까? 지금 touch나 add 나 다 log를 찍어보지않으면 안됨 ㅠㅠ
        return file;
    }

    // add 하고나서 변경하는 코드.
    public void swapFile(File file, File newFile) {
        files.remove(file);
        files.add(newFile);
//        return file; // 원래 File이었는데 안써서 걍 다시바꿈
        System.out.println("바꿈. 두번째것 : " + newFile.getStatus());
    }


    // git commit 하고나서 들어오는것. 후철ㅣ
    // Modified -> StagingnotChanged
    public void commitFile() {
        if (files.size() != 0) {
            for (int j = 0; j < files.size(); j++) {
                if (files.get(j) instanceof OnlyStaging) {
                    System.out.println(files.get(j) + " 이 OnlyStaging이라나봐요!");
                    File stagingFile = new StagingNotChanged();
                    stagingFile.setFile(files.get(j).getFileName());
                    swapFile(files.get(j), stagingFile);
                    j--; // 그럼 현재것을 가리키라고 한번 해보자
                } else if (files.get(j) instanceof StagingNotChanged) {
                    files.get(j).setStatus(Status.NOTCHANGED); // 변경안된것
                }
            }
            getClean();
        }
    }

    public String getSnapShot() {
        return this.snapshots.toString();
    }


}

public interface File {
    // 모두 public
    void setFile(String name);

    String getFileName();

    Status getStatus();

    void setStatus(Status status);
}

class StagingNotChanged implements File {
    private Status status = Status.NEWFILE;
    private String name;

    public StagingNotChanged() {

    }

    @Override
    public void setFile(String name) {
        this.name = name;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}

class OnlyStaging implements File {
    private Status status = Status.NEWFILE;
    private String name;


    public OnlyStaging() {

    }

    @Override
    public void setFile(String name) {
        this.name = name;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}

class Untracked implements File {
    private Status status = Status.NEWFILE;
    private String name;

    public Untracked() {

    }

    @Override
    public void setFile(String name) {
        this.name = name;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
