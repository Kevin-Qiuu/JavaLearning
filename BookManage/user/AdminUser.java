package user;

import book.BookList;
import operation.*;

import java.util.Scanner;

public class AdminUser extends User{
    public AdminUser(String name, int age, BookList books) {
        super(name, age, books);
        // 初始化可发生行为列表
        work_list = new IOperation[]{
                new ExitOperation(),
                new FindOperation(),
                new AddOperation(),
                new DeleteOperation(),
                new ShowOperation()
        }; // 使用匿名对象
    }

    public void menu(){
       Scanner in = new Scanner(System.in);
       System.out.println("******************************");
            System.out.println("欢迎 " + this.getName() +
                    " 来到本系统，用户年龄 " + this.getAge() + " 岁");
        while(true) {
            System.out.println("******** 管理员用户菜单 ********");
            System.out.println("1. 查找图书");
            System.out.println("2. 新增图书");
            System.out.println("3. 删除图书");
            System.out.println("4. 展示图书");
            System.out.println("0. 退出系统");
            System.out.println("******************************");
            int work_index = in.nextInt();
//            System.out.println("选择操作 " + work_index);
            work_list[work_index].work(books);
        }
    }
}
