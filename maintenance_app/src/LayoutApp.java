import java.sql.*;
import java.util.*;

public class LayoutApp {

    static final String URL="jdbc:postgresql://localhost:5432/test";
    static final String USER="postgres";
    static final String PASS="Amirtha@134"; // change

    static Connection getCon() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL,USER,PASS);
    }

    // ---------- LOGIN ----------
    static boolean adminLogin(String u,String p){
        try(Connection con=getCon()){
            PreparedStatement ps=con.prepareStatement(
            "SELECT 1 FROM admin_login WHERE username=? AND password=?");
            ps.setString(1,u); ps.setString(2,p);
            return ps.executeQuery().next();
        }catch(Exception e){e.printStackTrace();}
        return false;
    }

    static int ownerLogin(String u,String p){
        try(Connection con=getCon()){
            PreparedStatement ps=con.prepareStatement(
            "SELECT owner_id FROM owners WHERE username=? AND password=?");
            ps.setString(1,u); ps.setString(2,p);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) return rs.getInt(1);
        }catch(Exception e){e.printStackTrace();}
        return -1;
    }

    // ---------- OWNER ----------
    static void addSite(int ownerId){
        try(Connection con=getCon()){
            Scanner sc=new Scanner(System.in);
            System.out.print("Type:  (Villa/Apartment/House/Open):");
            String type=sc.nextLine();
            String status = type.equalsIgnoreCase("Open") ? "OPEN" : "OCCUPIED";

            System.out.print("Size:");
            String size=sc.nextLine();
            System.out.print("Sqft:");
            int sqft=sc.nextInt();
 
    
             PreparedStatement ps = con.prepareStatement(
        "INSERT INTO site_requests(owner_id,site_type,size,sqft,status) VALUES(?,?,?,?,?)");

        ps.setInt(1, ownerId);
        ps.setString(2, type);
        ps.setString(3, size);
        ps.setInt(4, sqft);
        ps.setString(5, "PENDING");
            ps.executeUpdate();

            System.out.println("Sent for Approval");
        }catch(Exception e){e.printStackTrace();}
    }
    
    static void viewMySite(int ownerId){
        try(Connection con=getCon()){
            PreparedStatement ps=con.prepareStatement(
            "SELECT site_type,size,sqft FROM sites WHERE owner_id=?");
            ps.setInt(1,ownerId);
            ResultSet rs=ps.executeQuery();

            boolean found=false;
            while(rs.next()){
                found=true;
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getInt(3));
            }
            if(!found) System.out.println("No Approved Sites");
        }catch(Exception e){e.printStackTrace();}
    }
   static void viewMyRequests(int ownerId){
    try(Connection con=getCon()){
        PreparedStatement ps=con.prepareStatement(
        "SELECT site_type,size,sqft,status FROM site_requests WHERE owner_id=?");
        ps.setInt(1,ownerId);
        ResultSet rs=ps.executeQuery();

        boolean found=false;
        while(rs.next()){
            found=true;
            System.out.println(
            rs.getString(1)+" "+
            rs.getString(2)+" "+
            rs.getInt(3)+" "+
            rs.getString(4));
        }
        if(!found) System.out.println("No Requests");
    }catch(Exception e){e.printStackTrace();}
}


    // ---------- ADMIN ----------
    static void viewAllSites(){
        try(Connection con=getCon()){
            ResultSet rs=con.createStatement().executeQuery("SELECT * FROM sites");
            while(rs.next()){
                System.out.println(
                rs.getInt("site_id")+" "+
                rs.getInt("owner_id")+" "+
                rs.getString("site_type")+" "+
                rs.getString("size")+" "+
                rs.getInt("sqft"));
            }
        }catch(Exception e){e.printStackTrace();}
    }

   static void addOwner(){
    try(Connection con=getCon()){
        Scanner sc=new Scanner(System.in);

        System.out.print("Name:");
        String name=sc.nextLine();
        System.out.print("Username:");
        String user=sc.nextLine();
        System.out.print("Password:");
        String pass=sc.nextLine();

        PreparedStatement ps=con.prepareStatement(
        "INSERT INTO owners(name,username,password) VALUES(?,?,?)");
        ps.setString(1,name);
        ps.setString(2,user);
        ps.setString(3,pass);
        ps.executeUpdate();

        System.out.println("Owner Created");
    }catch(Exception e){e.printStackTrace();}
}

    static void showOwners(){
    try(Connection con=getCon()){
        ResultSet rs=con.createStatement().executeQuery(
        "SELECT owner_id,name,username FROM owners");

        while(rs.next()){
            System.out.println(
            rs.getInt(1)+" "+
            rs.getString(2)+" "+
            rs.getString(3));
        }
    }catch(Exception e){e.printStackTrace();}
}


     static void removeOwner(){
    try(Connection con=getCon()){
        Scanner sc=new Scanner(System.in);
        showOwners();
        System.out.print("Enter Owner ID to Remove: ");
        int id=sc.nextInt();

        // Step 1 – delete pending requests
        PreparedStatement ps1=con.prepareStatement(
        "DELETE FROM site_requests WHERE owner_id=?");
        ps1.setInt(1,id);
        ps1.executeUpdate();

        // Step 2 – delete approved sites
        PreparedStatement ps2=con.prepareStatement(
        "DELETE FROM sites WHERE owner_id=?");
        ps2.setInt(1,id);
        ps2.executeUpdate();

        // Step 3 – delete owner
        PreparedStatement ps3=con.prepareStatement(
        "DELETE FROM owners WHERE owner_id=?");
        ps3.setInt(1,id);
        int rows=ps3.executeUpdate();

        if(rows>0)
            System.out.println("Owner Removed Successfully");
        else
            System.out.println("Owner Not Found");

    }catch(Exception e){
        e.printStackTrace();
    }
}
   static void generateMaintenance(){
    try(Connection con=getCon()){

        PreparedStatement ps=con.prepareStatement(
        "SELECT site_id,sqft,status FROM sites");
        ResultSet rs=ps.executeQuery();

        while(rs.next()){
            int id=rs.getInt(1);
            int sqft=rs.getInt(2);
            String status=rs.getString(3);

            double amount=calculateMaintenance(sqft,status);

            PreparedStatement upd=con.prepareStatement(
            "UPDATE sites SET maintenance_due=? WHERE site_id=?");
            upd.setDouble(1,amount);
            upd.setInt(2,id);
            upd.executeUpdate();
        }

        System.out.println("Maintenance Generated");

    }catch(Exception e){e.printStackTrace();}
}

    static void collectMaintenance(){
    try(Connection con=getCon()){
        Scanner sc=new Scanner(System.in);

        ResultSet rs=con.createStatement().executeQuery(
        "SELECT site_id,owner_id,maintenance_due FROM sites WHERE maintenance_due>0");

        boolean found=false;
        while(rs.next()){
            found=true;
            System.out.println(
            rs.getInt(1)+" "+
            rs.getInt(2)+" "+
            rs.getDouble(3));
        }

        if(!found){
            System.out.println("No Pending Maintenance");
            return;
        }

        System.out.print("Enter Site ID to Collect: ");
        int id=sc.nextInt();

        PreparedStatement ps=con.prepareStatement(
        "UPDATE sites SET maintenance_due=0 WHERE site_id=?");
        ps.setInt(1,id);
        ps.executeUpdate();

        System.out.println("Maintenance Collected");

    }catch(Exception e){e.printStackTrace();}
}
  
    static double calculateMaintenance(int sqft, String status){
    if(status.equalsIgnoreCase("OPEN"))
        return sqft * 6;
    else
        return sqft * 9;
}

    static boolean showPending(){
         boolean found = false;
        try(Connection con=getCon()){
            ResultSet rs=con.createStatement().executeQuery(
            "SELECT * FROM site_requests WHERE status='PENDING'");
            while(rs.next()){
                found = true;
                System.out.println(
                rs.getInt("req_id")+" "+
                rs.getInt("owner_id")+" "+
                rs.getString("site_type")+" "+
                rs.getString("size")+" "+
                rs.getInt("sqft"));
            }
           if (!found)
            System.out.println("No Pending Requests");
        }catch(Exception e){e.printStackTrace();}
    return found;
    }

    static void approve(){
        try(Connection con=getCon()){
            Scanner sc=new Scanner(System.in);
            if(!showPending()) return; 
            
            System.out.print("Req ID:");
            int id=sc.nextInt();

            PreparedStatement get=con.prepareStatement(
            "SELECT owner_id,site_type,size,sqft FROM site_requests WHERE req_id=?");
            get.setInt(1,id);
            ResultSet rs=get.executeQuery();

            if(rs.next()){
                PreparedStatement ins=con.prepareStatement(
                "INSERT INTO sites(owner_id,site_type,size,sqft) VALUES(?,?,?,?)");
                ins.setInt(1,rs.getInt(1));
                ins.setString(2,rs.getString(2));
                ins.setString(3,rs.getString(3));
                ins.setInt(4,rs.getInt(4));
                ins.executeUpdate();

                PreparedStatement upd=con.prepareStatement(
                "UPDATE site_requests SET status='APPROVED' WHERE req_id=?");
                upd.setInt(1,id);
                upd.executeUpdate();

                System.out.println("Approved");
            }
        }catch(Exception e){e.printStackTrace();}
    }

    static void reject(){
        try(Connection con=getCon()){
            Scanner sc=new Scanner(System.in);
            if(!showPending()) return; 
            
            System.out.print("Req ID:");
            int id=sc.nextInt();

            PreparedStatement ps=con.prepareStatement(
            "UPDATE site_requests SET status='REJECTED' WHERE req_id=?");
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Rejected");
        }catch(Exception e){e.printStackTrace();}
    }

    // ---------- MENUS ----------
    static void ownerMenu(int id){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("\n1.Add Site 2.View approved Site 3.View Requests 4.Exit");
            int ch=sc.nextInt(); sc.nextLine();
            if(ch==1) addSite(id);
            
            else if(ch==2) viewMySite(id);
            else if(ch==3) viewMyRequests(id);
            else return;
        }
    }

    static void adminMenu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("\n 1.Add Owner 2.Remove Owner 3.View Sites 4.Approve 5.Reject 6.Generate Maintenance 7.Collect Maintenance 8.Exit");
            int ch=sc.nextInt();
            if(ch==1) addOwner();
            else if(ch==2) removeOwner();
            else if(ch==3) viewAllSites();
            else if(ch==4) approve();
            else if(ch==5) reject();
            else if(ch==6) generateMaintenance();
            else if(ch==7) collectMaintenance();
            else return;
        }
    }

    // ---------- MAIN ----------
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("1.Admin 2.Owner");
        int role=sc.nextInt(); sc.nextLine();

        if(role==1){
            System.out.print("Username:");
            String u=sc.nextLine();
            System.out.print("Password:");
            String p=sc.nextLine();
            if(adminLogin(u,p)) adminMenu();
            else System.out.println("Invalid Admin");
        } else {
            System.out.print("Username:");
            String u=sc.nextLine();
            System.out.print("Password:");
            String p=sc.nextLine();
            int id=ownerLogin(u,p);
            if(id!=-1) ownerMenu(id);
            else System.out.println("Invalid Owner");
        }
    }
}
