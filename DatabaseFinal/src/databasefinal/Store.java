package databasefinal;

import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author zane
 */
public class Store {

    int storeid;
    String location;
    String storename;

    public void Store(int storeid, String location, String storename) {
        this.storeid = storeid;
        this.location = location;
        this.storename = storename;
    }

    public static void menu() {

        System.out.println("Please make a choice between 1-3:");
        System.out.println("1. View all stores to shop and there location"
                + "\n2. View the most expensive products from each store"
                + "\n3. Add a store"
                + "\n4. Exit to main menu"
        );

        Scanner scan = new Scanner(System.in);

        try {
            int userInput = Integer.parseInt(scan.nextLine());
            switch (userInput) {
                case 1:

                    getStoreNames();
                    menu();

                case 2:

                    getMostExpensiveProducts();
                    menu();

                case 3:

                    AddStore();
                    menu();

                case 4:

                    break;

                default:
                    System.out.println("Please enter a valid number");
                    menu();
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid number");
            menu();
        }
    }

    public static void getStoreNames() {
        
        String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
        String username = "postgres";
        String password = "bluejays123";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement stmt = connection.createStatement();
            String getStoreNames = "Select * From store";
            try (PreparedStatement preparedStatement = connection.prepareStatement(getStoreNames)) {
                ResultSet rs = stmt.executeQuery(getStoreNames);
                
                while (rs.next()) {
                    System.out.println("Store ID: " + rs.getInt("storeid") + " Store Name: " + rs.getString("storename") + " Location: " + rs.getString("location"));
                    System.out.println("--------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load driver");
        }
    }

    public static void getMostExpensiveProducts() {

        String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
        String username = "postgres";
        String password = "bluejays123";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            Statement stmt = connection.createStatement();
            String getStoresHighestProduct = "SELECT s.storeid, s.storename, i.itemname, i.itemcost\n"
                    + "FROM store s\n"
                    + "JOIN department d ON s.storeid = d.storeid\n"
                    + "JOIN (\n"
                    + "    SELECT deptid, MAX(itemcost) AS max_cost\n"
                    + "    FROM item\n"
                    + "    GROUP BY deptid\n"
                    + ") max_items ON d.deptid = max_items.deptid\n"
                    + "JOIN item i ON max_items.deptid = i.deptid AND max_items.max_cost = i.itemcost;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(getStoresHighestProduct)) {
                
                ResultSet rs = stmt.executeQuery(getStoresHighestProduct);
                
                while (rs.next()) {
                    System.out.println("Store ID: " + rs.getInt("storeid") + " Store Name: " + rs.getString("storename") + "\n" + " Item Name: " + rs.getString("itemname") + " Highest Priced Item In Store Cost " + rs.getDouble("itemcost"));
                    System.out.println("--------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load driver");
        }
    }

    public static void AddStore() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
        String username = "postgres";
        String password = "bluejays123";

        try {
            Scanner scan = new Scanner(System.in);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            String select = "INSERT INTO store (storename, location) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(select);

            System.out.println("Enter the store name: ");
            String storename = scan.nextLine();
            pstmt.setString(1, storename);

            System.out.println("Enter the store location: ");
            String storeLocation = scan.nextLine();
            pstmt.setString(2, storeLocation);

            pstmt.executeUpdate();
            
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot Load Driver");
        } catch (SQLException e) { }
    }
}