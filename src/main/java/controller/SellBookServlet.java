package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import dao.BookDAOImpl;
import model.Book;
import model.User;
import java.math.BigDecimal;

/** Servlet xử lý đăng bán sách */
@WebServlet("/sell_book")
@MultipartConfig(fileSizeThreshold=1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50)
public class SellBookServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    /** GET: hiện form đăng bán */
    protected void doGet(HttpServletRequest rq,HttpServletResponse rs)throws IOException,ServletException{
        rq.getRequestDispatcher("sell_book.jsp").forward(rq,rs);
    }

    /** POST: nhận thông tin sách và lưu DB */
    protected void doPost(HttpServletRequest rq,HttpServletResponse rs)throws IOException,ServletException{
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){ rs.sendRedirect("login.jsp"); return; }

        String title = rq.getParameter("title");
        String author = rq.getParameter("author");
        String priceS = rq.getParameter("price");
        String desc = rq.getParameter("description");
        String condition = rq.getParameter("condition");

        BigDecimal price = BigDecimal.ZERO;
        try { price = new BigDecimal(priceS); } catch(Exception e){}

        Part imgPart = rq.getPart("image");
        String fileName = null;
        if(imgPart != null && imgPart.getSubmittedFileName()!=null && !imgPart.getSubmittedFileName().isEmpty()){
            String uploads = getServletContext().getRealPath("") + File.separator + "images";
            File upDir = new File(uploads);
            if(!upDir.exists()) upDir.mkdirs();
            fileName = System.currentTimeMillis() + "_" + imgPart.getSubmittedFileName();
            imgPart.write(uploads + File.separator + fileName);
        } else {
            fileName = "images/default.png";
        }

        Book b = new Book();
        b.setTitle(title); b.setAuthor(author); b.setPrice(price);
        b.setDescription(desc); b.setImageUrl(fileName.startsWith("images/")?fileName:"images/"+fileName);

        int newId = bookDAO.insertBook(b);
        if(newId>0){
        	rs.sendRedirect("product-detail?id=" + newId);
        } else {
            rq.setAttribute("error","Đăng bán thất bại");
            rq.getRequestDispatcher("sell_book.jsp").forward(rq,rs);
        }
    }
}
