package Git;

import java.util.ArrayList;

import static Git.FileMgr.*;
import static Terminal.Color.*;

class FileMgr {
    enum Status {NEWFILE, MODIFIED}

    ArrayList<File> files = new ArrayList<File>();
    StringBuffer snapshotsBefore = new StringBuffer();
    StringBuffer snapshots = new StringBuffer();

    @Override
    public String toString() {
        return toString();
    }

    public boolean isSnapshotsExist() {
        if(snapshots.length()==0)
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

    public File searchFile(String name) {

        for (File file : files) {
            if (name.equals(file.getFileName())) {
                return file;
            }
        }
        System.out.println("해당 파일 이름 없음. 엥 ? 여기서 나오면 안되는데 ");
        return null;
    }

    public void getClean() {
        snapshotsBefore.append(snapshots);
        snapshots.setLength(0); // 초기화
    }

    // 모든 파일 알려주기
    public void getFiles() {
        getAllStagingNotChanged();
        getAllOnlyStaging();
        getAllUntracked();

        System.out.println("==========================");
        System.out.println("snapshot:"+snapshots);
        System.out.println("snapshotsBefore: "+snapshotsBefore);
        System.out.println("==========================");
    }


    // 🐛발견 !!
    // 현재 그냥 StringBuffer 로 넣기만 해서, new file: abc (push)-> modified: abc (commit)  내역이 그대로 저장됨
    public void getAllStagingNotChanged() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        str.append("Changes to be committed:\n\n");

        if(snapshotsBefore.length()!=0)
            isExist = true;

        if(isExist){
            System.out.println(str);
            System.out.print(snapshotsBefore);
        }
    }

    public void getAllOnlyStaging(){
        boolean isExist=false;
        snapshots.setLength(0);// 푸쉬하기직전까지 업데이트 하다가, 푸쉬하고 Before로 보낸다
        StringBuffer str = new StringBuffer();
        str.append("Changes not staged for commit:\n\n");
        str.append("\t(use \"git add <file>...\" to update what will be committed)\n");
        str.append("\t(use \"git checkout -- <file>...\" to discard changes in working directory)\n\n");
        for(File file : files){
            // 사실 얘.. OnlyStaging만이 아니라 위에 push된것중에도 수정된거 다 골라야되는거아냐 ?
            if(file instanceof StagingNotChanged){
                if(file.getStatus().equals(Status.MODIFIED)){
                isExist = true;
                snapshots.append(ANSI_RED + "\t\t\t"+file.getStatus().toString()+":\t"+file.getFileName()+"\n"+ANSI_RESET);
                str.append(ANSI_RED + "\t\t\t"+file.getStatus().toString()+":\t"+file.getFileName()+"\n"+ANSI_RESET);
                }
            }
            if(file instanceof OnlyStaging){
                isExist = true;
                snapshots.append(ANSI_RED + "\t\t\t"+file.getStatus().toString()+":\t"+file.getFileName()+"\n"+ANSI_RESET);
                str.append(ANSI_RED + "\t\t\t"+file.getStatus().toString()+":\t"+file.getFileName()+"\n"+ANSI_RESET);
            }
        }
        if(isExist)
            System.out.print(str);
    }

    public void getAllUntracked() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        str.append("Untracked files:\n\n");
        str.append("\t(use \"git add <file>...\" to include in what will be committed)\n\n");
        for(File file : files){
            if(file instanceof Untracked){
                isExist = true;
                str.append(ANSI_RED + "\t\t\t"+file.getFileName()+"\n"+ANSI_RESET);
            }
        }
        if(isExist)
            System.out.print(str);
    }


    // touch fileName
    // StagingNotChanged -> Modified 가 아니라 modified: 아니면 new file: 로!!!!
    public File touchFile(File file) {
        file.setStatus(Status.MODIFIED);
        System.out.println("수정됩니다 MODIFIED가 되어야정상"+file.getStatus());
//        if(file.getStatus() == Status.NEWFILE){
//            file.setStatus(Status.MODIFIED);
//        }
        // 1. 상태 받아와서 -> modified로 바꾼다
        // 1.5 touchedFile에 file을 대입하고
        // 2. 해당 바꾼 애를 리턴한다

//        if (file instanceof StagingNotChanged) {
//            touchedFile = new OnlyStaging();
//            touchedFile.setFile(file.getFileName());
//
//            System.out.println("✨파일이 수정되었습니다");
//        }  else if(file instanceof Untracked){
//            System.out.println("untracked 야!! add하고, 수정해줘");
//            touchedFile = file;
//        }
//        else {
//            // StagingChanged, Untracked는 원래 상태 그대로 :)
//            System.out.println("✨파일 수정 못했어요.. 뭔가 이상했나봐요.. >>" + file.getClass() + "<<이라는데요?");
//            touchedFile = file; // 그냥 고대로 보내기
//        }

        return file;
    }

    // git add fileName
    // Untracked -> OnlyStaging
    public File addFile(File file){
        if(file instanceof Untracked){
            File stagingFile = new OnlyStaging();
            stagingFile.setFile(file.getFileName());
            file = stagingFile;
            System.out.println("✨"+stagingFile.getFileName()+" 이 Staging 되었습니다");
        }else {
            System.out.println("✨더이상 Add 할 파일이 없어요");
        }
        return file;
    }

    // add 하고나서 변경하는 코드. untracked <-> OnlyStaging로
    public void swapFile(File file, File newFile){
        files.remove(file);
        files.add(newFile);
//        return file; // 원래 File이었는데 안써서 걍 다시바꿈
        System.out.println("바꿈. 두번째것 : " + newFile.getStatus());
    }

    // git commit 하고나서 들어오는것
    // Modified -> StagingnotChanged
    public void commitFile(){
        System.out.println("commitFile 진입은 잘 됐음. files.size : " + files.size());
        if(files.size() !=0 ){
            for(int j=0;j<files.size();j++){
                System.out.println(files.size()+" << 사이즈 바뀌었을까?" + files.get(j).getFileName() +"하는중 -> ");
                // 현재 문제점 : swap에서 삭제 -> 삽입을 하니까 이런 문제가 생기는거임
                if(files.get(j) instanceof OnlyStaging){
                    System.out.println(files.get(j)+" 이 OnlyStaging이라나봐요!");
                    File stagingFile = new StagingNotChanged();
                    stagingFile.setFile(files.get(j).getFileName());
                    swapFile(files.get(j), stagingFile);
                    j--; // 그럼 현재것을 가리키라고 한번 해보자
                }
            }
            getClean();
        }

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
