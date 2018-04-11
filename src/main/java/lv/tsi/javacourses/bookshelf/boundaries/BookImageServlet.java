package lv.tsi.javacourses.bookshelf.boundaries;

import lv.tsi.javacourses.bookshelf.entities.Book;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
@WebServlet(name = "BookImageServlet", urlPatterns = "/book-image")
public class BookImageServlet extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");
        Long id = Long.parseLong(strId);
        Book book = em.find(Book.class, id);
        byte[] imgData = book.getImage();
        response.setContentType(book.getImageMimeType());
        response.setContentLengthLong(imgData.length);
        response.getOutputStream().write(imgData);
    }
}
