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

    // 여기서 안되는 경우는 있을수없음 -> System 오류!
    // touch fileName
    public File touchFile(File file) {
        File touchedFile;
        if (file instanceof StagingNotChanged) { // StagingNotChanged만 -> Modified로 수정
            touchedFile = new Modified();
            touchedFile.setFile(file.getFileName());
            //            touchedFile = null;//touchedFile ref 날리기
            System.out.println("✨파일이 수정되었습니다");
        }  else if(file instanceof Untracked){
            System.out.println("modified긴 해 !! ");
            touchedFile = file;
        }
        else {
            // StagingChanged, Untracked는 원래 상태 그대로 :)
            System.out.println("✨파일 수정 못했어요.. 뭔가 이상했나봐요.. >>" + file.getClass() + "<<이라는데요?얘는toString>>"+file.toString());
            touchedFile = file; // 그냥 고대로 보내기
        }

        return touchedFile;
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
