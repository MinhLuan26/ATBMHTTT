package dao;

import java.util.Map;

import model.CartItem;

public interface ICartDAO {

	/**
	 * Lấy giỏ hàng (dưới dạng Map) từ CSDL cho một user.
	 */
	public Map<Integer, CartItem> getCartByUserId(int userId);

	/**
	 * Thêm/Cập nhật một món hàng vào giỏ hàng CSDL. Tự động xử lý "Nếu có thì
	 * UPDATE, nếu chưa có thì INSERT".
	 */
	public void addItemToCart(int userId, int bookId, int quantity);

	/**
	 * Hàm "Hợp nhất" (Merge) giỏ hàng Tạm (Session) vào CSDL.
	 */
	public void mergeCart(Map<Integer, CartItem> sessionCart, int userId);

	/**
	 * Xóa một món hàng khỏi giỏ hàng CSDL.
	 */
	public void removeItemFromCart(int userId, int bookId);
}