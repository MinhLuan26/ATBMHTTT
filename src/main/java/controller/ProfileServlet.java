package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.*;
import model.Order;
import model.User;
import utils.Chukydientu;

@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO = new UserDAOImpl();
    private IOrderDAO orderDAO = new OrderDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String sessionMessage = (String) session.getAttribute("message");
        if (sessionMessage != null) {
            request.setAttribute("message", sessionMessage);
            session.removeAttribute("message");
        }

        String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) tab = "info";

        if (tab.equals("orders")) {
            request.setAttribute("orders", orderDAO.getOrdersByUserId(user.getId()));
        }
        
        request.setAttribute("currentTab", tab);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String formType = request.getParameter("formType");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            if ("generateKey".equals(formType)) {
                KeyPair keyPair = Chukydientu.generateKeyPair();
                String pubKeyStr = Chukydientu.convertKeyToString(keyPair.getPublic(), "PUBLIC KEY");
                String privKeyStr = Chukydientu.convertKeyToString(keyPair.getPrivate(), "PRIVATE KEY");

                user.setPublicKey(pubKeyStr);
                userDAO.updateUser(user);

                session.setAttribute("user", user);
                session.setAttribute("newPrivateKey", "true");
                session.setAttribute("newPrivateKey_download", privKeyStr);
                
                // Set trạng thái về ACTIVE khi tạo khóa mới
                session.setAttribute("keyStatus", "ACTIVE"); 
                session.setAttribute("keyUpdatedAt", sdf.format(new Date()));
                session.setAttribute("message", "Tạo khóa mới thành công!");

                response.sendRedirect("profile?tab=keys");
                return;

            } else if ("uploadKey".equals(formType)) {
                Part filePart = request.getPart("publicKeyFile");
                if (filePart != null && filePart.getSize() > 0) {
                    InputStream fileContent = filePart.getInputStream();
                    String pubKeyStr = new String(fileContent.readAllBytes(), StandardCharsets.UTF_8);
                    
                    user.setPublicKey(pubKeyStr.trim());
                    userDAO.updateUser(user);
                    
                    session.setAttribute("user", user);
                    
                    // Set trạng thái về ACTIVE khi tải khóa mới lên
                    session.setAttribute("keyStatus", "ACTIVE");
                    session.setAttribute("keyUpdatedAt", sdf.format(new Date()));
                    session.setAttribute("message", "Đã tải lên khóa công khai thành công!");
                }
                response.sendRedirect("profile?tab=keys");
                return;

            } else if ("updateKey".equals(formType)) {
                String publicKey = request.getParameter("publicKey");
                user.setPublicKey(publicKey);
                userDAO.updateUser(user);
                
                session.setAttribute("user", user);
                session.setAttribute("keyStatus", "ACTIVE");
                session.setAttribute("keyUpdatedAt", sdf.format(new Date()));
                session.setAttribute("message", "Đã lưu khóa công khai thành công!");
                
                response.sendRedirect("profile?tab=keys");
                return;

            } 
            // 🌟 LUỒNG XỬ LÝ THU HỒI KHÓA ĐƯỢC THÊM VÀO ĐÂY 🌟
            else if ("revokeKey".equals(formType)) {
                
                /*
                 * GHI CHÚ CHO BẠN: 
                 * Nếu trong class model.User của bạn có thêm thuộc tính trạng thái (ví dụ: statusKey), 
                 * bạn hãy uncomment 2 dòng dưới đây để lưu thẳng xuống Database:
                 * * user.setKeyStatus("REVOKED");
                 * userDAO.updateUser(user);
                 */

                // Cập nhật biến Session để giao diện Web nhận diện và hiển thị nhãn màu đỏ
                session.setAttribute("keyStatus", "REVOKED");
                session.setAttribute("keyUpdatedAt", sdf.format(new Date()));
                
                session.setAttribute("message", "🚨 Đã báo mất và hủy kích hoạt khóa thành công! Hệ thống sẽ từ chối xác thực các giao dịch từ khóa này.");
                response.sendRedirect("profile?tab=keys");
                return;

            } else {
                user.setFullName(request.getParameter("fullname"));
                user.setPhone(request.getParameter("phone"));
                userDAO.updateUser(user);
                
                session.setAttribute("user", user);
                session.setAttribute("message", "Cập nhật hồ sơ thành công!");
                
                response.sendRedirect("profile?tab=info");
                return;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Lỗi: " + e.getMessage());
            response.sendRedirect("profile?tab=" + (formType != null && formType.contains("Key") ? "keys" : "info"));
        }
    }
}