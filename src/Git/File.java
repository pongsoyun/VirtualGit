package Git;

import java.util.ArrayList;

import static Terminal.Color.*;

class FileMgr {
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
        snapshotsBefore = snapshots;
        snapshots.setLength(0); // 초기화
    }

    // 모든 파일 알려주기
    public void getFiles() {
        getAllStagingNotChanged();
        getAllOnlyStaging();
        getAllUntracked();
    }


    public boolean getAllStagingNotChanged() {
        boolean isExist = false;
        StringBuffer str = new StringBuffer();
        str.append("Changes to be committed:\n\n\n");
//        for(File file : files){
//            if(file instanceof StagingNotChanged){
//                isExist = true;
//                str.append(ANSI_GREEN + "\t\t\tnew file:\t"+file.getFileName()+""+ANSI_RESET);
//            }
//        }
        // snapshotBefore가 있기라도 하면 -> snapshotsBefore = null 로 날릴때 : push 할때
        if(snapshotsBefore.length()==0)
            isExist = true;
        if(isExist){
            System.out.println(str);
            System.out.print(snapshotsBefore);
        }
        return isExist;
    }

    public boolean getAllOnlyStaging(){
        boolean isExist=false;
        snapshots.setLength(0);// 푸쉬하기직전까지 업데이트 하다가, 푸쉬하고 Before로 보낸다
        StringBuffer str = new StringBuffer();
        str.append("Changes not staged for commit:\n\n");
        str.append("\t(use \"git add <file>...\" to update what will be committed)\n");
        str.append("\t(use \"git checkout -- <file>...\" to discard changes in working directory)\n\n");
        for(File file : files){
            if(file instanceof OnlyStaging){
                isExist = true;
                snapshots.append(ANSI_RED + "\t\t\tmodified:\t"+file.getFileName()+"\n"+ANSI_RESET);
                str.append(ANSI_RED + "\t\t\tmodified:\t"+file.getFileName()+"\n"+ANSI_RESET);
            }
        }
        if(isExist)
            System.out.print(str);

        return isExist;
    }

    public boolean getAllUntracked() {
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

        return isExist;
    }


    // touch fileName
    // StagingNotChanged -> Modified
    public File touchFile(File file) {
        File touchedFile;
        if (file instanceof StagingNotChanged) {
            touchedFile = new OnlyStaging();
            touchedFile.setFile(file.getFileName());

            System.out.println("✨파일이 수정되었습니다");
        }  else if(file instanceof Untracked){
            System.out.println("untracked 야!! add하고, 수정해줘");
            touchedFile = file;
        }
        else {
            // StagingChanged, Untracked는 원래 상태 그대로 :)
            System.out.println("✨파일 수정 못했어요.. 뭔가 이상했나봐요.. >>" + file.getClass() + "<<이라는데요?");
            touchedFile = file; // 그냥 고대로 보내기
        }

        return touchedFile;
    }

    // git add fileName
    // Untracked -> OnlyStaging
    public File addFile(File file){
        File stagingFile;
        if(file instanceof Untracked){
            stagingFile = new OnlyStaging();
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
    }

    // git commit 하고나서 들어오는것
    // Modified -> StagingnotChanged
    public void commitFile(){
        File stagingFile;
        for(File file : files){
            if(file instanceof OnlyStaging){
                stagingFile = new StagingNotChanged();
                stagingFile.setFile(file.getFileName());
                swapFile(file, stagingFile);
            }
        }
    }
}

public interface File {
    // 모두 public
    void setFile(String name);

    String getFileName();
}

class StagingNotChanged implements File {
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
}

class OnlyStaging implements File {
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
}

class Untracked implements File {
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
}
