package dao;

import java.sql.*;
import java.util.*;
import database.DBContext;

public class WishlistDAO {

    public void add(int userId, int bookId) throws Exception {
        String sql = "INSERT INTO wishlist(user_id, book_id, added_at) VALUES (?, ?, GETDATE())";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

    public void remove(int userId, int bookId) throws Exception {
        String sql = "DELETE FROM wishlist WHERE user_id=? AND book_id=?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

    public boolean exists(int userId, int bookId) throws Exception {
        String sql = "SELECT 1 FROM wishlist WHERE user_id=? AND book_id=?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public List<Integer> getAll(int userId) throws Exception {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT book_id FROM wishlist WHERE user_id=?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("book_id"));
            }
        }
        return list;
    }
}
