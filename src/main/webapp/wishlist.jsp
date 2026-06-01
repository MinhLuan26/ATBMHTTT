<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Wishlist</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/homepage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/wishlist.css">
</head>

<body data-context="${pageContext.request.contextPath}">

    <jsp:include page="header.jsp" />

    <div class="wishlist-container">
        <h1>Danh sách yêu thích</h1>

        <c:if test="${empty books}">
            <p>Bạn chưa thêm sách nào vào danh sách yêu thích.</p>
        </c:if>

        <div class="grid">
            <c:forEach var="b" items="${books}">
                <div class="card">

                    <!-- CHUYỂN ĐẾN TRANG CHI TIẾT SẢN PHẨM -->
                  <a href="${pageContext.request.contextPath}/product-detail?id=${b.id}">

                        <img src="${b.imageUrl}" class="thumb">
                    </a>

                    <h3>${b.title}</h3>
                    <p class="author">${b.author}</p>

                    <p class="price">
                        <fmt:formatNumber value="${b.price}" type="number" /> đ
                    </p>

                    <button class="btn-remove" data-id="${b.id}">
                        Xóa khỏi yêu thích
                    </button>

                </div>
            </c:forEach>
        </div>
    </div>

    <!-- IMPORT JS RIÊNG -->
    <script src="${pageContext.request.contextPath}/js/wishlist.js?v=1"></script>

    <jsp:include page="footer.jsp" />
</body>
</html>
