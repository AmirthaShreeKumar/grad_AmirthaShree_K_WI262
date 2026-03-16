import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Home from "./Components/Home";
import LoanCalculator from "./Components/LoanCalculator";
import DepositCalculator from "./Components/DepositCalculator";
import Navbar from "./Components/Navbar";

function App() {
  return (
    <Router>

      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/loan" element={<LoanCalculator />} />
        <Route path="/deposit" element={<DepositCalculator />} />
      </Routes>

    </Router>
  );
}

export default App;