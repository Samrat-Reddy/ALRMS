/* ============================================================
   ALRMS — Charts Module
   Purpose: Reusable dashboard visualizations (mock data)
   Library: Chart.js (CDN)
   ============================================================ */

/* ---------- Common Chart Options ---------- */
const baseOptions = {
    responsive: true,
    maintainAspectRatio: false,   // works WITH fixed container
    plugins: {
        legend: {
            labels: {
                font: {
                    family: 'DM Sans',
                    size: 11
                }
            }
        }
    }
};

/* ---------- Public Dashboard Chart ---------- */
function renderPublicOverviewChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Computer Science', 'Mathematics', 'Electronics', 'Mechanical'],
            datasets: [{
                label: 'Books',
                data: [120, 90, 70, 50],
                backgroundColor: '#c8922a',
                borderRadius: 6
            }]
        },
        options: {
            ...baseOptions,
            plugins: {
                legend: { display: false }
            }
        }
    });
}

/* ---------- Student Dashboard Chart ---------- */
function renderStudentBookStatusChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Issued', 'Available'],
            datasets: [{
                data: [2, 8],
                backgroundColor: ['#c0392b', '#1e8a5e']
            }]
        },
        options: {
            ...baseOptions,
            cutout: '65%',
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

/* ---------- Admin Dashboard Chart ---------- */
function renderAdminMonthlyIssuesChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May'],
            datasets: [{
                label: 'Books Issued',
                data: [30, 45, 60, 40, 70],
                borderColor: '#1a2540',
                backgroundColor: 'rgba(26,37,64,.1)',
                tension: 0.35,
                fill: true
            }]
        },
        options: {
            ...baseOptions,
            plugins: {
                legend: { display: false }
            }
        }
    });
}