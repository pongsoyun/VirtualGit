package Git;

import java.util.ArrayList;

class FileMgr {
    ArrayList<File> files = new ArrayList<File>();

    @Override
    public String toString() {
        return toString();
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

    // 모든 파일 알려주기
    public void getFiles() {
        for (File file : files) {
            System.out.println("file 명 : " + file.getFileName());
            System.out.println("file 상태 : " + file.toString());
            System.out.println();
        }
    }

    // touch fileName
    // StagingNotChanged -> Modified
    public File touchFile(File file) {
        File touchedFile;
        if (file instanceof StagingNotChanged) {
            touchedFile = new Modified();
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
    // Untracked -> StagingNotChanged
    public File addFile(File file){
        File stagingFile;
        if(file instanceof Untracked){
            stagingFile = new StagingNotChanged();
            stagingFile.setFile(file.getFileName());
            file = stagingFile;
            System.out.println("✨"+stagingFile.getFileName()+" 이 Staging 되었습니다");
        }else {
            System.out.println("✨더이상 Add 할 파일이 없어요");
        }
        return file;
    }

    // add 하고나서 변경하는 코드. untracked <-> stagingNotChanged로
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
            if(file instanceof Modified){
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

class Modified implements File {
    private String status;
    private String name;

    public Modified() {

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
    private String status;
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
