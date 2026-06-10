<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Tải Công Cụ Bảo Mật - BookMarket</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/security_tool.css"> </head>
<body>

<jsp:include page="header.jsp"/>

<main class="security-container">
    <div class="security-header">
        <h1>Bảo Mật Chữ Ký Điện Tử</h1>
        <p>Để đảm bảo an toàn tuyệt đối cho tài sản và đơn hàng của bạn, hệ thống yêu cầu sử dụng công cụ Ngoại tuyến (CryptoApp).</p>
    </div>

    <div class="step-card">
        <h2><span class="step-number">1</span> Chuẩn bị môi trường (Yêu cầu hệ thống)</h2>
        <div class="step-content">
            <p>Vì công cụ <b>CryptoApp</b> được xây dựng trên nền tảng Java, máy tính của bạn cần phải được cài đặt <strong>Java Runtime Environment (JRE)</strong> phiên bản 8 trở lên.</p>
            <ul>
                <li><strong>Cách kiểm tra:</strong> Mở Command Prompt (cmd) và gõ lệnh <code>java -version</code>. Nếu hiện ra phiên bản Java, máy bạn đã sẵn sàng.</li>
                <li><strong>Cách cài đặt:</strong> Nếu máy chưa có Java, vui lòng tải và cài đặt Java tại trang chủ của Oracle: 
                    <a href="https://www.java.com/download/" target="_blank" class="java-link">Tải Java (JRE) miễn phí tại đây</a>.
                </li>
            </ul>
        </div>
    </div>

    <div class="step-card">
        <h2><span class="step-number">2</span> Tải công cụ CryptoApp</h2>
        <div class="step-content">
            <p>Sau khi máy tính đã có môi trường Java, hãy tải file ứng dụng dưới đây về máy tính của bạn.</p>
            <p><i>Lưu ý: Ứng dụng này không cần cài đặt, chỉ cần click đúp chuột vào file <code>.jar</code> để chạy.</i></p>
            
            <a href="${pageContext.request.contextPath}/downloads/ToolMaHoa.jar" class="btn-download" download>
                ⬇ Tải xuống CryptoApp (.jar)
            </a>

            <div class="note-box">
                <strong>⚠ Cảnh báo bảo mật quan trọng:</strong>
                <ul style="margin-bottom: 0; padding-left: 20px;">
                    <li>Tuyệt đối <b>KHÔNG</b> chia sẻ file <code>private.key</code> cho bất kỳ ai, kể cả nhân viên quản trị của BookMarket.</li>
                    <li>Website chỉ yêu cầu bạn dán <b>Khóa công khai (Public Key)</b> và <b>Chữ ký số</b>.</li>
                </ul>
            </div>
        </div>
    </div>

    <div class="step-card" style="border-left-color: #2196F3;">
        <h2 style="color: #2196F3;">Hướng dẫn sử dụng nhanh</h2>
        <div class="step-content">
            <ol>
                <li>Mở phần mềm <b>CryptoApp</b> vừa tải về.</li>
                <li>Chuyển sang Tab <b>Tạo Khóa (RSA)</b> và bấm Tạo cặp khóa mới.</li>
                <li>Copy nội dung <b>Public Key</b>, đăng nhập vào Web -> Hồ sơ của tôi -> Dán vào ô Khóa công khai.</li>
                <li>Khi thanh toán đơn hàng, dùng Tab <b>Chữ ký điện tử</b> trong CryptoApp để ký xác nhận mã đơn hàng.</li>
            </ol>
        </div>
    </div>

</main>

<jsp:include page="footer.jsp"/>

</body>
</html>