import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db:5432/test";
        String user = "postgres";
        String password = "Amirtha@134";

        System.out.println("Connecting to database: " + url);

        for (int i = 0; i < 10; i++) {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {

                Statement stmt = conn.createStatement();

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id SERIAL, name VARCHAR(255))");

                stmt.executeUpdate("INSERT INTO users (name) VALUES ('Java Docker User')");

                System.out.println("Record inserted successfully!");
                break;

            } catch (Exception e) {
                System.out.println("Connection failed, retrying...");
                try { Thread.sleep(3000); } catch (InterruptedException ie) {}
            }
        }

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {}
    }
}