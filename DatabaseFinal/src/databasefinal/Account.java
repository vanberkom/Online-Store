
package databasefinal;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author luke
 */
public class Account {
    
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
    static String username = "postgres";
    static String password = "bluejays123";
    
    public static void listAccountIDs() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement stmt = connection.createStatement();
            String select = "Select accountid FROM account;";
            ResultSet rs = stmt.executeQuery(select);
            while(rs.next()) {
                System.out.println("Account ID: " + rs.getInt(1));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
    public static void listAccountsByFirstName() {
        Scanner scan = new Scanner(System.in);
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String select = "Select * FROM account where firstname like ?";
            PreparedStatement stmt = connection.prepareStatement(select);
            System.out.println("Enter a first name to get info about the account:");
            String first = "%" + scan.nextLine() + "%";
            stmt.setString(1, first);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                System.out.println("Account ID: " + rs.getInt(1) + " First: " + rs.getString(2) + " Last: " + rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
    public static void getAllAccounts() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement stmt = connection.createStatement();
            String select = "Select * FROM account;";
            ResultSet rs = stmt.executeQuery(select);
            while(rs.next()) {
                System.out.println("Account ID: " + rs.getInt(1) + " First: " + rs.getString(2) + " Last: " + rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
    public static void insertAccount() {
        try {
            Scanner scan = new Scanner(System.in);
            
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            String select = "INSERT INTO account (firstname, lastname) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(select);
            
            System.out.println("Enter the first name: ");
            String first = scan.nextLine();
            pstmt.setString(1, first);
            
            System.out.println("Enter the last name: ");
            String last = scan.nextLine();
            pstmt.setString(2, last);
            
            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
    public static void Menu() {
        
        System.out.println("\nAccount Menu:\n1. Get all Accounts\n2. List all IDs\n3. Add a new Account\n4. Search by First Name:\n5. Exit to Main Menu");
        System.out.println("Choose a value between 1-5:");
        Scanner scan = new Scanner(System.in);
        
        try {
            int choice = Integer.parseInt(scan.nextLine());
            switch (choice) {
                case 1:
                    getAllAccounts();
                    Menu();
                break;
                case 2:
                    listAccountIDs();
                    Menu();
                break;
                case 3:
                    insertAccount();
                    Menu();
                break;
                case 4:
                    listAccountsByFirstName();
                    Menu();
                break;
                case 5:
                    System.out.println("Exiting to main menu.\n");
                break;
                default:
                    System.out.println("Enter a valid value.");
                    Menu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid value.");
            Menu();
        }
    }
    
}
