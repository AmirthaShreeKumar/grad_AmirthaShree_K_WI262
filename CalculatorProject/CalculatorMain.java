public class CalculatorMain {

    public static void main(String[] args) {

        Calculator c = new Calculator();

        System.out.println("Addition: " + c.add(10,5));
        System.out.println("Subtraction: " + c.sub(10,5));
        System.out.println("Multiplication: " + c.mul(10,5));
        System.out.println("Division: " + c.div(10,5));
    }
}