package lv.tsi.javacourses.bookshelf.entities;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
@NamedQuery(name = Reservation.FIRST_IN_QUEUE, query =
        "select mr from Reservation mr " +
                "where mr.user = :user " +
                "and mr.id in (" +
                "   select min(r.id) from Reservation r " +
                "   where r.book in (" +
                "       select ur.book from Reservation ur " +
                "       where ur.user = :user " +
                "       and (r.status = 'WAIT' or r.status = 'TAKEN')" +
                "   ) group by r.book" +
                ") order by mr.id")
public class Reservation {
    public static final String FIRST_IN_QUEUE = "first_in_queue";
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
