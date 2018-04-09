package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;
import lv.tsi.javacourses.bookshelf.entities.Reservation;
import lv.tsi.javacourses.bookshelf.entities.Status;

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
import java.util.stream.Collectors;

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
        canTake = em.createNamedQuery(Reservation.FIRST_IN_QUEUE, Reservation.class)
                .setParameter("user", currentUser.getSignedInUser())
                .getResultList()
                .stream()
                .filter(r -> r.getStatus() == Status.WAIT)
                .map(r -> r.getBook())
                .collect(Collectors.toList());

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
