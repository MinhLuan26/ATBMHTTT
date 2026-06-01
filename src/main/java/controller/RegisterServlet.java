package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;// Annotation này cũng là 1 cách thay cho web.xml
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import dao.IUserDAO;
import dao.UserDAOImpl;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private IUserDAO userDao;
    public RegisterServlet() {
        super();
        // Giải quyết khó khăn (Quy tắc 3): "Tự đi chợ"
        // chúng ta đang tự tạo đối tượng DAO
        // Đây chính là "Tight Coupling" (Khớp nối cứng).
        // Spring Boot sẽ giải quyết việc này bằng @Autowired (DI).
        this.userDao = new UserDAOImpl();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Khi người dùng truy cập /register (gõ URL), chúng ta chỉ CẦN HIỂN THỊ trang JSP.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Chuyển tiếp (forward) yêu cầu đến trang register.jsp
        // "Forward" là một hành động phía server, URL trên trình duyệt không đổi.
        request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Khi người dùng NHẤN NÚT SUBMIT (method="POST") trên form.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Lấy dữ liệu từ form (dựa vào thuộc tính 'name' của input)
		request.setCharacterEncoding("UTF-8");// nhận Tiếng Việt
		String fullName =  request.getParameter("fullname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		// lấy role người dùng chọn(là null thì mặc định là BUYER)
		String role = request.getParameter("role");
		if(role == null || !role.equals("SELLER")) {
			role = "BUYER";// chỉ cho phép đăng ký 2 quyền BUYER or SELLER
		}
		
		// 2. Tạo đối tượng User (JavaBean)
		User newUser = new User();
		newUser.setFullName(fullName);
		newUser.setEmail(email);
		// ===NÂNG CẤP BẢO MẬT===
		// mã hóa trước khi 
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
		newUser.setPassword(hashedPassword);
		newUser.setPhone(phone);
		newUser.setRole(role);
		
		// 3. Gọi DAO để thực hiện logic nghiệp vụ
		boolean isSuccess = false;
		try {
			isSuccess = userDao.register(newUser);
		}catch (Exception e) {
			e.printStackTrace();
			// Xử lý lỗi (vd: trùng email)
		}
		// 4. Phản hồi cho người dùng
		if(isSuccess) {
			// Đăng ký thành công -> Chuyển hướng (Redirect) sang trang đăng nhập
            // "Redirect" là bảo trình duyệt đi sang URL mới.
            // Rất quan trọng: Dùng redirect sau POST để tránh lỗi "F5" (tải lại trang)
			response.sendRedirect("login.jsp");
		}else {
			// Đăng ký thất bại
            // Gửi một thông báo lỗi lại trang đăng ký
			request.setAttribute("errorMessage", "Đăng ký thất bại! Email có thể đã tồn tại.");
			// Quay lại trang đăng ký bằng "forward"
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}
