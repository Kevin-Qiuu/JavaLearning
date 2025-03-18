package ee_10_document_io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo_03_file_demo_1 {
    public static void scan_dir(File scan_file, ArrayList<String> files_path_with_keyword, String keyword) throws IOException {
        File[] listFiles = scan_file.listFiles();
        if (listFiles != null) {
            for (File file : listFiles){
                if (file.isFile()){
                    if (file.getName().contains(keyword))
                        files_path_with_keyword.add(file.getCanonicalPath());
                } else {
                    scan_dir(file, files_path_with_keyword, keyword);
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

        ArrayList<String> files_path_with_keyword = new ArrayList<>();  // 输出型参数，统计全部包含关键字的文件路径
        if (!target_file.isFile()){
            scan_dir(target_file, files_path_with_keyword, keyword);
        } else {
            System.out.println("The file path is a file, not directory !!");
            return false;
        }

        System.out.println("These files will be delete, please check...");
//        System.out.println(files_path_with_keyword);
        for (String file_path_with_keyword : files_path_with_keyword) {
            System.out.println(file_path_with_keyword);
        }
        System.out.print("Please input [y]/[N] :");
        Scanner scanner = new Scanner(System.in);
        String user_choice = scanner.nextLine();
        if (user_choice.equals("y")){
            // todo
            System.out.println("Execute delete transaction...");
            return true;
        } else {
            System.out.println("Cancel successfully !");
            return false;
        }

    }

    public static void main(String[] args) throws IOException {
        /**
         * 用户输入一个目录和一个关键字，在这个路径下查找所有包含关键字的文件（不包含目），
         * 询问用户是否要删除这些文件
         */
//        Scanner scanner = new Scanner(System.in);
//        String file_path = scanner.nextLine();
//        String keyword = scanner.nextLine();
        String file_path = "/Users/kevinqiu/Desktop/JavaToGithub/JavaEE 学习/JavaEE_Learning_1/src/main/java";
        String keyword = "Demo_01";
//        System.out.println(file_path);
        delete_file_keywords(file_path, keyword);
    }
}
