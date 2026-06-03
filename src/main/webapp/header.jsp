<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="site-header">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/search.css">


	<div class="header-top">
		<div class="container">
			<div class="logo-area">
				<a href="${pageContext.request.contextPath}/" class="logo-link">
					<span class="logo-text">BookMarket</span>
				</a>
			</div>
			<div class="search-area">
    <form action="search" method="GET" class="search-form">
        <input type="text"
               name="query"
               id="searchInput"
               placeholder="Tìm sản phẩm bạn mong muốn..."
               maxlength="150"
               autocomplete="off">

        <button type="submit">
            <img src="${pageContext.request.contextPath}/images/search_icon.png"
                 alt="Tìm" class="search-icon">
        </button>

        <div id="searchSuggest" class="search-suggest"></div>
    </form>
</div>

			<div class="user-area">
				<c:if test="${sessionScope.user == null}">
					<a href="login.jsp" class="nav-item"> <img
						src="${pageContext.request.contextPath}/images/user_icon.png"
						alt="Tài khoản" class="nav-icon"> <b>Đăng nhập</b>
					</a>
					<a href="register.jsp" class="nav-item"> <b>Đăng ký</b>
					</a>
				</c:if>
				<c:if test="${sessionScope.user != null}">
					<a href="profile?tab=info" class="nav-item"> <img
						src="${pageContext.request.contextPath}/images/user_icon.png"
						alt="Tài khoản" class="nav-icon"> <span> <c:out
								value="${sessionScope.user.fullName}"></c:out>
					</span>
					</a>
					<a href="logout" class="nav-item"> <span>Đăng xuất</span>
					</a>
				</c:if>

				<a href="cart.jsp" class="nav-item cart-item"> <img
					src="${pageContext.request.contextPath}/images/cart_icon.png"
					alt="Giỏ hàng" class="nav-icon"> <span class="cart-count">
						<c:out
							value="${empty sessionScope.cart ? 0 : sessionScope.cart.size}" />
				</span>
				</a>
			</div>
		</div>
	</div>

	<nav class="header-nav">
		<div class="container">
			<div class="nav-links">
				<a href="${pageContext.request.contextPath}/"
					class="nav-item active">Trang chủ</a> <a
					href="products?category=moi" class="nav-item">Sách Mới Nhất</a> <a
					href="products" class="nav-item">Sản phẩm</a> <a href="#"
					class="nav-item">Tác Giả Nổi Bật</a> <a href="#" class="nav-item">Tin tức</a> <a href="#" class="nav-item">Liên hệ</a>
			</div>
		</div>
	</nav>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/search.css">

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/js/search.js" defer></script>

</header>