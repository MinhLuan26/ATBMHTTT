package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.User;
import dao.OrderDAO;
import dao.UserDAOImpl;
import utils.Hambam;
import utils.Chukydientu;

import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        // 1. Lấy thông tin từ form
        String address = request.getParameter("address");
        String signatureBase64 = request.getParameter("digitalSignature");

        // Gửi lại dữ liệu vào request để nếu lỗi thì form không bị xóa trắng
        request.setAttribute("address", address);
        request.setAttribute("digitalSignature", signatureBase64);

        // Validate cơ bản
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập địa chỉ nhận hàng!");
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
            return;
        }

        try {
            // 2. Tái tạo lại chuỗi Dữ liệu gốc của đơn hàng (Phải khớp với CheckoutServlet)
        	String orderRawData = "USER_" + user.getId() + "_TOTAL_" + cart.getTotal();
            String orderHash = Hambam.hashText(orderRawData, "SHA-256");

            // 3. Lấy Public Key của user từ CSDL
            String publicKeyStr = userDAO.getPublicKeyByUserId(user.getId());

            if (publicKeyStr == null || publicKeyStr.isEmpty()) {
                request.setAttribute("error", "Bạn chưa cập nhật Khóa Công Khai (Public Key) trong hồ sơ!");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
                return;
            }

            // 4. KIỂM TRA CHỮ KÝ (Sử dụng Chukydientu.verifyData)
            boolean isValid = Chukydientu.verifyData(orderHash, signatureBase64, publicKeyStr);

            if (!isValid) {
                request.setAttribute("error", "Chữ ký điện tử không hợp lệ! Hãy kiểm tra lại khóa hoặc nội dung đơn hàng.");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
                return;
            }

            // 5. Nếu chữ ký ĐÚNG -> Tiến hành lưu đơn hàng vào DB
            int orderId = orderDAO.createOrder(user.getId(), cart, address);

            if (orderId > 0) {
                cart.clear();
                session.setAttribute("cart", cart);
                
                // Truyền orderId sang trang success để hiển thị mã đơn
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("checkout_success.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi lưu đơn hàng vào hệ thống!");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống mã hóa: " + e.getMessage());
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
        }
    }
}