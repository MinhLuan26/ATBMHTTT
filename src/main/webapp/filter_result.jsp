<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Kết Quả Lọc Sách</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<div class="container">
	<h2>Kết quả lọc</h2>
	<c:if test="${empty books}">
		<p>Không có sách phù hợp.</p>
	</c:if>
	<div class="book-grid">
		<c:forEach var="b" items="${books}">
			<div class="book-card">
				<img src="${b.imageUrl}" alt="${b.title}" />
				<h4>
					<a href="book?id=${b.id}">${b.title}</a>
				</h4>
				<p class="author">${b.author}</p>
				<p class="price">${b.price}đ</p>
			</div>
		</c:forEach>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>