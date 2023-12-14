package databasefinal;

import java.util.Scanner;
import java.sql.*;

/**
 *
 * Author: Russell Abarte
 */

public class Department {

    int deptID;
    int dept_num;
    String dept_description;
    int store_id;

    // JDBC connection parameters
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
    static String username = "postgres";
    static String password = "bluejays123";


    public static void main(String[] args) {
        Department department = new Department();
        department.menu();
    }

    public void menu() {
        
        Scanner scanner = new Scanner(System.in);
        Department department = new Department();
        boolean state = true;

        while (state) {
            System.out.println("Menu:");
            System.out.println("1. Add a new department");
            System.out.println("2. Get departments by ID");
            System.out.println("3. Get departments by name");
            System.out.println("4. Get departments by description");
            System.out.println("5. Get departments by store ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    scanner.nextLine(); // Consume the newline character
                    makeNewDepartment();
                    break;

                case 2:

                    System.out.print("Enter department ID: ");
                    int departmentID = scanner.nextInt();
                    department.getByDeptID(departmentID);
                    break;

                case 3:

                    scanner.nextLine();
                    System.out.print("Enter department name: ");
                    String departmentName = scanner.nextLine();
                    department.getByDeptName(departmentName);
                    break;

                case 4:

                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter department description: ");
                    String description = scanner.nextLine();
                    department.getByDept_Description(description);
                    break;

                case 5:

                    System.out.print("Enter store ID: ");
                    int storeID = scanner.nextInt();
                    department.getByStore_Id(storeID);
                    break;

                case 6:

                    System.out.println("Exiting program. Goodbye!");
                    state = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    public void makeNewDepartment() {
        try {
         
            Scanner scan = new Scanner(System.in);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "INSERT INTO department (deptName, deptDescription, storeID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(select);

            System.out.println("Enter the department name: ");  
            String deptName = scan.nextLine();
            pstmt.setString(1, deptName);

            System.out.println("Enter the department description: ");
            String deptDescription = scan.nextLine();
            pstmt.setString(2, deptDescription);

            System.out.println("Enter the store ID: ");
            int storeID = scan.nextInt();
            pstmt.setInt(3, storeID);
            
            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    public void getByDeptID(int departmentID) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM department WHERE deptID = ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, departmentID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Department ID: " + rs.getInt(1) + " Department Name: " + rs.getString(2) + " Department Description: " + rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    public void getByDeptName(String departmentName) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "SELECT * FROM department WHERE deptname like ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, "%" + departmentName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Department ID: " + rs.getInt(1) + " Department Name: " + rs.getString(2) + " Department Description: " + rs.getString(3));
            }
        } catch (ClassNotFoundException | SQLException e) { }
    }

    public void getByDept_Description(String description) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM department WHERE deptdescription like ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, "%" + description + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Department ID: " + rs.getInt(1) + " Department Name: " + rs.getString(2) + " Department Description: " + rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }

    public void getByStore_Id(int storeID) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            String select = "Select * FROM department WHERE storeID = ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, storeID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("Department ID: " + rs.getInt(1) + " Department Name: " + rs.getString(2) + " Department Description: " + rs.getString(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }

    }

}

