package operation;

import book.BookList;

import java.util.Scanner;

public class BorrowOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("BorrowOperation");
        // 接口不可以直接实例化
        // 可以引用对接该接口实现的类实例化的对象，典型的多态
        // 可以在方法内定义一个匿名内部类，实现对接口方法的重写
        new ShowOperation().work(books); // 方法内只使用一次的变量不妨直接采用匿名对象
        System.out.print("输入所要借阅图书的名字 > ");
        String bookName = new Scanner(System.in).nextLine();
        for (int i = 0; i < books.getUsedSize(); i++) {
            if (books.getBook(i).getName().equals(bookName)){
                if (books.getBook(i).getBorrowed()){
                    System.out.println("此书已经被借走，无法借阅～");
                    return;
                }
                books.getBook(i).setBorrowed(true);
                System.out.println("成功借阅图书 "+books.getBook(i).getName());
                return;
            }
        }
        System.out.println("暂无此书，借阅失败～");
    }
}
