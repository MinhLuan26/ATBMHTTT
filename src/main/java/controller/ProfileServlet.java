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

        // 1. Lấy tham số "tab" từ URL (mặc định là 'info')
        String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) {
            tab = "info";
        }

        // 2. Xử lý dữ liệu cho từng Tab
        if (tab.equals("orders")) {
            // Nếu người dùng chọn tab Đơn hàng -> Lấy danh sách đơn hàng
            List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Để nhận tiếng Việt
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            // Lấy dữ liệu mới từ form
            String fullName = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            
            // Cập nhật đối tượng User
            user.setFullName(fullName);
            user.setPhone(phone);
            
            // 1. Cập nhật CSDL
            userDAO.updateUser(user);
            
            // 2. Cập nhật Session (Quan trọng! Để header hiển thị tên mới ngay lập tức)
            session.setAttribute("user", user);
            
            // Gửi thông báo thành công
            request.setAttribute("message", "Cập nhật thông tin thành công!");
        }
        
        
        response.sendRedirect("profile?tab=info");
    }
}
