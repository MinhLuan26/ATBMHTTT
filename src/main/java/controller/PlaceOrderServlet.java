package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.User;

import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            // nếu giỏ hàng trống, quay lại trang giỏ hàng
            response.sendRedirect("cart.jsp");
            return;
        }

        String address = request.getParameter("address");
        if (address == null || address.trim().isEmpty()) {
            // nếu địa chỉ trống, quay lại trang thanh toán
            request.setAttribute("error", "Vui lòng nhập địa chỉ nhận hàng!");
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
            return;
        }

        // --- Ở đây bạn có thể gọi DAO để lưu đơn hàng vào CSDL ---
        // VD: orderDAO.placeOrder(user.getId(), cart, address);

        // Xóa giỏ hàng trong session sau khi thanh toán thành công
        cart.clear();
        session.setAttribute("cart", cart);

        // Chuyển hướng sang trang thành công
        response.sendRedirect("payment_success.jsp");
    }
}
