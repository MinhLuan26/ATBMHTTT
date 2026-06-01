package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBContext;
import model.Book;

public class BookDAOImpl implements IBookDAO {

    @Override
    public List<Book> getFeaturedBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM books ORDER BY id DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setImageUrl(rs.getString("image_url"));  // FIX
                book.setStatus(rs.getString("status"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public Book findBookId(int id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getBigDecimal("price"));
                    book.setImageUrl(rs.getString("image_url"));  // FIX
                    book.setDescription(rs.getString("description"));
                    book.setStatus(rs.getString("status"));
                    book.setSellerId(rs.getInt("seller_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }


    @Override
    public List<Book> search(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getBigDecimal("price"));
                    b.setImageUrl(rs.getString("image_url"));  // FIX
                    b.setDescription(rs.getString("description"));
                    b.setStatus(rs.getString("status"));
                    b.setSellerId(rs.getInt("seller_id"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<Book> filter(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String condition) {
        List<Book> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT * FROM books WHERE 1=1");

        if (minPrice != null) sb.append(" AND price >= ?");
        if (maxPrice != null) sb.append(" AND price <= ?");
        if (condition != null && !condition.isEmpty()) sb.append(" AND condition = ?");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            int idx = 1;
            if (minPrice != null) ps.setBigDecimal(idx++, minPrice);
            if (maxPrice != null) ps.setBigDecimal(idx++, maxPrice);
            if (condition != null && !condition.isEmpty()) ps.setString(idx++, condition);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getBigDecimal("price"));
                    b.setImageUrl(rs.getString("image_url"));  // FIX
                    b.setDescription(rs.getString("description"));
                    b.setStatus(rs.getString("status"));
                    b.setSellerId(rs.getInt("seller_id"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public int insertBook(Book b) {
        // FIX: thiếu dấu hỏi cho seller_id → giờ đã đủ 8 dấu ?
        String sql = "INSERT INTO books(title, author, price, description, image_url, condition, status, seller_id) "
                   + "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setBigDecimal(3, b.getPrice());
            ps.setString(4, b.getDescription());
            ps.setString(5, b.getImageUrl());
            ps.setString(6, "Used");
            ps.setString(7, "PENDING");
            ps.setInt(8, b.getSellerId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public List<Book> getBooksByIds(List<Integer> ids) {
        List<Book> list = new ArrayList<>();
        if (ids == null || ids.isEmpty()) return list;

        String placeholders = String.join(",", java.util.Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT * FROM books WHERE id IN (" + placeholders + ")";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPrice(rs.getBigDecimal("price"));

                // FIX QUAN TRỌNG NHẤT
                b.setImageUrl(rs.getString("image_url"));  

                b.setSellerId(rs.getInt("seller_id"));
                b.setStatus(rs.getString("status"));
                b.setDescription(rs.getString("description"));

                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<Book> searchSuggest(String keyword) {
        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT TOP 10 *
            FROM books
            WHERE title LIKE ?
            ORDER BY
              CASE
                WHEN title LIKE ? THEN 0
                ELSE 1
              END,
              title
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%"); // chứa
            ps.setString(2, keyword + "%");       // bắt đầu

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getBigDecimal("price"));
                    b.setImageUrl(rs.getString("image_url"));
                    b.setDescription(rs.getString("description"));
                    b.setStatus(rs.getString("status"));
                    b.setSellerId(rs.getInt("seller_id"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
