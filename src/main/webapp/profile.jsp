<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý tài khoản - BookMarket</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/profile.css">
</head>
<body>

	<jsp:include page="header.jsp" />

	<main>
		<div class="profile-container">

			<aside class="profile-sidebar">
				<div class="profile-user-info">
					<img src="${pageContext.request.contextPath}/images/user_icon.png"
						alt="Avatar" class="profile-avatar">
					<div class="profile-name">
						<c:out value="${sessionScope.user.fullName}" />
					</div>
				</div>
				<nav class="profile-menu">

					<a href="profile?tab=info"
						class="${currentTab == 'info' ? 'active' : ''}"> Hồ sơ của tôi
					</a> <a href="sell_book.jsp">Bán sách</a> <a href="profile?tab=orders"
						class="${currentTab == 'orders' ? 'active' : ''}"> Quản lý đơn
						hàng </a> <a href="wishlist">Danh sách yêu thích</a> <a
						href="profile?tab=password"
						class="${currentTab == 'password' ? 'active' : ''}"> Đổi mật
						khẩu </a> <a href="logout" style="color: red;">Đăng xuất</a>

				</nav>
			</aside>

			<section class="profile-content">

				<c:if test="${currentTab == 'info'}">
					<div class="profile-header">
						<h2>Hồ sơ của tôi</h2>
						<p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
					</div>
					<form action="profile" method="POST">
						<div class="form-group">
							<label>Họ và tên</label> <input type="text" name="fullname"
								class="form-control" value="${sessionScope.user.fullName}"
								required>
						</div>
						<div class="form-group">
							<label>Email</label> <input type="text" class="form-control"
								value="${sessionScope.user.email}" disabled>
						</div>
						<div class="form-group">
							<label>Số điện thoại</label> <input type="text" name="phone"
								class="form-control" value="${sessionScope.user.phone}">
						</div>
						<button type="submit" class="btn-save">Lưu thay đổi</button>
					</form>
				</c:if>

				<c:if test="${currentTab == 'orders'}">
					<div class="profile-header">
						<h2>Đơn hàng của tôi</h2>
						<p>Xem lại lịch sử mua hàng</p>
					</div>

					<table class="order-table">
						<thead>
							<tr>
								<th>Mã đơn hàng</th>
								<th>Ngày đặt</th>
								<th>Địa chỉ</th>
								<th>Giá trị</th>
								<th>Trạng thái</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orders}" var="o">
								<tr>
									<td><b>#${o.id}</b></td>
									<td>${o.orderDate}</td>
									<td>${o.address}</td>
									<td style="color: #e44d26; font-weight: bold;"><fmt:formatNumber
											value="${o.totalMoney}" type="currency" currencySymbol="VNĐ" />
									</td>
									<td><span
										class="status-badge ${o.status == 'Đã giao' ? 'status-success' : 'status-pending'}">
											${o.status} </span></td>
								</tr>
							</c:forEach>
							<c:if test="${empty orders}">
								<tr>
									<td colspan="5" style="text-align: center;">Bạn chưa có
										đơn hàng nào.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</c:if>

				<c:if test="${currentTab == 'password'}">
					<div class="profile-header">
						<h2>Đổi mật khẩu</h2>
						<p>Vui lòng nhập mật khẩu hiện tại để thay đổi</p>
					</div>
					<form action="change-password" method="POST">
						<div class="form-group">
							<label>Mật khẩu hiện tại</label> <input type="password"
								name="currentPass" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Mật khẩu mới</label> <input type="password" name="newPass"
								class="form-control" required>
						</div>
						<div class="form-group">
							<label>Xác nhận mật khẩu mới</label> <input type="password"
								name="confirmPass" class="form-control" required>
						</div>
						<button type="submit" class="btn-save">Cập nhật mật khẩu</button>
					</form>
				</c:if>

				<c:if test="${currentTab == 'favorites'}">
					<div class="profile-header">
						<h2>Sản phẩm yêu thích</h2>
					</div>
					<p>Tính năng đang phát triển...</p>
				</c:if>

			</section>
		</div>

	</main>

	<jsp:include page="footer.jsp" />

</body>
</html>