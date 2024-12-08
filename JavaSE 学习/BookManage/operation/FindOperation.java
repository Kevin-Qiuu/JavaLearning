package operation;

import book.BookList;

import java.util.Scanner;

public class FindOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("FindOperation");
        System.out.print("输入查询的图书名称 > ");
        String bookName = new Scanner(System.in).nextLine();
        for (int i = 0; i < books.getUsedSize(); i++) {
            if (books.getBook(i).getName().equals(bookName)){
                System.out.println("查找成功，信息如下：");
                System.out.println(books.getBook(i).toString());
                return;
            }
        }
        System.out.println("查找失败，暂无此书。");
    }
}
