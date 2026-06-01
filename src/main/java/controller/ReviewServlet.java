package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.ReviewDAO;
import dao.ReviewDAOImpl;
import model.Review;
import model.User;
import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAOImpl();

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rq.setCharacterEncoding("UTF-8"); // Đọc bình luận có dấu Tiếng Việt
        
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("user");
        
        // Nếu chưa đăng nhập thì bắt buộc chuyển qua trang login
        if (user == null) {
            rs.sendRedirect("login.jsp");
            return;
        }

        try {
            int bookId = Integer.parseInt(rq.getParameter("bookId"));
            int rating = Integer.parseInt(rq.getParameter("rating"));
            String comment = rq.getParameter("comment");

            Review r = new Review();
            r.setUserId(user.getId());
            r.setBookId(bookId);
            r.setRating(rating);
            r.setComment(comment);

            reviewDAO.add(r);

            // Quay trở lại đúng trang chi tiết của cuốn sách vừa đánh giá
            rs.sendRedirect("product-detail?id=" + bookId);
        } catch (Exception e) {
            e.printStackTrace();
            rs.sendRedirect("index.jsp");
        }
    }
}