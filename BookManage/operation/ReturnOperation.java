package operation;

import book.BookList;

import java.util.Scanner;

public class ReturnOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("ReturnOperation");
        System.out.println("输入所要归还的图书 > ");
        new ShowOperation().work(books); // 方法内只使用一次的变量不妨直接采用匿名对象
        System.out.print("输入所要归还图书的名字 > ");
        String bookName = new Scanner(System.in).nextLine();
        for (int i = 0; i < books.getUsedSize(); i++) {
            if (books.getBook(i).getName().equals(bookName)){
                if (books.getBook(i).getBorrowed()){
                    books.getBook(i).setBorrowed(false);
                    System.out.println("成功归还～");
                    return;
                }
                System.out.println("当前图书未被借出，归还失败～");
                return;
            }
        }
        System.out.println("暂无此书，归还失败～");
    }
}
