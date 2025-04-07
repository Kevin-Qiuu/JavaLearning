package ee_10_document_io;

import java.io.*;
import java.util.Scanner;

public class Demo_04_copy_file {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input source file path: ");
        String source_file_path = scanner.nextLine();
        File source_file = new File(source_file_path);

        // 校验源路径文件
        if (!source_file.exists()) {
            System.out.println("Can not find source file " + source_file.getName() + " .");
            return;
        }
        if (!source_file.isFile()) {
            System.out.println("Source file " + source_file.getName() + " is not a common file.");
            return;
        }

        System.out.print("Input target file path: ");
        String target_file_path = scanner.nextLine();
        File target_file = new File(target_file_path);

        if (target_file.exists()) {
            // 校验目标路径文件
            if (target_file.isDirectory()) {
                System.out.println("Target file " + target_file_path + " is a directory which can not copy.");
                return;
            }
            if (target_file.isFile()) {
                System.out.println("The file is exist, will you cover the old file ? [Y]/[N]");
                String opt = scanner.nextLine().toLowerCase();
                if (!opt.equals("y")) {
                    System.out.println("The copy execution has been canceled.");
                    return;
                }

            }
        }

        // copy operation
        // 使用数据流读取和写入数据时可以使用 try 代码块，在程序执行完 try 代码块时会自动释放这两个数据流
        try(InputStream inputStream = new FileInputStream(source_file);
            OutputStream outputStream = new FileOutputStream(target_file)){
            byte[] bytes = new byte[1024];  // 创建 1KB 的空间进行过渡
            int len = 0;
            while (true){
                len = inputStream.read(bytes);  // 不指定 off 的话则默认从 0 开始写
                if (len == -1){
                    System.out.println("Copy successfully!");
                    break;
                }
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();  // 强制清空缓存池，迫使操作系统把数据从缓冲池中写入文件中
        }

        scanner.close();

    }


}
