package model;

import java.io.Serializable;
import java.math.BigDecimal; // Import lớp này

public class Book implements Serializable{
	private int id;
	private String title;
	private String author;
	private BigDecimal price; // Dùng BigDecimal cho tiền tệ
	private String description;
	private String imageUrl;
	private String status; // PENDING, ACTIVE...
	private int sellerId; // ID người bán

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	// Constructors (để trống)
	public Book() {
	}

	// Getters and Setters
	// (Hãy dùng Eclipse: Chuột phải > Source > Generate Getters and Setters... >
	// Select All)

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}