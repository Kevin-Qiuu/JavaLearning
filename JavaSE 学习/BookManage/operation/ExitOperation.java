package operation;

import book.BookList;

public class ExitOperation implements IOperation{
    @Override
    public void work(BookList books) {
//        System.out.println("ExitOperation");
        System.exit(0);
    }
}
