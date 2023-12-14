package databasefinal;

import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author luke
 */
public class Item {

    int id;
    String name;
    String description;
    double cost;

    static String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
    static String username = "postgres";
    static String password = "bluejays123";

    public void getItemsByNames(String n) {
       try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM Item WHERE itemname like ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, "%" + n + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                System.out.println("Item ID: " + rs.getInt(1) + " Item Name: " + rs.getString(2) + " Item Description: " + rs.getString(3) + " Item Cost: " + rs.getDouble(4));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    /**
     * 
     * @param i 
     */
    public void getItemsByID(int i) {
       try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM Item WHERE itemid = ?";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, i);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Item ID: " + rs.getInt(1) + " Item Name: " + rs.getString(2) + " Item Description: " + rs.getString(3) + " Item Cost: " + rs.getDouble(4));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    /**
     * 
     * @param n
     */
    public void getItemsByDescription(String n) {
       try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM Item WHERE itemdescription like ?";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, "%" + n + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Item ID: " + rs.getInt(1) + " Item Name: " + rs.getString(2) + " Item Description: " + rs.getString(3) + " Item Cost: " + rs.getDouble(4));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    /**
     * 
     * @param d
     */
    public void getItemsBycost(double d) {
       try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM Item WHERE itemcost = ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setDouble(1, d);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Item ID: " + rs.getInt(1) + " Item Name: " + rs.getString(2) + " Item Description: " + rs.getString(3) + " Item Cost: " + rs.getDouble(4));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
    /**
     * 
     */
    public void makeNewItem() {
         try {
            Scanner scan = new Scanner(System.in);
            
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            String select = "INSERT INTO Item (itemname, itemdescription, itemcost, deptid) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(select);
            
            System.out.println("Enter the Item name: ");  
            String itemname = scan.nextLine();
            pstmt.setString(1, itemname);
            
            System.out.println("Enter the Item description: ");
            String itemDescription = scan.nextLine();
            pstmt.setString(2, itemDescription);
            
            System.out.println("Enter the Item Cost: ");
            double itemcost = scan.nextInt();
            pstmt.setDouble(3, itemcost);
            
            System.out.println("Enter the department ID: ");
            int deptid= scan.nextInt();
            pstmt.setInt(4, deptid);
            
            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    /**
     * 
     */
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        Item item = new Item();
        boolean state = true;
        
        while (state) {
            
            System.out.println("\nItem Menu:");
            System.out.println("1. Get Items by ID");
            System.out.println("2. Get Items by Name");
            System.out.println("3. Get Items by Description");
            System.out.println("4. Get Items by Cost");
            System.out.println("5. Add New Item");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Item Id: ");
                    id = scanner.nextInt();
                    item.getItemsByID(id);
                    break;
                    
                case 2:
                    
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Item Name: ");
                    name = scanner.nextLine();
                    item.getItemsByNames(name);
                    break;
                    
                case 3:
                    
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Item Description: ");
                    description = scanner.nextLine();
                    item.getItemsByDescription(description);
                    break;
                    
                case 4:
                    
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Item Cost: ");
                    cost = scanner.nextDouble();
                    item.getItemsBycost(cost);
                    break;
                    
                case 5: 
                    
                    makeNewItem();
                    break;
                    
                case 6:
                    
                    state = false;
                    break;
                    
                default:
                    System.out.println("Invalid Choice. Please enter a number between 1 and 6.");
            }
        }
    }
}


