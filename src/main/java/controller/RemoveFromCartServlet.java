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

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICartDAO cartDAO = new CartDAOImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        int bookId = Integer.parseInt(request.getParameter("bookId"));
	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");
	        
	        // Luôn luôn lấy ra đối tượng model.Cart từ session
	        Cart cart = (Cart) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new Cart();
	        }

	        if (user == null) {
	            // Kịch bản 1: Khách chưa đăng nhập -> Xóa trực tiếp trong thuộc tính Cart của session
	            cart.remove(bookId);
	        } else {
	            // Kịch bản 2: User đã đăng nhập -> Xóa bản ghi trong CSDL
	            cartDAO.removeItemFromCart(user.getId(), bookId);

	            // Đồng bộ trực tiếp: Xóa luôn phần tử trong Cart session hiện tại để giảm tải việc truy vấn lại DB
	            cart.remove(bookId);
	        }
	        
	        // Lưu lại giỏ hàng chuẩn vào session
	        session.setAttribute("cart", cart);
	        response.sendRedirect("cart.jsp");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        response.sendRedirect("cart.jsp");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}