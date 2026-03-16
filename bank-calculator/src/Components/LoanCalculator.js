import React,{useState} from "react";
import { calculateLoan } from "../utils/loanLogic";

function LoanCalculator(){

  const[name,setName]=useState("");
  const[type,setType]=useState("");
  const[amount,setAmount]=useState("");
  const[duration,setDuration]=useState("");
  const[result,setResult]=useState("");

  const calculate=()=>{

    let output = calculateLoan(
        name,
        type,
        Number(amount),
        Number(duration)
    );

    setResult(output);
  }

  return(
    <div>

      <h2>Loan Calculator</h2>

      Name
      <input onChange={(e)=>setName(e.target.value)} />

      <br/><br/>

      Loan Type
      <select onChange={(e)=>setType(e.target.value)}>
        <option value="">Select</option>
        <option value="home">Home</option>
        <option value="car">Car</option>
        <option value="personal">Personal</option>
      </select>

      <br/><br/>

      Amount
      <input type="number"
      onChange={(e)=>setAmount(e.target.value)} />

      <br/><br/>

      Duration
      <input type="number"
      onChange={(e)=>setDuration(e.target.value)} />

      <br/><br/>

      <button onClick={calculate}>
        Calculate EMI
      </button>

      <h3>{result}</h3>

    </div>
  )
}

export default LoanCalculator