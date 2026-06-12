package model;

import java.io.Serializable;
//Serializable là một "marker" interface, hữu ích khi làm việc với Session
//Tạo class JavaBean User.java
public class User implements Serializable{
	private static final long seriaVersionUID = 1l;
	
	private int id;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	private String role;
	private String publicKey;
	
	public User() {}
	
	public User( String fullName, String email, String password, String phone, String role, String publicKey) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.publicKey = publicKey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSeriaversionuid() {
		return seriaVersionUID;
	}
	
	public String getPublicKey() {
	    return publicKey;
	}

	public void setPublicKey(String publicKey) {
	    this.publicKey = publicKey;
	}
}