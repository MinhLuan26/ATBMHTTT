package dao;

import model.User;

public interface IUserDAO {
	public boolean register(User user);
	public User findByEmail(String email);
	public void updateUser(User user);
	public String getPublicKeyByUserId(int userId);
	
}