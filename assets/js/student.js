// LOGIN PROTECTION
const user = JSON.parse(localStorage.getItem("alrmsUser"));

if (!user || user.role !== "student") {
    // Not logged in or wrong role
    window.location.href = "../../pages/login.html";
}

// DISPLAY ROLE IN NAVBAR
const roleSpan = document.getElementById("userRole");
if (roleSpan && user) {
    roleSpan.textContent = user.role.charAt(0).toUpperCase() + user.role.slice(1);
}


// LOGOUT FUNCTIONALITY
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {
    logoutBtn.addEventListener("click", function () {
        localStorage.removeItem("alrmsUser");
        window.location.href = "../../pages/login.html";
    });
}
