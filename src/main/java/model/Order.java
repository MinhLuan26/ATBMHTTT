package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List; // Thêm import này để dùng List

public class Order {
    private int id;
    private int userId;
    private BigDecimal totalPrice;
    private String status;
    private Date orderDate;
    private String shippingAddress;
    private List<OrderDetail> details; // Thêm trường này để lưu danh sách các món hàng trong đơn

    // 1. Constructor không tham số (Bắt buộc phải có vì OrderDAOImpl sử dụng: new Order())
    public Order() {
    }

    // 2. Constructor có tham số hiện tại của bạn
    public Order(int id, int userId, BigDecimal totalPrice, String status, Date orderDate, String shippingAddress) {
        super();
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
    }

    // === CÁC HÀM GETTER VÀ SETTER ===

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    // 3. THÊM GETTER VÀ SETTER CHO DETAILS (Giải quyết lỗi bắt tạo method getDetails)
    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}