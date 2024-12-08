package operation;

import book.Book;
import book.BookList;

import java.util.Scanner;

public class AddOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("AddOperation");
        System.out.println("依次输入所要添加的书名、作者、价格以及类型：");
        String[] in_ret = new Scanner(System.in).nextLine().split(" ");
        Book add_book = new Book(in_ret[0],in_ret[1],Integer.parseInt(in_ret[2]),in_ret[3]);
        books.setBook(books.getUsedSize(), add_book);
        books.setUsedSize(books.getUsedSize() + 1);
        System.out.println("添加成功～");
    }
}
