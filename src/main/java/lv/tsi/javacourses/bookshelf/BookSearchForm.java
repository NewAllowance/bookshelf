package lv.tsi.javacourses.bookshelf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class BookSearchForm {
    private String term;
    private List<Book> searchResult;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Book> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Book> searchResult) {
        this.searchResult = searchResult;
    }
}
