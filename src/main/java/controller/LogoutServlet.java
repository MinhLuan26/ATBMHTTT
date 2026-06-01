package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet chịu trách nhiệm Đăng xuất
 */
@WebServlet("/logout") // <-- Ánh xạ với URL /logout
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Lấy Session hiện tại.
        // Giải quyết Khó khăn (Quy tắc 3):
        // Dùng request.getSession(false) có nghĩa là:
        // "Hãy tìm session hiện tại. Nếu không có (người dùng chưa đăng nhập) 
        // thì trả về NULL, CHỨ ĐỪNG TẠO MỚI."
        HttpSession session = request.getSession(false); 
        
        // 2. Kiểm tra xem session có tồn tại không
        if (session != null) {
            // Nếu tồn tại, HỦY nó đi
            session.invalidate();
//          System.out.println(">>> DEBUG: Đã hủy Session, đăng xuất thành công.");
        } else {
//          System.out.println(">>> DEBUG: Không có Session để hủy (người dùng chưa đăng nhập).");
        }
        
        // 3. Chuyển hướng người dùng về trang chủ
        // Dùng sendRedirect để thay đổi URL trên trình duyệt
        response.sendRedirect(request.getContextPath());
    }

    // Chúng ta không cần doPost cho chức năng logout
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}