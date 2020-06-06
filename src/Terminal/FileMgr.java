package Terminal;

import java.io.File;

/*
mkdir, file 생성하는 곳입니다.
 */
public class FileMgr {

    public FileMgr() {

    }

    // commitLogs 들 저장
    public static void makeFile(String contents) {


    }

    // folder 생성
    public static void makeFolder(String name) {
        String path = "./TermProject/src/" + name;
        File Folder = new File(path);

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try {
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }
    }
}
