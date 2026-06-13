package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.Map;

import dao.CartDAOImpl;
import dao.ICartDAO;

@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICartDAO cartDAO = new CartDAOImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        int bookId = Integer.parseInt(request.getParameter("bookId"));
	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");
	        Cart cart = (Cart) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new Cart();
	        }

	        if (user == null) {
	            cart.remove(bookId);
	        } else {
	            cartDAO.removeItemFromCart(user.getId(), bookId);
	            cart.remove(bookId);
	        }
	        session.setAttribute("cart", cart);
	        response.sendRedirect("cart.jsp");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        response.sendRedirect("cart.jsp");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}