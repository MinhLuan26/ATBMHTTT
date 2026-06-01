package controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;

import dao.BookDAOImpl;
import dao.IBookDAO;
import dao.WishlistDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {

    private WishlistDAO dao = new WishlistDAO();
    private IBookDAO bookDAO = new BookDAOImpl();


    // ===============================
    // HIỂN THỊ TRANG WISHLIST (GET)
    // ===============================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        Object u = session.getAttribute("user");

        if (u == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = ((model.User) u).getId();

        // Lấy book_id trong wishlist
        List<Integer> ids = null;

    
            try {
				ids = dao.getAll(userId);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // Optional: chuyển sang trang lỗi hoặc báo lỗi
        
        // Lấy thông tin sách
        List<Book> books = bookDAO.getBooksByIds(ids);
        

        // Gửi sang JSP
        req.setAttribute("books", books);

        req.getRequestDispatcher("wishlist.jsp").forward(req, resp);
    }

    // ===============================
    // XỬ LÝ AJAX TOGGLE (POST)
    // ===============================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json; charset=UTF-8");

        HttpSession session = req.getSession();
        Object u = session.getAttribute("user");
        JsonObject json = new JsonObject();

        if (u == null) {
            json.addProperty("success", false);
            json.addProperty("message", "Bạn cần đăng nhập để sử dụng wishlist.");
            resp.getWriter().write(json.toString());
            return;
        }

        int userId = ((model.User) u).getId();

        String action = req.getParameter("action");
        String bookIdParam = req.getParameter("bookId");

        if (bookIdParam == null || bookIdParam.isEmpty()) {
            json.addProperty("success", false);
            json.addProperty("message", "Thiếu tham số book id.");
            resp.getWriter().write(json.toString());
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam);
        } catch (NumberFormatException e) {
            json.addProperty("success", false);
            json.addProperty("message", "Book ID không hợp lệ.");
            resp.getWriter().write(json.toString());
            return;
        }

        try {
            if ("toggle".equals(action)) {
                boolean exists = dao.exists(userId, bookId);

                if (exists) {
                    dao.remove(userId, bookId);
                    json.addProperty("status", "removed");
                    json.addProperty("message", "Đã xóa khỏi danh sách yêu thích");
                } else {
                    dao.add(userId, bookId);
                    json.addProperty("status", "added");
                    json.addProperty("message", "Đã thêm vào danh sách yêu thích");
                }

                json.addProperty("success", true);
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Action không hợp lệ!");
            }

            resp.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.addProperty("success", false);
            json.addProperty("message", "Lỗi server!");
            resp.getWriter().write(json.toString());
        }
    }
}
