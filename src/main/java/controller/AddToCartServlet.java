package controller;

import java.io.IOException;


import java.util.Map;

import dao.BookDAOImpl;
import dao.CartDAOImpl;
import dao.IBookDAO;
import dao.ICartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.Cart;
import model.CartItem;
import model.User;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    private IBookDAO bookDAO = new BookDAOImpl();
    private ICartDAO cartDAO = new CartDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookId;
        int quantity = 1;

        try {
            bookId = Integer.parseInt(request.getParameter("bookId"));
            if (request.getParameter("quantity") != null) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
                if (quantity < 1) quantity = 1;
            }
        } catch (Exception e) {
            response.sendRedirect("index.jsp");
            return;
        }

        Book book = bookDAO.findBookId(bookId);
        if (book == null) {
            response.sendRedirect("cart.jsp");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Object cartObj = session.getAttribute("cart");
        Cart cart;

        if (cartObj == null || !(cartObj instanceof Cart)) {
            cart = new Cart();              // tạo cart mới
            session.setAttribute("cart", cart);
        } else {
            cart = (Cart) cartObj;
        }

        if (user == null) {
            cart.add(book, quantity);
        } else {
            cartDAO.addItemToCart(user.getId(), bookId, quantity);
            Map<Integer, CartItem> dbCart = cartDAO.getCartByUserId(user.getId());
            cart.clear();
            for (CartItem item : dbCart.values()) {
                cart.add(item.getBook(), item.getQuantity());
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}