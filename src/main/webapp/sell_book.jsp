<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng Bán Sách Cũ</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/sell.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="sell-form">

		<h2>Đăng bán sách</h2>

		<!-- Thông báo lỗi -->
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>

		<form action="sell_book" method="post" enctype="multipart/form-data">

			<!-- ===== HÀNG 1: TIÊU ĐỀ - TÁC GIẢ ===== -->
			<div class="sell-row">
				<div>
					<label>Tiêu đề</label> <input type="text" name="title" required />
				</div>

				<div>
					<label>Tác giả</label> <input type="text" name="author" required />
				</div>
			</div>

			<!-- ===== HÀNG 2: GIÁ - TÌNH TRẠNG ===== -->
			<div class="sell-row">
				<div>
					<label>Giá (VNĐ)</label> <input type="number" name="price"
						step="0.01" required />
				</div>

				<div>
					<label>Tình trạng</label> <select name="condition" required>
						<option value="">-- Chọn --</option>
						<option value="new">Mới</option>
						<option value="used">Cũ</option>
					</select>
				</div>
			</div>

			<!-- ===== MÔ TẢ ===== -->
			<label>Mô tả</label>
			<textarea name="description"
				placeholder="Mô tả chi tiết về cuốn sách..."></textarea>

			<!-- ===== UPLOAD ẢNH ===== -->
			<label>Ảnh bìa sách</label> <input type="file" name="image"
				accept="image/*" class="upload-input" />

			<!-- ===== NÚT SUBMIT ===== -->
			<button type="submit">Đăng bán</button>

		</form>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
