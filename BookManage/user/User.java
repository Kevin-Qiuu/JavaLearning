package user;

import book.BookList;
import operation.IOperation;

public class User {
    private String name;
    private int age;
    BookList books;
    IOperation[] work_list;

    public User(String name, int age, BookList books) {
        this.name = name;
        this.age = age;
        this.books = books;
    }

    public void menu(){
        // pass
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
