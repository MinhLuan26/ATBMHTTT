// ====== LẤY CONTEXT PATH ======
const contextPath = document.body.getAttribute("data-context");

// ====== ĐỒNG BỘ SỐ LƯỢNG ======
const q = document.getElementById("quantity");
const buy = document.getElementById("buy-now-quantity");
const cart = document.getElementById("cart-quantity");

if (q && buy && cart) {
    q.addEventListener("input", () => {
        buy.value = q.value;
        cart.value = q.value;
    });
}

// ====== TOAST THÔNG BÁO ======
function showToast(msg) {
    let toast = document.createElement("div");
    toast.className = "toast-msg";
    toast.textContent = msg;
    document.body.appendChild(toast);

    setTimeout(() => toast.classList.add("show"), 10);
    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => toast.remove(), 300);
    }, 2000);
}

// ====== WISHLIST TOGGLE ======
document.addEventListener("DOMContentLoaded", function () {
    const hearts = document.querySelectorAll(".wishlist-heart");

    hearts.forEach(h => {
        h.addEventListener("click", function () {
            const bookId = this.getAttribute("data-id");

            fetch(contextPath + "/wishlist", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "action=toggle&bookId=" + bookId
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    this.classList.toggle("active", data.status === "added");
                    showToast(data.message);
                }
            });
        });
    });
});
