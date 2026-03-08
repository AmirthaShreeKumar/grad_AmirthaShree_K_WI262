function showSection(sectionId) {
    let sections = document.querySelectorAll(".section");
    sections.forEach(s => s.style.display = "none");
    
    document.getElementById(sectionId).style.display = "block";
    
    // Clear dynamic areas when switching main sections
    if(sectionId !== 'services') document.getElementById('serviceDetailArea').innerHTML = '';
    if(sectionId === 'netbanking') showLogin();

    // Scroll to top
    window.scrollTo(0, 0);
}

function renderService(type) {
    const area = document.getElementById('serviceDetailArea');
    let content = "";

    if(type === 'loan') {
        content = `
            <div class="card border-primary p-4 bg-light shadow">
                <h3>Loan Department</h3>
                <p>Apply for Home, Car, or Education loans at competitive rates.</p>
                <ul class="list-group mb-3">
                    <li class="list-group-item">Home Loan: 8.4%</li>
                    <li class="list-group-item">Car Loan: 9.2%</li>
                </ul>
                <button class="btn btn-primary w-25">Apply Now</button>
            </div>`;
    } else if (type === 'deposit') {
        content = `
            <div class="card border-success p-4 bg-light shadow">
                <h3>Investment Plans</h3>
                <p>Secure your future with our Fixed Deposits.</p>
                <div class="alert alert-info">Current FD Rate: 7.5% for Senior Citizens</div>
                <button class="btn btn-success w-25">Open FD</button>
            </div>`;
    } else {
        content = `<div class="card border-warning p-4 bg-light shadow"><h3>Quick Account Opening</h3><p>Complete your KYC online.</p></div>`;
    }

    area.innerHTML = content;
    area.scrollIntoView({ behavior: 'smooth' });
}

function showLogin() {
    document.getElementById("formArea").innerHTML = `
    <div class="card p-4 shadow-lg border-0">
        <div class="text-center mb-4">
            <i class="bi bi-person-circle display-4 text-primary"></i>
        </div>
        <input type="text" class="form-control mb-3 py-2" placeholder="Customer ID">
        <input type="password" class="form-control mb-4 py-2" placeholder="Password">
        <button class="btn btn-primary w-100 py-2 fw-bold shadow">SECURE LOGIN</button>
        <p class="mt-3 small text-muted">Forgot password? <a href="#">Click here</a></p>
    </div>`;
}

function showSignup() {
    document.getElementById("formArea").innerHTML = `
    <div class="card p-4 shadow-lg border-0">
        <h4 class="mb-4 fw-bold">Join ABC Bank</h4>
        <input type="text" class="form-control mb-2" placeholder="Full Name">
        <input type="email" class="form-control mb-2" placeholder="Email Address">
        <input type="tel" class="form-control mb-3" placeholder="Phone Number">
        <button class="btn btn-success w-100 py-2 fw-bold shadow">CREATE ACCOUNT</button>
    </div>`;
}