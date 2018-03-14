package lv.tsi.javacourses.bookshelf.boundary;

import lv.tsi.javacourses.bookshelf.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class CurrentUser implements Serializable {
    private Long userId = 1L;
    private User user = new User();

    public CurrentUser() {
        user.setId(1L);
        user.setName("John Smith");
    }

    public boolean isSignedIn() {
        return userId != null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
