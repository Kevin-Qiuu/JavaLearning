package learn.Interface_fun;

public class PaperNumComparable implements Comparator<Student>{
    @Override
    public int compare(Student a, Student b) {
        return a.papers_num - b.papers_num;
    }
}
