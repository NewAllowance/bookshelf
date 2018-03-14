package lv.tsi.javacourses.bookshelf.boundary;

import lv.tsi.javacourses.bookshelf.entity.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequestScoped
@Named
public class BookDetailsForm {
    @PersistenceContext
    private EntityManager em;

    private Book book;
    private long bookId;

    @Transactional
    public void findBook() {
        book = em.find(Book.class, bookId);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
