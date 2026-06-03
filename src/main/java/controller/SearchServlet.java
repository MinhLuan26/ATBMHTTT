package controller;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

// Bổ sung thêm "/products" vào đây để Servlet này gánh luôn đường dẫn bị lỗi
@WebServlet(urlPatterns = {"/search", "/products"})
public class SearchServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws IOException, ServletException {
        
        // Lấy đường dẫn hiện tại mà người dùng đang truy cập
        String path = rq.getServletPath();
        List<Book> results;
        String titleDisplay = "";

        if ("/products".equals(path)) {
            // Xử lý khi người dùng bấm vào menu "Sản phẩm" hoặc "Sách Mới Nhất"
            String category = rq.getParameter("category");
            
            if ("moi".equals(category)) {
                // Tận dụng hàm getFeaturedBooks() đã có để lấy sách mới (ORDER BY id DESC)
                results = bookDAO.getFeaturedBooks();
                titleDisplay = "Sách Mới Nhất";
            } else {
                // Tận dụng hàm search("") với chuỗi rỗng để lấy TẤT CẢ sách
                results = bookDAO.search("");
                titleDisplay = "Tất cả sản phẩm";
            }
        } else {
            // Xử lý luồng tìm kiếm bình thường (/search)
            String keyword = rq.getParameter("query");
            if (keyword == null) keyword = "";
            results = bookDAO.search(keyword);
            titleDisplay = keyword;
        }

        // Đẩy dữ liệu sang trang search.jsp để hiển thị
        rq.setAttribute("books", results);
        rq.setAttribute("q", titleDisplay); // q dùng để hiển thị tiêu đề trên giao diện

        rq.getRequestDispatcher("search.jsp").forward(rq, rs);
    }
}