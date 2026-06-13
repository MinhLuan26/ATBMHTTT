package dao;

import java.math.BigDecimal;
import java.util.List;

import model.Book;

public interface IBookDAO {
    List<Book> getFeaturedBooks();
    Book findBookId(int id);
    List<Book> search(String keyword);
    List<Book> searchSuggest(String keyword);
    List<Book> filter(BigDecimal minPrice, BigDecimal maxPrice, String condition);
    int insertBook(Book b);
    List<Book> getBooksByIds(List<Integer> ids);
}