package dao;

import java.util.List;

import model.Order;

public interface IOrderDAO {
	public List<Order> getOrdersByUserId(int userId);
	public List<model.Order> getByUserId(int userId);
	public int save(Order o);
}