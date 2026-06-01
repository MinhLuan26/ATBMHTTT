package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import database.DBContext;

public class UserDAOImpl implements IUserDAO {

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users (fullname, email, password, phone, role) VALUES(?, ?, ?, ?, ?)";
		
		// cài mặc định vai trò "buyer" nếu chưa set
		String role = (user.getRole()==null || user.getRole().isEmpty()? "buyer" : user.getRole());

		// giải quyết khó khăn( Quy tắc 3): Quản lý tài nguyên JDBC thủ công
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// Lấy kết nối DBContext
			conn = DBContext.getConnection();
			// PrepareStatement giúp chống lỗi SQL Injection
			ps = conn.prepareStatement(sql);
			// Set các tham số cho các câu lệnh SQL
			ps.setString(1, user.getFullName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());// password đã được mã hóa ở phần đăng ký(reServlet.java)
			ps.setString(4, user.getPhone());
			ps.setString(5, role);
			// Thực thi câu lệnh
			int rowsAffected = ps.executeUpdate();
			// Nếu có ít nhất 1 dòng bị ảnh hưởng, nghĩa là INSERT thành công
			return rowsAffected >0;
		}catch (SQLException e) {
			e.printStackTrace();
			// Có thể email đã tồn tại (lỗi UNIQUE constraint)
			return false;
		}finally {
			try {
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBContext.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery(); // Dùng executeQuery cho lệnh SELECT
			// Nếu tìm thấy kết quả 
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFullName(rs.getString("fullname"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setRole(rs.getString("role"));
				return user;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// Đóng tất cả tài nguyên
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null; // không tìm thấy User
	}

	@Override
	public void updateUser(User user) {
	    // Chỉ cập nhật Họ tên và Số điện thoại (Email thường không cho đổi)
	    String sql = "UPDATE users SET fullname = ?, phone = ? WHERE id = ?";

	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getFullName());
	        ps.setString(2, user.getPhone());
	        ps.setInt(3, user.getId());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
