package controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import dao.BookDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws IOException, ServletException {
        String keyword = rq.getParameter("query");
        if (keyword == null) keyword = "";

        List<Book> results = bookDAO.search(keyword);

        rq.setAttribute("books", results);
        rq.setAttribute("q", keyword);

        rq.getRequestDispatcher("search.jsp").forward(rq, rs);
    }
}