package controller;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

@WebServlet(urlPatterns = {"/search", "/products"})
public class SearchServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws IOException, ServletException {
        String path = rq.getServletPath();
        List<Book> results;
        String titleDisplay = "";

        if ("/products".equals(path)) {
            String category = rq.getParameter("category");
            
            if ("moi".equals(category)) {
                results = bookDAO.getFeaturedBooks();
                titleDisplay = "Sách Mới Nhất";
            } else {
                results = bookDAO.search("");
                titleDisplay = "Tất cả sản phẩm";
            }
        } else {
            String keyword = rq.getParameter("query");
            if (keyword == null) keyword = "";
            results = bookDAO.search(keyword);
            titleDisplay = keyword;
        }
        rq.setAttribute("books", results);
        rq.setAttribute("q", titleDisplay);
        rq.getRequestDispatcher("search.jsp").forward(rq, rs);
    }
}