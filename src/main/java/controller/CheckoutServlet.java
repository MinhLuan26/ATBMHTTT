package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User; 
import utils.Hambam;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object cartObj = session.getAttribute("cart");
        User user = (User) session.getAttribute("user"); // Lấy thông tin user

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

        // --- TÍCH HỢP TẠO MÃ HASH ĐỂ KÝ ĐIỆN TỬ ---
        if (user != null) {
            try {
                // Tạo chuỗi dữ liệu gốc (phải đồng bộ với cấu trúc trong CryptoApp của bạn)
                String rawData = "USER_" + user.getId() + "_TOTAL_" + cart.getTotal();
                
                // Băm bằng SHA-256
                String orderHash = Hambam.hashText(rawData, "SHA-256");
                
                // Gửi mã băm sang JSP
                request.setAttribute("orderHash", orderHash);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("orderHash", "Lỗi tạo mã đơn hàng!");
            }
        }

        request.getRequestDispatcher("payment_confirm.jsp").forward(request, response);
    }
}