package dao;

import model.User;

public interface IUserDAO {
	public boolean register(User user);
	// có thể thêm các phương thức checkLogin, findByEmail...
	public User findByEmail(String email);
	// cập nhật thông tin người dùng
	public void updateUser(User user);
}
