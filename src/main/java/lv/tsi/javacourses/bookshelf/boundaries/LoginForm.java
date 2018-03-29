package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequestScoped
@Named
public class LoginForm {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;

    private String email;
    private String password;

    @Transactional
    public String login() {
        TypedQuery<User> query = em.createQuery(
                "select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            User u = query.getSingleResult();
            if (Objects.equals(u.getPassword(), password)) {
                currentUser.setSignedInUser(u);
                currentUser.setUserId(u.getId());
                return "/index.xhtml";
            } else {
                FacesContext.getCurrentInstance()
                        .addMessage("login:password",
                                new FacesMessage("Incorrect password"));
            }
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance()
                    .addMessage("login:email",
                            new FacesMessage("Email is not registered"));
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
