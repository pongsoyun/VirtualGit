package Terminal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOMgr {
    public FileIOMgr() {
    }

    // commitLogs 들 저장
    public static void makeFile(String repoName, String branchName, String contents) {
        String path = "./TermProject/src/" + repoName; // 인텔리 제이 사용해서 경로가 이렇게 지정되어있음.
        String fileName = "remote_" + branchName + ".txt";
        File folder = new File(path);
        System.out.println("file name : " + fileName);
        File file = new File(path + "/" + fileName);
        System.out.println("file name : " + path + "/" + fileName);


        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!folder.exists()) {
            try {
                folder.mkdir(); //폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }

        // 파일 쓰기
        try {
            if (file.createNewFile()) {
                System.out.println("파일 생성 :" + file.getName());
            } else {
                System.out.println("같은 이름의 파일 이미 존재");
            }
            FileWriter writer = null;

            try {
                writer = new FileWriter(file, false); // 기존 내용 지우고(false)
                writer.write(contents);
                writer.flush();

                System.out.println("DONE");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR 발생");
            e.printStackTrace();
        }
    }

}
