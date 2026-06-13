package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

import database.DBContext; // Import thêm kết nối DB
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
        User user = userDAO.findByEmail(email);
        if (user == null) {
            String insertSql = "INSERT INTO users (fullname, email, password, phone, role, created_at) VALUES (?, ?, ?, ?, ?, GETDATE())";
            
            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                
                String shortName = email.split("@")[0];
                ps.setString(1, "Thành viên (" + shortName + ")");
                ps.setString(2, email);
                ps.setString(3, BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
                ps.setString(4, "0123456789");
                ps.setString(5, "BUYER");
                
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newUserId = generatedKeys.getInt(1);
                            user = new User();
                            user.setId(newUserId);
                            user.setEmail(email);
                            user.setFullName("Thành viên (" + shortName + ")");
                            user.setRole("BUYER");
                            user.setPhone("0123456789");
                            System.out.println("====== ĐÃ TỰ ĐỘNG TẠO USER THẬT TRONG DB VỚI ID: " + newUserId + " ======");
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Lỗi tự động tạo tài khoản thật: " + e.getMessage());
                e.printStackTrace();
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        Cart sessionCart = (Cart) session.getAttribute("cart");
        Cart dbCart = new Cart();
        if (user != null) {
            var dbCartItems = cartDAO.getCartByUserId(user.getId());
            if (dbCartItems != null) {
                dbCartItems.values().forEach(item -> dbCart.add(item.getBook(), item.getQuantity()));
            }
        }

        if (sessionCart != null) {
            final User finalUser = user;
            sessionCart.getItems().forEach(item -> {
                dbCart.add(item.getBook(), item.getQuantity());
                if (finalUser != null) {
                    cartDAO.addItemToCart(finalUser.getId(), item.getBook().getId(), item.getQuantity());
                }
            });
        }
        session.setAttribute("cart", dbCart);
        session.setMaxInactiveInterval(30 * 60);
        response.sendRedirect(request.getContextPath());
    }
}