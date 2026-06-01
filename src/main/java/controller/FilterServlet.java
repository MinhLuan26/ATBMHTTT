package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.BookDAOImpl;
import java.io.IOException;
import java.math.BigDecimal;

/** Lọc sách theo giá, tình trạng */
@WebServlet("/filter")
public class FilterServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    /** GET: áp dụng filter và trả danh sách */
    protected void doGet(HttpServletRequest rq,HttpServletResponse rs)throws IOException,ServletException{
        String min = rq.getParameter("min");
        String max = rq.getParameter("max");
        String cond = rq.getParameter("condition");
        BigDecimal minP = null, maxP = null;
        try { if(min!=null && !min.isEmpty()) minP = new BigDecimal(min); } catch(Exception e){}
        try { if(max!=null && !max.isEmpty()) maxP = new BigDecimal(max); } catch(Exception e){}
        java.util.List<model.Book> results = bookDAO.filter(minP, maxP, cond);
        rq.setAttribute("books", results);
        rq.getRequestDispatcher("filter_result.jsp").forward(rq,rs);
    }
}
