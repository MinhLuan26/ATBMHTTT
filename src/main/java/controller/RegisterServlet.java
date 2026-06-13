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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserDAO userDao;
    public RegisterServlet() {
        super();
        this.userDao = new UserDAOImpl();
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String fullName =  request.getParameter("fullname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		if(role == null || !role.equals("SELLER")) {
			role = "BUYER";
		}
		User newUser = new User();
		newUser.setFullName(fullName);
		newUser.setEmail(email);
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
		newUser.setPassword(hashedPassword);
		newUser.setPhone(phone);
		newUser.setRole(role);
		boolean isSuccess = false;
		try {
			isSuccess = userDao.register(newUser);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(isSuccess) {
			response.sendRedirect("login.jsp");
		}else {
			request.setAttribute("errorMessage", "Đăng ký thất bại! Email có thể đã tồn tại.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}