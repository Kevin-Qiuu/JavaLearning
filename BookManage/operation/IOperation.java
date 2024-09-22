package operation;

import book.BookList;

// 各个操作的接口，统一开发规范
public interface IOperation {
    void work(BookList books);
}
