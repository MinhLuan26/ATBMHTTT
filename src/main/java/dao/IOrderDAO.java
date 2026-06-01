package dao;

import java.util.List;

import model.Order;

public interface IOrderDAO {
	// lấy ds Order theo id user
	public List<Order> getOrdersByUserId(int userId);

	// Lấy danh sách đơn hàng theo userId (mua hàng)
	public List<model.Order> getByUserId(int userId);
	public int save(Order o);
}
