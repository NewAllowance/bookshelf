package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@SessionScoped
@Named
public class CurrentUser implements Serializable {
    @PersistenceContext
    private transient EntityManager em;
    private User signedInUser;
    private String email;
    private String password;


    public boolean isSignedIn() {
        return signedInUser != null;
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public void setSignedInUser(User signedInUser) {
        this.signedInUser = signedInUser;
    }
}
