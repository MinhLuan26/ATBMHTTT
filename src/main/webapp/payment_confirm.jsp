<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Thêm thư viện này để format hash nếu cần --%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Xác nhận thanh toán</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/payment_confirm.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<main class="checkout-container">

    <h2 class="page-title">Xác nhận đơn hàng</h2>

    <div class="checkout-card">
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sessionScope.cart.items}" var="item">
                    <tr>
                        <td class="product-info">
                            <img src="${item.book.imageUrl}" alt="${item.book.title}" class="product-img">
                            <span>${item.book.title}</span>
                        </td>
                        <td>${item.book.price} VNĐ</td>
                        <td>${item.quantity}</td>
                        <td>${item.total} VNĐ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="cart-summary">
            <div class="summary-left">Tổng tiền:</div>
            <div class="summary-right">${sessionScope.cart.total} VNĐ</div>
        </div>

        <c:if test="${not empty error}">
            <div style="background-color: #ffebee; color: #c62828; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #ef9a9a;">
                <strong>Lỗi:</strong> ${error}
            </div>
        </c:if>
        
        <form action="place-order" method="post" class="checkout-form">
            <label for="address">Địa chỉ giao hàng</label>
            <input type="text" id="address" name="address" value="${address}" placeholder="Nhập địa chỉ nhận hàng" required>

            <div class="security-box" style="border: 2px solid #07805b; padding: 15px; border-radius: 8px; margin-bottom: 20px; background-color: #f9f9f9;">
                <h3 style="color: #07805b; margin-top: 0;">Xác thực bằng Chữ ký điện tử</h3>
                <p style="font-size: 14px;">1. Copy mã đơn hàng này vào Tool: 
                   <textarea readonly style="width: 100%; height: 50px; margin-top: 5px; font-family: monospace; font-size: 12px;">${orderHash}</textarea>
                </p>
                <p style="font-size: 14px;">2. Dán chữ ký nhận được từ Tool vào đây:</p>
                <textarea name="digitalSignature" placeholder="Dán chữ ký (Base64) vào đây..." 
                          style="width: 100%; height: 80px; padding: 10px; border: 1px solid #ccc; border-radius: 4px;" required>${digitalSignature}</textarea>
            </div>

            <button type="submit" class="btn-confirm">Xác nhận thanh toán</button>
        </form>
    </div>

</main>

<jsp:include page="footer.jsp"/>
</body>
</html>