package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.controls.UserControl;
import lv.tsi.javacourses.bookshelf.controls.Util;
import lv.tsi.javacourses.bookshelf.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class LoginForm {
    private static final Logger logger = LoggerFactory.getLogger(LoginForm.class);
    @Inject
    private UserControl userControl;
    @Inject
    private CurrentUser currentUser;
    @Inject
    private HttpServletRequest request;

    private String email;
    private String password;

    public String signIn() {
        User user = userControl.findUserByEmail(email, true);
        if (user == null) {
            Util.addError("login:email", "Unknown email");
            return null;
        }
        try {
            request.login(email, password);
            currentUser.setSignedInUser(user);
            logger.debug("User {} is signed in", user);
            return "/login.xhtml?faces-redirect=true";
        } catch (ServletException e) {
            logger.error("Sign in error", e);
            Util.addError("login:password", "Wrong password");
        }
        return null;
    }

    public void signOut() {
        try {
            request.logout();
            currentUser.setSignedInUser(null);
        } catch (ServletException e) {
            logger.error("Sign out error", e);
        }
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
