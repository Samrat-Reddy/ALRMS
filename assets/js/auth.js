document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;
    const errorMessage = document.getElementById("errorMessage");

    errorMessage.textContent = "";

    if (email === "" || password === "" || role === "") {
        errorMessage.textContent = "Please fill all fields before logging in.";
        return;
    }

    const user = {
        email: email,
        role: role,
        loginTime: new Date().toISOString()
    };

    localStorage.setItem("alrmsUser", JSON.stringify(user));

    if (role === "student") {
        window.location.href = "../pages/student.html";
    } else {
        window.location.href = "../pages/admin.html";
    }
});
