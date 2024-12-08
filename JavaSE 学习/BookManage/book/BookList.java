package book;

public class BookList {
    private Book[] books = new Book[10];
    private int usedSize;

    public BookList() {
        // 初始化时即向内存空间写入 bookList 存储的书本
        // 有机会将其改写成直接存入存储空间，因为内存会自动刷新
        books[0] = new Book("矩阵分析引论","罗家洪",25,"数学");
        books[1] = new Book("三国演义","罗贯中",20,"文学");
        books[2] = new Book("鬼吹灯","佚名",30,"恐怖");

        usedSize = 3;
    }

    public Book getBook(int pos) {
        if (pos < 0 || pos >= usedSize)
            return null;

        return books[pos];
    }

    public void setBook(int pos, Book book) {
        this.books[pos] = book;
    }

    public int getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(int usedSize) {
        this.usedSize = usedSize;
    }
}
