<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Kết quả tìm kiếm</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/homepage.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/search-result.css">
</head>

<body data-context="${pageContext.request.contextPath}">

	<jsp:include page="header.jsp" />

	<div class="container search-page">

		<!-- TIÊU ĐỀ -->
		<div class="search-title">
			Kết quả tìm kiếm cho: <span>"${q}"</span>
		</div>

		<c:if test="${not empty books}">
			<div class="book-grid">
				<c:forEach var="b" items="${books}">
					<div class="book-card" data-id="${b.id}">

						<img src="${b.imageUrl}" alt="${b.title}">

						<div class="info">
							<h4>${b.title}</h4>
							<div class="author">${b.author}</div>
							<div class="price">${b.price}đ</div>
						</div>

					</div>
				</c:forEach>
			</div>
		</c:if>

	</div>

	<jsp:include page="footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/search-result.js"></script>

</body>
</html>
