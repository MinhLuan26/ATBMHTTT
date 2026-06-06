package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.User;
import dao.OrderDAO;
import dao.UserDAOImpl;
import utils.Chukydientu; // File bạn vừa thêm vào web
import utils.Hambam;      // File bạn vừa thêm vào web

import java.io.IOException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 1. Lấy thông tin từ form
        String address = request.getParameter("address");
        String signatureBase64 = request.getParameter("digitalSignature"); // Lấy chữ ký từ form

        // 2. Tái tạo lại chuỗi Dữ liệu gốc của đơn hàng (Giống hệt lúc tạo ra trên JSP)
        // Ví dụ: "USER_3_TOTAL_100000_ADDR_HCM"
        String orderRawData = "USER_" + user.getId() + "_TOTAL_" + cart.getTotal() + "_ADDR_" + address;
        
        try {
            // Lấy mã băm (Hash)
            String orderHash = Hambam.hashText(orderRawData, "SHA-256");

            // 3. Lấy Public Key của user từ CSDL (Bạn cần code thêm hàm getPublicKey(userId) trong DAO)
            String publicKeyStr = userDAO.getPublicKeyByUserId(user.getId());

            if(publicKeyStr == null || publicKeyStr.isEmpty()) {
                request.setAttribute("error", "Bạn chưa cập nhật Khóa Công Khai (Public Key) trong hồ sơ!");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
                return;
            }

            // 4. KIỂM TRA CHỮ KÝ (Dùng file Chukydientu.java của bạn)
            boolean isValid = Chukydientu.verifyData(orderHash, signatureBase64, publicKeyStr);

            if (!isValid) {
                // Nếu chữ ký sai, từ chối thanh toán
                request.setAttribute("error", "Chữ ký điện tử không hợp lệ! Vui lòng kiểm tra lại tool CryptoApp.");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
                return;
            }

            // 5. Nếu chữ ký ĐÚNG -> Tiến hành lưu đơn hàng vào DB
            int orderId = orderDAO.createOrder(user.getId(), cart, address);

            if (orderId > 0) {
                cart.clear();
                session.setAttribute("cart", cart);
                response.sendRedirect("checkout_success.jsp"); // hoặc payment_success.jsp
            } else {
                request.setAttribute("error", "Lỗi lưu đơn hàng vào hệ thống!");
                request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống mã hóa: " + e.getMessage());
            request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
        }
    }
}