package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")// ánh xạ với Ủl /home
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookDAO bookDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
    	// Giải quyết Khó khăn (Quy tắc 3): Lại "tự đi chợ" (new)
        this.bookDAO = new BookDAOImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Gọi Model (DAO) để lấy dữ liệu 
		List<Book> bookList = bookDAO.getFeaturedBooks();
		// gửi dữ liệu sang view(JSP)
		// "request.setAttribute" là "cầu nối" từ Controller -> View
        request.setAttribute("featuredBooks", bookList);
        // CHUYỂN TIẾP (FORWARD) ĐẾN VIEW
        request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
