import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {

  const navigate = useNavigate();

  return (

    <div className="home-container">

      <h1>🏦 Bank Calculator</h1>

      <div className="card-container">

        <div className="card">
          <h2>Loan Calculator</h2>
          <p>Calculate EMI for Home, Car and Personal loans.</p>
          <button onClick={() => navigate("/loan")}>
            Open Loan Calculator
          </button>
        </div>

        <div className="card">
          <h2>Deposit Calculator</h2>
          <p>Check maturity amount for your deposits.</p>
          <button onClick={() => navigate("/deposit")}>
            Open Deposit Calculator
          </button>
        </div>

      </div>

    </div>

  );
}

export default Home;