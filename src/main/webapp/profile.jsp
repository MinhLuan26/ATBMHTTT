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
					</a> 
					<a href="sell_book.jsp">Bán sách</a> 
					<a href="profile?tab=orders"
						class="${currentTab == 'orders' ? 'active' : ''}"> Quản lý đơn hàng </a> <a href="wishlist">Danh sách yêu thích
					</a> 
					<a href="profile?tab=keys" class="${currentTab == 'keys' ? 'active' : ''}">  Quản lý khóa
    				</a>
					<a href="profile?tab=password"
						class="${currentTab == 'password' ? 'active' : ''}"> Đổi mật khẩu 
					</a> 
					<a href="logout" style="color: red;">Đăng xuất</a>

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
									<td>${o.shippingAddress}</td> <td style="color: #e44d26; font-weight: bold;"><fmt:formatNumber
											value="${o.totalPrice}" type="number" /> VNĐ </td>
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

				<c:if test="${currentTab == 'keys'}">
				    <div class="profile-header">
				        <h2>Quản lý khóa bảo mật</h2>
				        <p>Trung tâm thiết lập và quản lý chữ ký số của bạn.</p>
				    </div>
				    
				    <c:if test="${not empty sessionScope.newPrivateKey}">
				        <div style="background-color: #fff3cd; border: 2px dashed #ff9800; padding: 20px; border-radius: 8px; margin-bottom: 25px; text-align: center;">
				            <h3 style="color: #e65100; margin-top: 0; font-size: 22px;">🎉 Tạo cặp khóa thành công!</h3>
				            <p style="color: #d32f2f; font-size: 15px;">
				                <b>CẢNH BÁO QUAN TRỌNG:</b> Đây là lần duy nhất bạn có thể tải <b>Private Key</b>. 
				                Hệ thống tuyệt đối không lưu trữ khóa bí mật của bạn. Nếu bạn bỏ qua bước này, bạn sẽ không có khóa để ký thanh toán!
				            </p>
				            <form action="download-private-key" method="POST" style="margin-top: 15px;">
				                <button type="submit" style="background-color: #d32f2f; color: white; border: none; padding: 15px 30px; font-size: 18px; font-weight: bold; border-radius: 8px; cursor: pointer; box-shadow: 0 4px 10px rgba(211,47,47,0.3); transition: 0.3s;">
				                    ⬇ PRIVATE KEY - TẢI VỀ NGAY
				                </button>
				            </form>
				        </div>
				        <% session.removeAttribute("newPrivateKey"); %>
				    </c:if>
				
				    <div style="display: flex; gap: 20px; margin-bottom: 25px;">
				        <div style="flex: 1; background: #f9fbfd; border: 1px solid #e0e0e0; padding: 20px; border-radius: 8px; text-align: center;">
				            <h3 style="color: #07805b; margin-top: 0;">✨ Tạo cặp khóa mới</h3>
				            <p style="font-size: 13px; color: #555; height: 40px;">Hệ thống sinh tự động cặp RSA 2048-bit. Khóa công khai tự động lưu vào hồ sơ.</p>
				            <form action="profile" method="POST">
				                <input type="hidden" name="formType" value="generateKey">
				                <button type="submit" class="btn-save" style="width: 100%; background-color: #07805b; box-shadow: 0 4px 6px rgba(7,128,91,0.2);">Tạo Khóa Mới</button>
				            </form>
				        </div>
				
				        <div style="flex: 1; background: #f9fbfd; border: 1px solid #e0e0e0; padding: 20px; border-radius: 8px; text-align: center;">
				            <h3 style="color: #2196F3; margin-top: 0;">📤 Tải lên Public Key</h3>
				            <p style="font-size: 13px; color: #555; height: 40px;">Bạn đã có file public.key từ phần mềm? Trực tiếp tải file lên hệ thống.</p>
				            <form action="profile" method="POST" enctype="multipart/form-data" style="display: flex; gap: 10px; flex-direction: column;">
				                <input type="hidden" name="formType" value="uploadKey">
				                <input type="file" name="publicKeyFile" accept=".key,.txt" required style="font-size: 13px; border: 1px solid #ccc; padding: 5px; border-radius: 4px; background: white;">
				                <button type="submit" class="btn-save" style="background-color: #2196F3; width: 100%; box-shadow: 0 4px 6px rgba(33,150,243,0.2);">Tải File Lên</button>
				            </form>
				        </div>
				    </div>
					<c:if test="${not empty sessionScope.message}">
					    <div style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 15px;">
					        ${sessionScope.message}
					    </div>
					    <% session.removeAttribute("message"); %>
					</c:if>
				    <div style="border: 1px solid #e0e0e0; padding: 20px; border-radius: 8px; margin-bottom: 25px;">
				        <h3 style="margin-top: 0; color: #333;">🔑 Khóa Công Khai Hiện Tại</h3>
				        <form action="profile" method="POST">
				            <input type="hidden" name="formType" value="updateKey">
				            <div class="form-group" style="margin-bottom: 10px;">
				                <textarea name="publicKey" class="form-control" rows="5" 
				                          placeholder="Chưa có khóa công khai nào được thiết lập..." 
				                          required style="font-family: monospace; font-size: 12px; background-color: #fff; border: 1px solid #ccc;">${sessionScope.user.publicKey}</textarea>
				            </div>
				            <button type="submit" class="btn-save" style="background-color: #e44d26;">Lưu Lại Khóa</button>
				        </form>
				    </div>
				
				    <div style="border: 1px solid #e0e0e0; padding: 20px; border-radius: 8px;">
				        <h3 style="margin-top: 0; color: #333;">🕒 Lịch sử tạo khóa</h3>
				        <table class="cart-table" style="font-size: 14px; margin-bottom: 0;">
				            <thead>
				                <tr>
				                    <th>Hành động</th>
				                    <th>Trạng thái</th>
				                    <th>Thời gian ghi nhận</th>
				                </tr>
				            </thead>
				            <tbody>
				                <c:if test="${not empty sessionScope.user.publicKey}">
				                    <tr>
				                        <td>Hệ thống đã ghi nhận 01 Khóa Công Khai</td>
				                        <td><span style="color: white; background: #4caf50; padding: 3px 8px; border-radius: 4px; font-size: 12px;">Đang hoạt động</span></td>
				                        <td>Gần đây nhất</td>
				                    </tr>
				                </c:if>
				                <c:if test="${empty sessionScope.user.publicKey}">
				                    <tr>
				                        <td colspan="3" style="text-align: center; color: #888;">Chưa có dữ liệu lịch sử. Hãy tạo hoặc tải khóa lên.</td>
				                    </tr>
				                </c:if>
				            </tbody>
				        </table>
				    </div>
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