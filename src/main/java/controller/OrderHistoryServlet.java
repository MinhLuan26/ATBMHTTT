package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.OrderDAOImpl;
import model.User;
import java.io.IOException;

/** Lịch sử mua hàng */
@WebServlet("/orders")
public class OrderHistoryServlet extends HttpServlet {

    private OrderDAOImpl orderDAO = new OrderDAOImpl();

    /** GET: hiển thị danh sách đơn hàng */
    protected void doGet(HttpServletRequest rq,HttpServletResponse rs)throws IOException,ServletException{
        HttpSession session = rq.getSession();
        User u = (User) session.getAttribute("user");
        if(u==null){ rs.sendRedirect("login.jsp"); return; }
        java.util.List<model.Order> orders = orderDAO.getByUserId(u.getId());
        rq.setAttribute("orders", orders);
        rq.getRequestDispatcher("order_history.jsp").forward(rq,rs);
    }
}
