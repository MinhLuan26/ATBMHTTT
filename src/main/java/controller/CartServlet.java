package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dao.BookDAOImpl;
import model.Book;
import model.Cart;
import model.CartItem;

import java.io.IOException;

/**
 * CartServlet handles adding/removing/updating items in cart saved in session.
 *
 * Supported actions (via request parameter "action"):
 *  - add : adds book to cart (requires id and optional qty)
 *  - remove : removes book from cart (requires id)
 *  - update : updates quantities from form parameters (POST)
 *
 * All operations redirect back to cart.jsp to show current cart.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    /** Handle GET for add/remove and show */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) { cart = new Cart(); session.setAttribute("cart", cart); }

        if ("add".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                int qty = 1;
                if (req.getParameter("qty") != null) qty = Integer.parseInt(req.getParameter("qty"));
                Book b = bookDAO.findBookId(id);
                if (b != null) {
                    cart.add(b, qty);
                }
            } catch (Exception e) {
                // ignore bad input
            }
            resp.sendRedirect("cart.jsp");
            return;
        } else if ("remove".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                cart.remove(id);
            } catch (Exception e) {}
            resp.sendRedirect("cart.jsp");
            return;
        }

        // default: forward to cart.jsp
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    /** Handle POST for bulk update of quantities from cart form */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) { resp.sendRedirect("cart.jsp"); return; }

        // Expect parameter names like qty_<bookId>
        for (String name : req.getParameterMap().keySet()) {
            if (name.startsWith("qty_")) {
                try {
                    int bookId = Integer.parseInt(name.substring(4));
                    int qty = Integer.parseInt(req.getParameter(name));
                    cart.update(bookId, qty);
                } catch (Exception e) { /* ignore bad input */ }
            }
        }
        resp.sendRedirect("cart.jsp");
    }
}
