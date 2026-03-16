import React,{useState} from "react";

function DepositCalculator(){

  const[amount,setAmount]=useState("");
  const[years,setYears]=useState("");
  const[result,setResult]=useState("");

  const calculate=()=>{

      let rate=7;

      let interest=(amount*rate*years)/100;
      let total=Number(amount)+interest;

      setResult(`Total Amount = ₹${total}`);
  }

  return(

    <div>

      <h2>Deposit Calculator</h2>

      Amount
      <input type="number"
      onChange={(e)=>setAmount(e.target.value)} />

      <br/><br/>

      Years
      <input type="number"
      onChange={(e)=>setYears(e.target.value)} />

      <br/><br/>

      <button onClick={calculate}>
        Calculate
      </button>

      <h3>{result}</h3>

    </div>

  )
}

export default DepositCalculator