// LOGIN PROTECTION
const user = JSON.parse(localStorage.getItem("alrmsUser"));

if (!user || user.role !== "admin") {
    window.location.href = "../../pages/login.html";
}

const roleSpan = document.getElementById("userRole");
if (roleSpan && user) {
    roleSpan.textContent = "Admin";
}

// LOGOUT FUNCTIONALITY
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {
    logoutBtn.addEventListener("click", function () {
        localStorage.removeItem("alrmsUser");
        window.location.href = "../../pages/login.html";
    });
}
