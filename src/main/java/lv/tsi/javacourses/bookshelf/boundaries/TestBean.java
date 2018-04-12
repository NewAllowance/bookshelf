package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
    public class TestBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    private List<Book> books;
    private List<Integer> allYears;
    private String selectedYear;
    private Long bookID;

    public void loadBooks(){
        books = em.createQuery("select b from Book b ").getResultList();
        allYears = em.createQuery("select b from Book b ").getResultList();
    }

    public void processBook(){
       Book selectedBook = em.find(Book.class, bookID);
        System.out.println("Book selected: " + selectedBook);
    }

    public void processBookYear() {
        System.out.println("Selected year: " + selectedYear);
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
}
