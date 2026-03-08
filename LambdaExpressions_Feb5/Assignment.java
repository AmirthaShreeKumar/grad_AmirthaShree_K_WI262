class A {
    public void abc(String name) {
        for(int i=1; i<=50; i++) {
            System.out.println(name+" : "+i+" mtrs");
            try {
                Thread.sleep(300);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}
public class Assignment {
    public static void main(String args[]) {

        A obj = new A();

        Thread t1 = new Thread(() -> obj.abc("Ram"));
        Thread t2 = new Thread(() -> obj.abc("Shyam"));
        Thread t3 = new Thread(() -> obj.abc("John"));

        t1.start();
        t2.start();
        t3.start();
    }
}
