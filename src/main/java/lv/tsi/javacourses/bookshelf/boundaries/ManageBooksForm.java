package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Reservation;
import lv.tsi.javacourses.bookshelf.entities.Status;
import lv.tsi.javacourses.bookshelf.entities.User;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class ManageBooksForm implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private List<User> users;
    private User current;
    private List<Reservation> reservations;

    public void prepareUsers() {
        users = em.createQuery(
                "select distinct r.user from Reservation r " +
                        "where r.status <> 'RELEASED'")
                .getResultList();
    }

    public void prepareReservations(User user) {
        reservations = em.createNamedQuery(Reservation.FIRST_IN_QUEUE)
                .setParameter("user", user)
                .getResultList();
    }

    @Transactional
    public void releaseBook(Reservation res) {
        Reservation reservation = em.merge(res);
        reservation.setStatus(Status.RELEASED);
        prepareReservations(res.getUser());
    }

    @Transactional
    public void giveBook(Reservation res) {
        Reservation reservation = em.merge(res);
        reservation.setStatus(Status.TAKEN);
        prepareReservations(res.getUser());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
