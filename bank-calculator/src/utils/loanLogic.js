export function calculateLoan(name,type,amount,duration){

    let rate;

    if(type==="home"){
        rate=9;

        if(amount < 500000)
            return "Home loan minimum amount is 5 lakhs";

        if(duration>30)
            return "Home loan max duration is 30 years";
    }

    if(type==="car"){
        rate=12;

        if(amount < 100000)
            return "Car loan minimum amount is 1 lakh";

        if(duration>7)
            return "Car loan max duration is 7 years";
    }

    if(type==="personal"){
        rate=15;

        if(amount < 10000)
            return "Personal loan minimum amount is 10000";

        if(duration>5)
            return "Personal loan max duration is 5 years";
    }

    let monthlyRate = rate/(12*100);
    let months = duration*12;

    let emi =
    (amount*monthlyRate*Math.pow(1+monthlyRate,months)) /
    (Math.pow(1+monthlyRate,months)-1);

    return `Hello ${name}, Your EMI is ₹${emi.toFixed(2)}`;
}