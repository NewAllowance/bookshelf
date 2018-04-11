package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;
import org.apache.commons.io.IOUtils;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named
public class EditBookForm implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EditBookForm.class.getName());
    @PersistenceContext
    private EntityManager em;
    private Long bookId;
    private Book book;
    private Part imageData;

    public void loadBook() {
        book = em.find(Book.class, bookId);
    }

    @Transactional
    public String updateBook() {
        book = em.merge(book);
        String contentType = imageData.getContentType();
        book.setImageMimeType(contentType);
        try (InputStream in = imageData.getInputStream()) {
            byte[] imageBytes = IOUtils.toByteArray(in);
            book.setImage(imageBytes);
            return "/book.xhtml?faces-redirect=true&id="+book.getId();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Upload error", e);
        }
        return null;
    }

    public Part getImageData() {
        return imageData;
    }

    public void setImageData(Part imageData) {
        this.imageData = imageData;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
