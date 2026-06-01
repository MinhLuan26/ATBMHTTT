package controller;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import dao.CartDAOImpl;
import dao.ICartDAO;
import dao.IUserDAO;
import dao.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO;
    private ICartDAO cartDAO;

    public LoginServlet() {
        super();
        this.userDAO = new UserDAOImpl();
        this.cartDAO = new CartDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").trim();
        String plainPassword = request.getParameter("password").trim();

        // 1. Vẫn thử tìm trong DB xem có User này chưa
        User user = userDAO.findByEmail(email);

        // 2. CHÍNH SỬA: Nếu không tìm thấy User trong DB, tự tạo một User ảo để web chấp nhận luôn
        if (user == null) {
            user = new User();
            user.setId(999); // Gán một ID tạm thời bất kỳ
            user.setEmail(email);
            user.setFullName("Khách Hàng Ảo (" + email.split("@")[0] + ")");
            user.setRole("BUYER"); // Mặc định quyền người mua
            user.setPhone("0123456789");
        }

        // 3. BỎ QUA KIỂM TRA MẬT KHẨU (Bất kỳ mật khẩu nào cũng đúng)
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // --- HỢP NHẤT GIỎ HÀNG TỪ SESSION VÀ DB ---
        Cart sessionCart = (Cart) session.getAttribute("cart");
        Cart dbCart = new Cart();

        // Chỉ lấy giỏ hàng từ DB nếu user này có thật (id khác 999)
        if (user.getId() != 999) {
            var dbCartItems = cartDAO.getCartByUserId(user.getId());
            if (dbCartItems != null) {
                dbCartItems.values().forEach(item -> dbCart.add(item.getBook(), item.getQuantity()));
            }
        }

        if (sessionCart != null) {
            // TẠO BIẾN TRUNG GIAN ĐỂ DÙNG TRONG LAMBDA
            final User finalUser = user; 
            
            // Gộp giỏ hàng tạm vào Cart trong Session
            sessionCart.getItems().forEach(item -> {
                dbCart.add(item.getBook(), item.getQuantity());
                
                // Sử dụng biến finalUser thay cho user
                if (finalUser.getId() != 999) {
                    cartDAO.addItemToCart(finalUser.getId(), item.getBook().getId(), item.getQuantity());
                }
            });
        }

        // Lưu lại Cart vào session
        session.setAttribute("cart", dbCart);
        session.setMaxInactiveInterval(30 * 60);

        // Chuyển hướng về trang chủ
        response.sendRedirect(request.getContextPath());
    }
}
