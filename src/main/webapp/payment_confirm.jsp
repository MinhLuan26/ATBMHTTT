<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

       <form action="place-order" method="post" class="checkout-form">
    <label for="address">Địa chỉ giao hàng</label>
    <input type="text" id="address" name="address" placeholder="Nhập địa chỉ nhận hàng" required>
    <button type="submit" class="btn-confirm">Xác nhận thanh toán</button>
</form>

    </div>

</main>

<jsp:include page="footer.jsp"/>
</body>
</html>
