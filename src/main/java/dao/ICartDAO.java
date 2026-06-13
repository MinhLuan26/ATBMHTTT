package dao;

import java.util.Map;

import model.CartItem;

public interface ICartDAO {
	public Map<Integer, CartItem> getCartByUserId(int userId);
	public void addItemToCart(int userId, int bookId, int quantity);
	public void mergeCart(Map<Integer, CartItem> sessionCart, int userId);
	public void removeItemFromCart(int userId, int bookId);
}