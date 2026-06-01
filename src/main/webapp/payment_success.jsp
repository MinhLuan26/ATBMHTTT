<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán thành công</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/payment_success.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<main class="success-container">
    <div class="success-card">
        <h2>🎉 Thanh toán thành công!</h2>
        <p>Cảm ơn bạn đã mua sắm. Đơn hàng của bạn đang được xử lý.</p>
        <a href="index.jsp" class="btn-detail">Quay về trang chủ</a>
        <a href="cart.jsp" class="btn-detail btn-secondary">Xem giỏ hàng</a>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
