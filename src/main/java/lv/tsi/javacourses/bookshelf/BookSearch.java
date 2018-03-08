package lv.tsi.javacourses.bookshelf;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Named
public class BookSearch {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private BookSearchForm bookSearchForm;

    public List<Book> getAllBooks() {
        Query q = em.createQuery("SELECT b FROM Book b");
        return q.getResultList();
    }

    public void doSearch() {
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.author = :name");
        q.setParameter("name", bookSearchForm.getTerm());
        bookSearchForm.setSearchResult(q.getResultList());
    }

}
