import java.sql.*;
import java.util.*;

public class LayoutApp {

    // ---------------- DB CONNECTION ----------------
    static class DBUtil {
        static Connection getConnection() throws Exception {
            String url = "jdbc:postgresql://localhost:5432/layoutdb";
            String user = "postgres";
            String pass = "Amirtha@134";
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, pass);
        }
    }

    // ---------------- MODELS ----------------
    static class User {
        int id;
        String username;
        String role;
    }

    static class Site {
        int siteNo;
        String dimension;
        int area;
        String propertyType;
        int perSqft;
        int totalAmount;
        int balance;
        String status;
        int ownerId;
        String siteType;
    }

    // ---------------- DAO INTERFACES ----------------
    interface UserDAO {
        User login(String u, String p);
        void registerOwner(String u, String p);
    }

    interface SiteDAO {
        List<Site> getAllSites();
        Site getSite(int siteNo);
        void assignOwner(int siteNo, int ownerId, String propertyType);
        void collectPayment(int siteNo, int amount);
        List<Site> getPendingSites();
        List<Site> getOwnerSites(int ownerId);
        void requestUpdate(int siteNo, String propertyType);
        void approveUpdate(int siteNo, boolean approve);
    }

    // ---------------- DAO IMPLEMENTATIONS ----------------
    static class UserDAOImpl implements UserDAO {

        public User login(String u, String p) {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, u);
                ps.setString(2, p);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    User user = new User();
                    user.id = rs.getInt("user_id");
                    user.username = rs.getString("username");
                    user.role = rs.getString("role");
                    return user;
                }
            } catch (Exception e) { e.printStackTrace(); }
            return null;
        }

        public void registerOwner(String u, String p) {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "INSERT INTO users(username,password,role) VALUES(?,?, 'OWNER')";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, u);
                ps.setString(2, p);
                ps.executeUpdate();
                System.out.println("Owner Registered");
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    static class SiteDAOImpl implements SiteDAO {

        public List<Site> getAllSites() {
            List<Site> list = new ArrayList<>();
            try (Connection con = DBUtil.getConnection()) {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM sites");
                while (rs.next()) list.add(map(rs));
            } catch (Exception e) { e.printStackTrace(); }
            return list;
        }

        public Site getSite(int siteNo) {
            try (Connection con = DBUtil.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM sites WHERE site_no=?");
                ps.setInt(1, siteNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return map(rs);
            } catch (Exception e) { e.printStackTrace(); }
            return null;
        }

        public void assignOwner(int siteNo, int ownerId, String propertyType) {
            try (Connection con = DBUtil.getConnection()) {
                String sql =
                    "UPDATE sites SET owner_id=?, property_type=?, site_type='OCCUPIED', " +
                    "per_sqft=9, total_amount=area*9, balance=area*9 WHERE site_no=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, ownerId);
                ps.setString(2, propertyType);
                ps.setInt(3, siteNo);
                ps.executeUpdate();
            } catch (Exception e) { e.printStackTrace(); }
        }

        public void collectPayment(int siteNo, int amount) {
            try (Connection con = DBUtil.getConnection()) {

                // Insert payment
                PreparedStatement ps1 = con.prepareStatement(
                    "INSERT INTO payments(site_no,paid_amount) VALUES(?,?)");
                ps1.setInt(1, siteNo);
                ps1.setInt(2, amount);
                ps1.executeUpdate();

                // Update balance
                PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE sites SET balance=balance-? WHERE site_no=?");
                ps2.setInt(1, amount);
                ps2.setInt(2, siteNo);
                ps2.executeUpdate();

            } catch (Exception e) { e.printStackTrace(); }
        }

        public List<Site> getPendingSites() {
            List<Site> list = new ArrayList<>();
            try (Connection con = DBUtil.getConnection()) {
                ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM sites WHERE status='PENDING'");
                while (rs.next()) list.add(map(rs));
            } catch (Exception e) { e.printStackTrace(); }
            return list;
        }

        public List<Site> getOwnerSites(int ownerId) {
            List<Site> list = new ArrayList<>();
            try (Connection con = DBUtil.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM sites WHERE owner_id=?");
                ps.setInt(1, ownerId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) list.add(map(rs));
            } catch (Exception e) { e.printStackTrace(); }
            return list;
        }

        // owner request
        public void requestUpdate(int siteNo, String propertyType) {
            try (Connection con = DBUtil.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE sites SET property_type=? WHERE site_no=?");
                ps.setString(1, propertyType);
                ps.setInt(2, siteNo);
                ps.executeUpdate();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // admin approval
       
        public void approveUpdate(int siteNo, boolean approve) {
            try (Connection con = DBUtil.getConnection()) {
                if (approve) {
                    con.createStatement().executeUpdate(
                        "UPDATE sites SET per_sqft=9,total_amount=area*9,balance=area*9 WHERE site_no=" + siteNo);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        private Site map(ResultSet rs) throws Exception {
            Site s = new Site();
            s.siteNo = rs.getInt("site_no");
            s.dimension = rs.getString("dimension");
            s.area = rs.getInt("area");
            s.propertyType = rs.getString("property_type");
            s.perSqft = rs.getInt("per_sqft");
            s.totalAmount = rs.getInt("total_amount");
            s.balance = rs.getInt("balance");
            s.status = rs.getString("status");
            s.ownerId = rs.getInt("owner_id");
            s.siteType = rs.getString("site_type");
            return s;
        }
    }
     static void showAllOwners() {
    try (Connection con = DBUtil.getConnection()) {
        ResultSet rs = con.createStatement().executeQuery(
            "SELECT user_id, username FROM users WHERE role='OWNER'");

        System.out.println("\nAVAILABLE OWNERS:");
        System.out.println("----------------------------");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("user_id") +
                               " | Username: " + rs.getString("username"));
        }
        System.out.println("----------------------------");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   
    // ---------------- MENUS ----------------
    static Scanner sc = new Scanner(System.in);
    static UserDAO userDAO = new UserDAOImpl();
    static SiteDAO siteDAO = new SiteDAOImpl();

    static void adminMenu(User admin) {
        while (true) {
            System.out.println("\nADMIN MENU");
            System.out.println("1.View All Sites");
            System.out.println("2.Assign Owner");
            System.out.println("3.Collect Payment");
            System.out.println("4.View Pending");
            System.out.println("5.Register Owner");
            System.out.println("6.Exit");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> siteDAO.getAllSites().forEach(s -> printSite(s));
                case 2 -> {
                    System.out.print("Site No:");
                    int sn = sc.nextInt();
                    showAllOwners();
                    System.out.print("Owner ID:");
                    int oid = sc.nextInt();
                    System.out.print("Type(VILLA/APARTMENT/HOUSE):");
                    String t = sc.next();
                    siteDAO.assignOwner(sn, oid, t);
                }
                case 3 -> {
                    System.out.print("Site:");
                    int sn = sc.nextInt();
                    System.out.print("Amount:");
                    int amt = sc.nextInt();
                    siteDAO.collectPayment(sn, amt);
                }
                case 4 -> siteDAO.getPendingSites().forEach(s -> printSite(s));
                case 5 -> {
                    System.out.print("Username:");
                    String u = sc.next();
                    System.out.print("Password:");
                    String p = sc.next();
                    userDAO.registerOwner(u, p);
                }
                case 6 -> { return; }
            }
        }
    }

    static void ownerMenu(User owner) {
        while (true) {
            System.out.println("\nOWNER MENU");
            System.out.println("1.View My Site");
            System.out.println("2.Request Property Update");
            System.out.println("3.Exit");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> siteDAO.getOwnerSites(owner.id).forEach(s -> printSite(s));
                case 2 -> {
                  
    
                    System.out.print("Site No:");
                    int sn = sc.nextInt();
                    System.out.print("New Type:");
                    String t = sc.next();
                    siteDAO.requestUpdate(sn, t);
                }
                case 3 -> { return; }
            }
        }
    }

    static void printSite(Site s) {
        System.out.println(s.siteNo + " | " + s.dimension + " | " +
                s.propertyType + " | Balance:" + s.balance + " | " + s.status);
    }

    // ---------------- MAIN ----------------
   public static void main(String[] args) {

    while (true) {

        System.out.println("\n==== LAYOUT MAINTENANCE APPLICATION ====");
        System.out.println("1. Admin Login");
        System.out.println("2. Owner Login");
        System.out.println("3. Exit");
        System.out.print("Select Role: ");
        int roleChoice = sc.nextInt();

        if (roleChoice == 3) {
            System.out.println("Thank You!");
            break;
        }

        System.out.print("Username: ");
        String u = sc.next();

        System.out.print("Password: ");
        String p = sc.next();

        User user = userDAO.login(u, p);

        if (user == null) {
            System.out.println("Invalid Credentials!");
            continue;
        }

        // Role validation
        if (roleChoice == 1 && user.role.equals("ADMIN")) {
            adminMenu(user);

        } else if (roleChoice == 2 && user.role.equals("OWNER")) {
            ownerMenu(user);

        } else {
            System.out.println("Role Mismatch! Access Denied.");
        }
    }
}

}
