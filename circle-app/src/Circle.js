import React, { useState } from "react";

function Circle() {
  const [radius, setRadius] = useState(0);
  const pi = 3.142;

  const diameter = 2 * radius;
  const perimeter = 2 * pi * radius;
  const area = pi * radius * radius;

  return (
    <div>
      <h2>Details of Circle</h2>

      Enter Radius:
      <input
        type="number"
        onChange={(e) => setRadius(Number(e.target.value))}
      />

      <p>Radius : {radius}</p>
      <p>Diameter : {diameter}</p>
      <p>Perimeter : {perimeter}</p>
      <p>Area : {area}</p>
    </div>
  );
}

export default Circle;