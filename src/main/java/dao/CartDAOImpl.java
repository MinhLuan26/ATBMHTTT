package dao;

import model.Book;
import model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.DBContext;

public class CartDAOImpl implements ICartDAO {

	/**
	 * Tìm cart_id của user. Nếu chưa có -> tạo mới cart và trả về cart_id vừa tạo.
	 */
	private int findOrCreateCart(Connection conn, int userId) throws SQLException {
		int cartId = 0;

		String selectSql = "SELECT cart_id FROM carts WHERE user_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					cartId = rs.getInt("cart_id");
				}
			}
		}

		// Nếu chưa có cart -> tạo mới
		if (cartId == 0) {
			String insertSql = "INSERT INTO carts (user_id) OUTPUT INSERTED.cart_id VALUES (?)";
			try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
				ps.setInt(1, userId);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						cartId = rs.getInt(1);
					}
				}
			}
		}

		return cartId;
	}

	/**
	 * Lấy toàn bộ giỏ hàng của user từ CSDL
	 */
	@Override
	public Map<Integer, CartItem> getCartByUserId(int userId) {
		Map<Integer, CartItem> cart = new HashMap<>();

		String sql = "SELECT b.id, b.title, b.author, b.price, b.image_url, b.description, ci.quantity "
				+ "FROM carts c " + "JOIN cart_items ci ON c.cart_id = ci.cart_id "
				+ "JOIN books b ON ci.book_id = b.id " + "WHERE c.user_id = ?";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setPrice(rs.getBigDecimal("price"));
					book.setImageUrl(rs.getString("image_url"));
					book.setDescription(rs.getString("description"));

					int quantity = rs.getInt("quantity");

					cart.put(book.getId(), new CartItem(book, quantity));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cart;
	}

	/**
	 * Thêm sách vào giỏ hàng (user đã đăng nhập) Có xử lý TRANSACTION
	 */
	@Override
	public void addItemToCart(int userId, int bookId, int quantity) {

		String checkSql = "SELECT quantity FROM cart_items WHERE cart_id = ? AND book_id = ?";

		String updateSql = "UPDATE cart_items SET quantity = quantity + ? " + "WHERE cart_id = ? AND book_id = ?";

		String insertSql = "INSERT INTO cart_items (cart_id, book_id, quantity) VALUES (?, ?, ?)";

		try (Connection conn = DBContext.getConnection()) {

			conn.setAutoCommit(false);

			int cartId = findOrCreateCart(conn, userId);
			int currentQuantity = 0;

			// Kiểm tra đã tồn tại sách trong giỏ chưa
			try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
				ps.setInt(1, cartId);
				ps.setInt(2, bookId);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						currentQuantity = rs.getInt("quantity");
					}
				}
			}

			if (currentQuantity > 0) {
				// UPDATE
				try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
					ps.setInt(1, quantity);
					ps.setInt(2, cartId);
					ps.setInt(3, bookId);
					ps.executeUpdate();
				}
			} else {
				// INSERT
				try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
					ps.setInt(1, cartId);
					ps.setInt(2, bookId);
					ps.setInt(3, quantity);
					ps.executeUpdate();
				}
			}

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gộp giỏ hàng session vào giỏ hàng DB khi user đăng nhập
	 */
	@Override
	public void mergeCart(Map<Integer, CartItem> sessionCart, int userId) {
		if (sessionCart == null || sessionCart.isEmpty())
			return;

		for (CartItem item : sessionCart.values()) {
			addItemToCart(userId, item.getBook().getId(), item.getQuantity());
		}
	}

	/**
	 * Xóa 1 sản phẩm khỏi giỏ hàng
	 */
	@Override
	public void removeItemFromCart(int userId, int bookId) {

		String sql = "DELETE ci " + "FROM cart_items ci " + "JOIN carts c ON ci.cart_id = c.cart_id "
				+ "WHERE c.user_id = ? AND ci.book_id = ?";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
