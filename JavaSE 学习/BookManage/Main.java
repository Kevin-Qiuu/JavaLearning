import book.BookList;
import user.AdminUser;
import user.NormalUser;
import user.User;

import java.util.Scanner;
import java.util.function.BooleanSupplier;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main2(String[] args) {
        BookList books = new BookList();
        User user = new NormalUser("qiu", 12, books);
        user.menu();
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("请选择你的身份: 1.管理员 2.普通用户");
        int id_num = in.nextInt();
        System.out.println("请输入你的姓名和年龄：");
        in.nextLine();
        String[] in_ret = in.nextLine().split(" ");
        User user = null;
        BookList books = new BookList();
        switch (id_num){
            case 1:{
                user = new AdminUser(in_ret[0], Integer.parseInt(in_ret[1]), books);
                break;
            }
            case 2:{
                user = new NormalUser(in_ret[0], Integer.parseInt(in_ret[1]), books);
                break;
            }
        }
        if (user != null) {
            user.menu();
        }


    }
}