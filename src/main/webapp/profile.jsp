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
					<a href="profile?tab=info" class="${currentTab == 'info' ? 'active' : ''}"> Hồ sơ của tôi </a> 
					<a href="sell_book.jsp">Bán sách</a> 
					<a href="profile?tab=orders" class="${currentTab == 'orders' ? 'active' : ''}"> Quản lý đơn hàng </a> 
					<a href="wishlist">Danh sách yêu thích </a> 
					<a href="profile?tab=keys" class="${currentTab == 'keys' ? 'active' : ''}"> Quản lý khóa </a>
					<a href="profile?tab=password" class="${currentTab == 'password' ? 'active' : ''}"> Đổi mật khẩu </a> 
					<a href="logout" style="color: red;">Đăng xuất</a>
				</nav>
			</aside>
			
			<section class="profile-content">

				<c:if test="${not empty message}">
				    <div style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 20px; border-left: 5px solid #28a745; font-weight: 500;">
				        ${message}
				    </div>
				</c:if>

				<c:if test="${currentTab == 'info'}">
					<div class="profile-header">
						<h2>Hồ sơ của tôi</h2>
						<p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
					</div>
					<form action="profile" method="POST">
						<div class="form-group">
							<label>Họ và tên</label> 
							<input type="text" name="fullname" class="form-control" value="${sessionScope.user.fullName}" required>
						</div>
						<div class="form-group">
							<label>Email</label> 
							<input type="text" class="form-control" value="${sessionScope.user.email}" disabled>
						</div>
						<div class="form-group">
							<label>Số điện thoại</label> 
							<input type="text" name="phone" class="form-control" value="${sessionScope.user.phone}">
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
									<td>${o.shippingAddress}</td> 
									<td style="color: #e44d26; font-weight: bold;">
										<fmt:formatNumber value="${o.totalPrice}" type="number" /> VNĐ
									</td>
									<td>
										<span class="status-badge ${o.status == 'Đã giao' ? 'status-success' : 'status-pending'}">
											${o.status}
										</span>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty orders}">
								<tr>
									<td colspan="5" style="text-align: center;">Bạn chưa có đơn hàng nào.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</c:if>

				<c:if test="${currentTab == 'keys'}">
				    <style>
				        .modern-card {
				            background: #ffffff;
				            border: 1px solid #eaedf1;
				            border-radius: 12px;
				            padding: 25px;
				            box-shadow: 0 4px 15px rgba(0,0,0,0.03);
				            transition: transform 0.2s ease, box-shadow 0.2s ease;
				        }
				        .modern-card:hover {
				            transform: translateY(-3px);
				            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
				        }
				        .btn-modern {
				            border: none;
				            padding: 12px 20px;
				            border-radius: 8px;
				            font-weight: 600;
				            font-size: 14px;
				            cursor: pointer;
				            transition: all 0.3s ease;
				            display: block;
				            text-align: center;
				        }
				        .btn-green { background: #059669; color: white; width: 100%; box-shadow: 0 4px 10px rgba(5,150,105,0.2); }
				        .btn-green:hover { background: #047857; transform: scale(1.02); }
				        .btn-blue { background: #2563eb; color: white; width: 100%; box-shadow: 0 4px 10px rgba(37,99,235,0.2); }
				        .btn-blue:hover { background: #1d4ed8; transform: scale(1.02); }
				        .btn-red { background: #dc2626; color: white; padding: 12px 35px; box-shadow: 0 4px 10px rgba(220,38,38,0.2); }
				        .btn-red:hover { background: #b91c1c; }
				        
				        .key-textarea {
				            width: 100%;
				            border: 1px solid #d1d5db;
				            border-radius: 8px;
				            padding: 15px;
				            font-family: 'Consolas', 'Courier New', monospace;
				            font-size: 13px;
				            color: #374151;
				            background-color: #f9fafb;
				            resize: vertical;
				            transition: border-color 0.3s, box-shadow 0.3s;
				        }
				        .key-textarea:focus {
				            outline: none;
				            border-color: #3b82f6;
				            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
				        }
				        
				        .file-drop-area {
				            background: #f3f4f6;
				            border: 1.5px dashed #9ca3af;
				            border-radius: 8px;
				            padding: 12px;
				            text-align: center;
				            margin-bottom: 15px;
				            transition: background 0.3s;
				        }
				        .file-drop-area:hover { background: #e5e7eb; border-color: #6b7280; }
				        .file-drop-area input[type="file"] { font-size: 13px; color: #4b5563; outline: none; cursor: pointer; }
				        
				        .badge-active {
				            background: #10b981; color: white; padding: 5px 12px; 
				            border-radius: 20px; font-size: 12px; font-weight: bold;
				        }
				        .badge-revoked {
						    background: #ef4444; color: white; padding: 5px 12px; 
						    border-radius: 20px; font-size: 12px; font-weight: bold;
						}
				    </style>

				    <div class="profile-header" style="margin-bottom: 25px;">
				        <h2 style="font-size: 24px; color: #1f2937;">Quản lý khóa bảo mật</h2>
				        <p style="color: #6b7280; font-size: 15px;">Trung tâm thiết lập và quản lý chữ ký số của bạn.</p>
				    </div>
				    
				    <c:if test="${not empty sessionScope.newPrivateKey}">
				        <div style="background-color: #fffbeb; border-left: 5px solid #f59e0b; padding: 20px; border-radius: 8px; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.05);">
				            <h3 style="color: #d97706; margin-top: 0; font-size: 20px;">🎉 Tạo cặp khóa thành công!</h3>
				            <p style="color: #4b5563; font-size: 15px; line-height: 1.6;">
				                <b style="color: #dc2626;">CẢNH BÁO QUAN TRỌNG:</b> Đây là lần duy nhất bạn có thể tải <b>Private Key</b>. 
				                Hệ thống tuyệt đối không lưu trữ khóa bí mật của bạn. Hãy lưu trữ an toàn để ký thanh toán đơn hàng!
				            </p>
				            <form action="download-private-key" method="POST" style="margin-top: 15px;">
				                <button type="submit" style="background-color: #ef4444; color: white; border: none; padding: 12px 25px; font-size: 16px; font-weight: bold; border-radius: 6px; cursor: pointer; box-shadow: 0 4px 10px rgba(239,68,68,0.3); transition: 0.3s;">
				                    ⬇ TẢI PRIVATE KEY VỀ MÁY
				                </button>
				            </form>
				        </div>
				        <% session.removeAttribute("newPrivateKey"); %>
				    </c:if>
				
				    <div style="display: flex; gap: 25px; margin-bottom: 30px;">
				        <div class="modern-card" style="flex: 1; text-align: center;">
				            <h3 style="color: #059669; margin-top: 0; font-size: 18px;">✨ Tạo cặp khóa mới</h3>
				            <p style="font-size: 14px; color: #6b7280; height: 45px; margin-bottom: 20px;">
				                Hệ thống tự động sinh cặp RSA 2048-bit an toàn. Khóa công khai sẽ tự động cập nhật vào hồ sơ.
				            </p>
				            <form action="profile" method="POST">
				                <input type="hidden" name="formType" value="generateKey">
				                <button type="submit" class="btn-modern btn-green">Tạo Khóa Mới Tự Động</button>
				            </form>
				        </div>
				
				        <div class="modern-card" style="flex: 1; text-align: center;">
				            <h3 style="color: #2563eb; margin-top: 0; font-size: 18px;">📤 Tải lên Public Key</h3>
				            <p style="font-size: 14px; color: #6b7280; height: 45px; margin-bottom: 20px;">
				                Bạn đã có file <code>public.key</code> từ Tool Desktop? Trực tiếp tải file lên hệ thống tại đây.
				            </p>
				            <form action="profile" method="POST" enctype="multipart/form-data">
				                <input type="hidden" name="formType" value="uploadKey">
				                <button type="submit" class="btn-modern btn-blue">Xác Nhận Tải File Lên</button>
				                <div class="file-drop-area">
				                    <input type="file" name="publicKeyFile" accept=".key,.txt" required>
				                </div>
				            </form>
				        </div>
				    </div>
				
				    <div class="modern-card" style="margin-bottom: 30px; padding: 30px;">
				        <h3 style="margin-top: 0; color: #1f2937; font-size: 18px; margin-bottom: 15px;">🔑 Khóa Công Khai Hiện Tại</h3>
				        <form action="profile" method="POST">
				            <input type="hidden" name="formType" value="updateKey">
				            <div class="form-group" style="margin-bottom: 15px;">
				                <textarea name="publicKey" class="key-textarea" rows="6" 
				                          placeholder="Chưa có khóa công khai nào được thiết lập trên hệ thống..." 
				                          required>${sessionScope.user.publicKey}</textarea>
				            </div>
				            <button type="submit" class="btn-modern btn-red">Lưu Cập Nhật Khóa</button>
				        </form>
				        <c:if test="${not empty sessionScope.user.publicKey}">
					        <form action="profile" method="POST" onsubmit="return confirm('🚨 CẢNH BÁO: Bạn có chắc chắn muốn báo mất và thu hồi khóa này? Hành động này KHÔNG THỂ hoàn tác!');" style="margin-top: 15px;">
					            <input type="hidden" name="formType" value="revokeKey">
					            <button type="submit" class="btn-modern" style="background-color: #991b1b; color: white; width: 100%; border: 1px solid #7f1d1d;">
					                🚨 Báo mất / Thu hồi khóa khẩn cấp
					            </button>
					        </form>
				        </c:if>
				    </div>
				
				    <div class="modern-card" style="padding: 25px 30px;">
				        <h3 style="margin-top: 0; color: #1f2937; font-size: 18px; margin-bottom: 15px;">🕒 Trạng thái bảo mật</h3>
				        <table class="cart-table" style="font-size: 14px; margin-bottom: 0; width: 100%; border-collapse: collapse;">
				            <thead>
				                <tr style="border-bottom: 2px solid #e5e7eb; color: #4b5563;">
				                    <th style="padding: 12px 10px; text-align: left;">Khóa bảo mật</th>
				                    <th style="padding: 12px 10px; text-align: center;">Trạng thái</th>
				                    <th style="padding: 12px 10px; text-align: right;">Cập nhật lần cuối</th>
				                </tr>
				            </thead>
				            <tbody>
				                <c:if test="${not empty sessionScope.user.publicKey}">
				                    <tr>
				                        <td style="padding: 15px 10px; color: #374151; font-family: monospace;">
				                            ...${sessionScope.user.publicKey.substring(sessionScope.user.publicKey.length() - 20)}
				                        </td>
				                        <td style="padding: 15px 10px; text-align: center;">
				                            <c:choose>
				                                <c:when test="${sessionScope.keyStatus == 'REVOKED'}">
				                                    <span class="badge-revoked">Đã thu hồi</span>
				                                </c:when>
				                                <c:otherwise>
				                                    <span class="badge-active">Đang hoạt động</span>
				                                </c:otherwise>
				                            </c:choose>
				                        </td>
				                        <td style="padding: 15px 10px; text-align: right; color: #6b7280;">
				                            ${not empty sessionScope.keyUpdatedAt ? sessionScope.keyUpdatedAt : 'Vừa cập nhật'}
				                        </td>
				                    </tr>
				                </c:if>
				                <c:if test="${empty sessionScope.user.publicKey}">
				                    <tr>
				                        <td colspan="3" style="text-align: center; padding: 25px; color: #9ca3af; font-style: italic;">
				                            Chưa có dữ liệu bảo mật. Hãy tạo hoặc tải khóa công khai lên.
				                        </td>
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
							<label>Mật khẩu hiện tại</label> 
							<input type="password" name="currentPass" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Mật khẩu mới</label> 
							<input type="password" name="newPass" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Xác nhận mật khẩu mới</label> 
							<input type="password" name="confirmPass" class="form-control" required>
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