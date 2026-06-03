
package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DBContext;

/** Triển khai OrderDAO */
public class OrderDAOImpl implements IOrderDAO {

	/** Lưu đơn hàng vào CSDL */
	/**
	 * Lưu đơn hàng và chi tiết (sử dụng transaction). Trả về orderId mới tạo (hoặc
	 * -1 nếu lỗi).
	 */
	public int save(Order o) {
		String sqlOrder = "INSERT INTO orders(user_id,total_price,status) VALUES(?,?,?)";
		String sqlDetail = "INSERT INTO order_details(order_id,book_id,quantity,price) VALUES(?,?,?,?)";
		try (java.sql.Connection conn = database.DBContext.getConnection()) {
			conn.setAutoCommit(false);
			try (java.sql.PreparedStatement ps = conn.prepareStatement(sqlOrder,
					java.sql.Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, o.getUserId());
				ps.setBigDecimal(2, o.getTotalPrice());
				ps.setString(3, o.getStatus());
				ps.executeUpdate();
				int orderId = -1;
				try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next())
						orderId = rs.getInt(1);
				}
				if (orderId == -1)
					throw new Exception("No order id generated");
				try (java.sql.PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
					for (OrderDetail d : o.getDetails()) {
						psd.setInt(1, orderId);
						psd.setInt(2, d.getBookId());
						psd.setInt(3, d.getQuantity());
						psd.setDouble(4, d.getPrice());
						psd.addBatch();
					}
					psd.executeBatch();
				}
				conn.commit();
				return orderId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<Order> getOrdersByUserId(int userId) {
	    List<Order> list = new ArrayList<>();
	    
	    // SỬA: Bỏ điều kiện lọc cứng status = 'COMPLETED', lấy toàn bộ đơn hàng của user đó
	    String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
	    
	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Order o = new Order();
	                o.setId(rs.getInt("id"));
	                o.setUserId(rs.getInt("user_id"));
	                o.setTotalPrice(rs.getBigDecimal("total_price"));
	                o.setStatus(rs.getString("status")); // Lấy cả PENDING, COMPLETED, SHIPPING...
	                o.setOrderDate(rs.getTimestamp("order_date"));
	                o.setShippingAddress(rs.getString("shipping_address"));
	                list.add(o);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	@Override
	public List<Order> getByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
