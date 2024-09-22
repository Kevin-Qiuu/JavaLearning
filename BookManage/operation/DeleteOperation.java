package operation;

import book.BookList;

import java.util.Scanner;

public class DeleteOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("DeleteOperation");
        System.out.print("输入删除的序号 > ");
        int del_pos = new Scanner(System.in).nextInt();
        if (del_pos < 1 || del_pos > books.getUsedSize()){
            System.out.println("选择失败！");
            return;
        }
        for (int i = del_pos; i < books.getUsedSize(); i++) {
            books.setBook(i - 1, books.getBook(i));
        }
        books.setUsedSize(books.getUsedSize() - 1);
        System.out.println("删除成功～");
    }
}
