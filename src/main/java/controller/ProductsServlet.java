package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import dao.BookDAOImpl;
import dao.IBookDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBookDAO bookDAO = new BookDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Dùng hàm tìm kiếm với chuỗi rỗng "" để lấy toàn bộ danh sách sách từ DB
        List<Book> allBooks = bookDAO.search(""); 
        
        // 2. Đẩy dữ liệu sang trang JSP
        request.setAttribute("books", allBooks);
        request.setAttribute("title", "Tất cả sản phẩm");
        
        // 3. Tận dụng lại giao diện lưới sách của filter_result.jsp hoặc search.jsp
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}