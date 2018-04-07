package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;
import lv.tsi.javacourses.bookshelf.entities.Reservation;
import lv.tsi.javacourses.bookshelf.entities.Status;
import lv.tsi.javacourses.bookshelf.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Named
public class BookDetailsForm {
    private static Logger logger = Logger.getLogger("BookDetailsForm");
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;

    private Long bookId;
    private Book book;

    @Transactional
    public void findBook() {
        book = em.find(Book.class, bookId);
    }

    @Transactional
    public String reserve() {
        logger.info("RESERVATION STARTED");
        User user = currentUser.getSignedInUser();
        findBook();
        logger.info("User " + user.getId());
        logger.info("Book " + book.getId());

        List<Reservation> reservations = em.createQuery(
                "select r from Reservation r " +
                        "where r.book = :book " +
                        "and r.user = :user " +
                        "and r.status <> 'RELEASED'")
                .setParameter("book", book)
                .setParameter("user", user)
                .getResultList();

        if (!reservations.isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage("Already reserved"));
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setStatus(Status.WAIT);

        em.persist(reservation);
        return "/user-space/mybooks.xhtml?faces-redirect=true";
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
