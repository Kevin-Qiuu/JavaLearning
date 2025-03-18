package ee_10_document_io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo_03_file_demo_1 {
    public static void scan_dir(File scan_file, ArrayList<String> files_with_keyword, String keyword) throws IOException {
        File[] listFiles = scan_file.listFiles();
        if (listFiles != null) {
            for (File file : listFiles){
                if (file.isFile()){
                    if (file.getName().contains(keyword))
                        files_with_keyword.add(file.getCanonicalPath());
                } else {
                    scan_dir(file, files_with_keyword, keyword);
                }
            }

        }
    }

    public static boolean delete_file_keywords(String file_path, String keyword) throws IOException {
        File target_file = new File(file_path);

        // 文件不存在，则报错
        if (!target_file.exists()){
            System.out.println("The directory is not exit!!");
            return false;
        }

        // 目录存在提取目录的全部文件
        File[] target_file_list = target_file.listFiles();
        ArrayList<String> files_with_keyword = new ArrayList<>();  // 输出型参数
        if (target_file_list != null) {
            for (File file : target_file_list) {
//                File file = new File(target_file, target_file_list[i]);
                if (file.isFile()){
                    if (file.getName().contains(keyword)) {
                        files_with_keyword.add(file.getCanonicalPath());
                    }
                } else {
                    scan_dir(file, files_with_keyword, keyword);
                }

            }
        } else {
            System.out.println("The directory has not any files");
            return true;
        }


    }
    public static void main(String[] args) {
        /**
         * 用户输入一个目录和一个关键字，在这个路径下查找所有包含关键字的文件（不包含目），
         * 询问用户是否要删除这些文件
         */
        Scanner scanner = new Scanner(System.in);
        String file_path = scanner.nextLine();
        String keyword = scanner.nextLine();
//        System.out.println(file_path);
        delete_file_keywords(file_path, keyword);
    }
}
