package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.User;
import dao.OrderDAO; // 1. Khai báo import lớp OrderDAO của bạn

import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // 2. Khởi tạo đối tượng orderDAO
    private OrderDAO orderDAO = new OrderDAO(); 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        // Kiểm tra người dùng đã đăng nhập chưa để lấy ID
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String address = request.getParameter("address");
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập địa chỉ nhận hàng!");
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
            return;
        }

        // 3. THỰC HIỆN GỌI DAO ĐỂ LƯU VÀO CSDL TẠI ĐÂY
        int orderId = orderDAO.createOrder(user.getId(), cart, address);

        if (orderId > 0) {
            // Xóa giỏ hàng trong session sau khi đã lưu DB thành công
            cart.clear();
            session.setAttribute("cart", cart);

            // Nếu muốn hiển thị mã đơn hàng ở trang checkout_success.jsp như bạn đã thiết kế:
            // Bạn có thể chuyển hướng kèm param hoặc dùng forward. 
            // Ở đây tạm thời giữ nguyên sendRedirect sang trang thành công:
            response.sendRedirect("payment_success.jsp");
        } else {
            // Xử lý nếu lưu thất bại (lỗi kết nối DB chẳng hạn)
            request.setAttribute("error", "Có lỗi xảy ra trong quá trình xử lý đơn hàng!");
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
        }
    }
}