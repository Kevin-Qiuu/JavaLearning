package operation;

import book.Book;
import book.BookList;

public class ShowOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("ShowOperation");
        for (int i = 0; i < books.getUsedSize(); ++i){
            System.out.println(books.getBook(i).toString());
        }
    }
}
