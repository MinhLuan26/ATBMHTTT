document.addEventListener("DOMContentLoaded", function () {

    const input = document.getElementById("searchInput");
    const box = document.getElementById("searchSuggest");

    let debounceTimer = null;

    input.addEventListener("keyup", function (e) {
        const keyword = this.value.trim();
        if (e.key === "Enter") {
            box.style.display = "none";
            return;
        }
        if (keyword.length === 0) {
            box.innerHTML = "";
            box.style.display = "none";
            return;
        }
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
         fetch(contextPath + "/search-suggest?q=" + encodeURIComponent(keyword))

                .then(res => res.json())
                .then(data => renderSuggest(data, keyword))
                .catch(err => console.error(err));
        }, 300);
    });
	
    document.addEventListener("click", function (e) {
        if (!e.target.closest(".search-area")) {
            box.style.display = "none";
        }
    });

    function renderSuggest(data, keyword) {
        box.innerHTML = "";

        if (!data || data.length === 0) {
            box.style.display = "none";
            return;
        }

        data.forEach(book => {
            const div = document.createElement("div");
            div.className = "suggest-item";
            const regex = new RegExp(`(${keyword})`, "gi");
            div.innerHTML = book.title.replace(regex, "<strong>$1</strong>");

            div.addEventListener("click", function () {
                input.value = book.title;
                window.location.href =
                    "search?query=" + encodeURIComponent(book.title);
            });

            box.appendChild(div);
        });

        box.style.display = "block";
    }
});
