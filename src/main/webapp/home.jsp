<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ - BOOK MARKET</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main>

		<section class="main-banner">
			<img src="${pageContext.request.contextPath}/images/main_banner.png"
				alt="KHÁM PHÁ TRI THỨC VIỆT">
		</section>

		<section class="product-section">

			<div class="container">

				<div class="section-header">
					<h2 class="section-title">SÁCH MỚI ĐƯỢC YÊU THÍCH</h2>
					<p class="section-subtitle">Tuyển tập những cuốn sách bán chạy
						nhất tháng</p>
					<div class="title-underline"></div>
				</div>

				<div class="book-grid">
					<c:forEach items="${featuredBooks}" var="book">
						<div class="book-card">
							<div class="card-image">
								<a href="product-detail?id=${book.id}" title="${book.title}">
									<img src="${book.imageUrl}" alt="${book.title}">
								</a>
							</div>

							<div class="card-content">
								<h4>
									<a href="product-detail?id=${book.id}"><c:out
											value="${book.title}" /></a>
								</h4>
								<p class="author">
									Tác giả:
									<c:out value="${book.author}" />
								</p>
								<p class="price">
									<c:out value="${book.price}" />
									VNĐ
								</p>
							</div>

							<div class="card-action">
								<a href="product-detail?id=${book.id}" class="btn-detail">Xem
									chi tiết</a>
							</div>
						</div>
					</c:forEach>
					<c:if test="${empty featuredBooks}">
						<p>Hiện chưa có sách nào được đăng bán.</p>
					</c:if>
				</div>

				<div class=view-more-area>
					<a href="#" class="btn-view-more"> Xem thêm tất cả sản phẩm
						&rarr; </a>
				</div>
				<div class="section-header" style="text-align: left;">
					<h2 class="section-title">TÁC GIẢ NỔI BẬC</h2>
					<p class="section-subtitle">Tuyển tập những cuốn sách của các
						ngồi bút tài ba!</p>

				</div>
			</div>

		</section>

	</main>
	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>