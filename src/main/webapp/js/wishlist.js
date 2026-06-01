// Lấy context path từ body
const contextPath = document.body.getAttribute("data-context");

// XÓA KHỎI WISHLIST
document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".btn-remove");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            let id = btn.getAttribute("data-id");

            fetch(contextPath + "/wishlist", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "action=toggle&bookId=" + id
            })
            .then(r => r.json())
            .then(d => {
                if (d.success) {
                    location.reload();
                }
            });
        });
    });
});
