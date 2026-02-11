// ---------- Navigation ----------
function navigate(page) {
    window.location.href = page;
}

// ---------- Books Page Logic ----------
document.addEventListener("DOMContentLoaded", function () {
    const bookForm = document.getElementById("bookForm");

    if(bookForm) {
        bookForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const bookId = document.getElementById("bookId").value;
            const title = document.getElementById("title").value;
            const author = document.getElementById("author").value;
            const category = document.getElementById("category").value;
            const copies = document.getElementById("copies").value;

            if (!bookId || !title || !author || !category || !copies) {
                alert("Please fill all fields");
                return;
            }

            const table = document.querySelector("#bookTable tbody");

            const row = `
                <tr>
                    <td>${bookId}</td>
                    <td>${title}</td>
                    <td>${author}</td>
                    <td>${category}</td>
                    <td>${copies}</td>
                </tr>
            `;

            table.innerHTML += row;

            bookForm.reset();
        });
    }
});

// ---------- Members Page Logic ----------

document.addEventListener("DOMContentLoaded", function () {

    const memberForm = document.getElementById("memberForm");

    if (memberForm) {

        memberForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const memberId = document.getElementById("memberId").value;
            const memberName = document.getElementById("memberName").value;

            if (!memberId || !memberName) {
                alert("Please fill all fields");
                return;
            }

            const table = document.querySelector("#memberTable tbody");

            const row = `
                <tr>
                    <td>${memberId}</td>
                    <td>${memberName}</td>
                </tr>
            `;

            table.innerHTML += row;

            memberForm.reset();
        });

    }

});

// ---------- Issue Book Logic ----------

document.addEventListener("DOMContentLoaded", function () {

    const issueForm = document.getElementById("issueForm");

    if (issueForm) {

        issueForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const memberId = document.getElementById("issueMemberId").value;
            const bookId = document.getElementById("issueBookId").value;

            if (!memberId || !bookId) {
                alert("Please enter Member ID and Book ID");
                return;
            }

            alert("Book issued successfully (Simulation)");

            issueForm.reset();
        });
    }
});

// ---------- Return Book Logic ----------

document.addEventListener("DOMContentLoaded", function () {

    const returnForm = document.getElementById("returnForm");

    if (returnForm) {

        returnForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const memberId = document.getElementById("returnMemberId").value;
            const bookId = document.getElementById("returnBookId").value;

            if (!memberId || !bookId) {
                alert("Please enter Member ID and Book ID");
                return;
            }

            alert("Book returned successfully (Simulation)");

            returnForm.reset();
        });
    }
});

