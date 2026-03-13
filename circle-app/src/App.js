import React, { useState } from "react";
import "./App.css";

function App() {

  const [radius, setRadius] = useState("");

  const diameter = radius ? 2 * radius : 0;
  const perimeter = radius ? (2 * 3.142 * radius).toFixed(2) : 0;
  const area = radius ? (3.142 * radius * radius).toFixed(1) : 0;

  return (
    <div className="container">

      <div className="card">
        <h2>Circle Calculator</h2>

        <input
          type="number"
          placeholder="Enter Radius"
          value={radius}
          onChange={(e) => setRadius(e.target.value)}
        />

        <div className="result">
          <p><b>Radius :</b> {radius}</p>
          <p><b>Diameter :</b> {diameter}</p>
          <p><b>Perimeter :</b> {perimeter}</p>
          <p><b>Area :</b> {area}</p>
        </div>

      </div>

    </div>
  );
}

export default App;