import { Link } from "react-router-dom";

function Navbar() {
  return (
    <div className="navbar">

      <div className="logo">🏦 Bank App</div>

      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/loan">Loan Calculator</Link>
        <Link to="/deposit">Deposit Calculator</Link>
      </div>

    </div>
  );
}

export default Navbar;