package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;
import lv.tsi.javacourses.bookshelf.entities.Reservation;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ViewScoped
@Named
public class MyBooksForm implements Serializable {
    @PersistenceContext
    private transient EntityManager em;
    @Inject
    private CurrentUser currentUser;

    private List<Book> taken;
    private List<Book> canTake;
    private List<Book> await;
    private Set<Book> read;


    public void prepareMyBooks() {
        canTake = em.createQuery(
                "select mr.book from Reservation mr " +
                        "where mr.user = :user " +
                        "and mr.status = 'WAIT' " +
                        "and mr.id in (" +
                        "   select min(r.id) from Reservation r " +
                        "   where r.book in (" +
                        "       select ur.book from Reservation ur " +
                        "       where ur.user = :user " +
                        "       and (r.status = 'WAIT' or r.status = 'TAKEN')" +
                        "   ) group by r.book" +
                        ") order by mr.id")
                .setParameter("user", currentUser.getSignedInUser())
                .getResultList();

        List<Reservation> myReservations = em.createQuery(
                "select r from Reservation r " +
                        "where r.user = :user " +
                        "order by r.id")
                .setParameter("user", currentUser.getSignedInUser())
                .getResultList();

        taken = new ArrayList<>(10);
        await = new ArrayList<>(10);
        read = new LinkedHashSet<>(10);

        for (Reservation r : myReservations) {
            Book book = r.getBook();
            switch (r.getStatus()) {
                case TAKEN:
                    taken.add(book);
                    break;
                case WAIT:
                    if (!canTake.contains(book)) {
                        await.add(book);
                    }
                    break;
                case RELEASED:
                    read.add(book);
            }
        }
    }

    public List<Book> getTaken() {
        return taken;
    }

    public List<Book> getCanTake() {
        return canTake;
    }

    public List<Book> getAwait() {
        return await;
    }

    public Set<Book> getRead() {
        return read;
    }
}
