package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DBContext;
import model.Cart;
import model.CartItem;

public class OrderDAO {

    public int createOrder(int userId, Cart cart, String address) {
        int orderId = -1;
        String insertOrder = "INSERT INTO orders(user_id, total_price, shipping_address) VALUES (?, ?, ?)";
        String insertDetail = "INSERT INTO order_details(order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn =  DBContext.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setBigDecimal(2, cart.getTotal());
            ps.setString(3, address);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) orderId = rs.getInt(1);

            PreparedStatement psDetail = conn.prepareStatement(insertDetail);
            for (CartItem item : cart.getItems()) {
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getBook().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setBigDecimal(4, item.getBook().getPrice());
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }
}