package ee_10_document_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo_05_find_file_and_delete_pro {
    // 删除给定目录中文件名和文件内容包含 keyword 的文件，不包含目录
    public static void main(String[] args) throws IOException {

        boolean opt_ret = delete_files_with_keywords();
        System.out.println(opt_ret ? "Delete successfully!" : "Delete failed!");

    }

    private static boolean delete_files_with_keywords() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the target file path: ");
        String target_dir_path = scanner.nextLine();
        File target_dir = new File(target_dir_path);

        // 校验 target_file
        if (!target_dir.exists()) {
            System.out.println("Target file is not existed.");
            return false;
        }
        if (target_dir.isFile()) {
            System.out.println("This is not a directory, delete failed.");
            return false;
        }

        System.out.print("Input the keyword: ");
        String keyword = scanner.nextLine();
        // 校验成功，执行查找并删除包含 keyword 的数据的文件
        ArrayList<String> files_contain_keyword_path = new ArrayList<>();
        scan_file(target_dir, files_contain_keyword_path, keyword);

        // 执行删除操作，首先让用户确认
        System.out.println("The files below will be deleted, please check them...");
//        System.out.println(files_path_with_keyword);
        for (String file_path_with_keyword : files_contain_keyword_path) {
            System.out.println(file_path_with_keyword);
        }
        System.out.print("Please input [y]/[N] :");
        String user_choice = scanner.nextLine();
        if (user_choice.equals("y")) {
            // todo
            System.out.println("Execute delete transaction...");
            for (String file_path_with_keyword : files_contain_keyword_path) {
                if (!new File(file_path_with_keyword).delete()) {
                    System.out.println("The file " + file_path_with_keyword + " delete failed.");
                }
            }
            return true;
        } else {
            System.out.println("Cancel successfully !");
            return false;
        }
    }

    private static void scan_file(File targetDir, ArrayList<String> filesContainKeywordPath, String keyword) throws IOException {
        File[] files = targetDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains(keyword)) {
                        filesContainKeywordPath.add(file.getCanonicalPath());
                        continue;
                    }
                    // 使用 Scanner 借助文件输入流来读取文件
                    Scanner scanner = new Scanner(new FileInputStream(file));
                    while (scanner.hasNext()) {
                        String s = scanner.nextLine();
                        if (s.contains(keyword)) {
                            filesContainKeywordPath.add(file.getCanonicalPath());
                            break;
                        }
                    }
                } else {
                    scan_file(file, filesContainKeywordPath, keyword);
                }
            }
        }
    }
}
