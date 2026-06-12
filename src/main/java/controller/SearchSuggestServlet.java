package controller;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

@WebServlet("/search-suggest")
public class SearchSuggestServlet extends HttpServlet {

    private BookDAOImpl dao = new BookDAOImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String q = req.getParameter("q");
        resp.setContentType("application/json;charset=UTF-8");

        if (q == null || q.trim().isEmpty()) {
            resp.getWriter().print("[]");
            return;
        }

        List<Book> list = dao.searchSuggest(q);
        resp.getWriter().print(new com.google.gson.Gson().toJson(list));
    }
}