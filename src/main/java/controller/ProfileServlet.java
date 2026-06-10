package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dao.*;
import model.Order;
import model.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO = new UserDAOImpl();
    private IOrderDAO orderDAO = new OrderDAOImpl(); // Khởi tạo DAO đơn hàng

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 🔥 SỬA LỖI: Lấy thông báo từ session nếu có (do doPost chuyển qua)
        String sessionMessage = (String) session.getAttribute("message");
        if (sessionMessage != null) {
            request.setAttribute("message", sessionMessage);
            session.removeAttribute("message"); // Hiển thị xong thì xóa ngay
        }

        // 1. Lấy tham số "tab" từ URL (mặc định là 'info')
        String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) {
            tab = "info";
        }

        // 2. Xử lý dữ liệu cho từng Tab
        if (tab.equals("orders")) {
            // 🔍 THÊM LOG DEBUG: In ra cửa sổ Console của Eclipse để kiểm tra dòng chảy dữ liệu
            System.out.println("\n========== PROFILE SERVLET DEBUG ==========");
            System.out.println("-> Người dùng đang xem Tab Đơn Hàng!");
            System.out.println("-> ID Tài khoản thật hiện tại: " + user.getId());
            System.out.println("-> Email tài khoản: " + user.getEmail());
            
            // Gọi DAO lấy danh sách đơn hàng
            List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
            
            // In kết quả kiểm tra xem DAO trả về bao nhiêu dòng
            System.out.println("-> Số lượng đơn hàng lấy được từ CSDL: " + (orders != null ? orders.size() : 0));
            System.out.println("===========================================\n");
            
            request.setAttribute("orders", orders);
        } 
        else if (tab.equals("favorites")) {
            // (Sau này sẽ lấy danh sách yêu thích ở đây)
        }

        // 3. Gửi "tab hiện tại" sang JSP để nó biết active menu nào
        request.setAttribute("currentTab", tab);
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    // 2. Xử lý Cập nhật thông tin
 // 2. Xử lý Cập nhật thông tin và Cập nhật Khóa
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            // Lấy loại form gửi lên
            String formType = request.getParameter("formType");
            
            if ("updateKey".equals(formType)) {
                // ============================================
                // LUỒNG 1: XỬ LÝ CẬP NHẬT KHÓA CÔNG KHAI
                // ============================================
                String publicKey = request.getParameter("publicKey");
                
                // Gán khóa mới cho User
                user.setPublicKey(publicKey);
                userDAO.updateUser(user);
                
                // Cập nhật session và gửi thông báo
                session.setAttribute("user", user);
                session.setAttribute("message", "Đã cập nhật Khóa công khai thành công! Bạn có thể bắt đầu mua hàng an toàn.");
                
                // Trả về đúng tab Quản lý khóa
                response.sendRedirect("profile?tab=keys");
                return;
                
            } else {
                // ============================================
                // LUỒNG 2: XỬ LÝ CẬP NHẬT THÔNG TIN CÁ NHÂN
                // ============================================
                String fullName = request.getParameter("fullname");
                String phone = request.getParameter("phone");
                
                user.setFullName(fullName);
                user.setPhone(phone);
                userDAO.updateUser(user);
                
                session.setAttribute("user", user);
                session.setAttribute("message", "Cập nhật thông tin cá nhân thành công!");
                
                // Trả về tab Thông tin
                response.sendRedirect("profile?tab=info");
                return;
            }
        }
        
        response.sendRedirect("login.jsp");
    }
}