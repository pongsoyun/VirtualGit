package Git;

import java.util.ArrayList;

class FileMgr {
    ArrayList<File> files = new ArrayList<File>();

    @Override
    public String toString() {
        System.out.print("getClass(): " + getClass() +
                "toString(): " + getClass().toString() +
                "super(): " + getClass().getSuperclass());
        return toString();
    }


    public void setFile(String name, String status) {
        File file = null;
        if (status.equals("staging")) { // stagingNotChange
            file = new StagingNotChanged();
            file.setFile(name);
        } else if (status.equals("modified")) { // StagingChanged
            file = null;
            file = new Modified();
            file.setFile(name);
        } else if (status.equals("untracked")) { // Untracked
            file = null;
            file = new Untracked();
            file.setFile(name);
        } else {
            // 해당하는 상태가 없음 -> System 오류!
        }
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
    // StagingNotChanged만 -> Modified로 수정
    public File touchFile(File file) {
        File touchedFile;
        if (file.toString().equals("StagingNotChanged")) { // StagingNotChanged
            touchedFile = new Modified();
            touchedFile.setFile(file.getFileName());
            //            touchedFile = null;//touchedFile ref 날리기
            System.out.println("✨파일이 수정되었습니다");
        } else {
            // StagingChanged, Untracked는 원래 상태 그대로 :)
            System.out.println("✨파일 수정 못했어요.. 뭔가 이상했나봐요.. >>" + file.getFileName() + "<<이라는데요?");
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
