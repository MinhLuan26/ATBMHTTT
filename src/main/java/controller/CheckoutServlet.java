package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object cartObj = session.getAttribute("cart");

        if (!(cartObj instanceof Cart)) {
            session.removeAttribute("cart");
            response.sendRedirect("cart.jsp");
            return;
        }

        Cart cart = (Cart) cartObj;
        if (cart.getSize() == 0) {
            response.sendRedirect("cart.jsp");
            return;
        }

        request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
    }
}
