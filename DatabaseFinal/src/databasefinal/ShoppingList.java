/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasefinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author luke
 */
public class ShoppingList {

    private int acc;
    private int quantity;
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Assignment8";
    static String username = "postgres";
    static String password = "bluejays123";

    public int getAccount() {
        return acc;
    }

    public void setAccount() {

        Scanner scan = new Scanner(System.in);
        boolean shopping = true;
        boolean choosing = true;
        ArrayList<Integer> accounts = new ArrayList<>();

        while (shopping) {
            try {

                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                Statement stmt = connection.createStatement();
                String select = "Select accountid FROM account;";
                ResultSet rs = stmt.executeQuery(select);

                while (rs.next()) {
                    accounts.add(rs.getInt(1));
                }

                System.out.println("Enter your Account ID:");
                while (choosing) {
                    int choice = scan.nextInt();
                    if (accounts.contains(choice)) {
                        choosing = false;
                        shopping = false;
                        this.acc = choice;
                    } else {
                        System.out.println("Enter a valid value.");
                    }
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load driver.");
            } catch (SQLException e) {
            }
        }
    }

    public void chooseStore() {
        Scanner scan = new Scanner(System.in);
        boolean shopping = true;
        boolean choosing = true;
        while (shopping) {
            try {
                System.out.println("Choose a store:");
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                Statement stmt = connection.createStatement();
                String select = "Select storename FROM store;";
                ResultSet rs = stmt.executeQuery(select);
                int count = 1;
                
                while (rs.next()) {
                    System.out.println(count + ". " + rs.getString(1));
                    count++;
                }
                
                while (choosing) {
                    int choice = Integer.parseInt(scan.nextLine());
                    if (choice < count && choice > 0) {
                        choosing = false;
                        chooseDepartment(choice);
                    } else {
                        System.out.println("Enter a valid value.");
                    }
                }
                shopping = false;
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load driver.");
            } catch (SQLException e) {
            }
        }
    }

    public void chooseDepartment(int id) {

        Scanner scan = new Scanner(System.in);
        boolean shopping = true;
        boolean choosing = true;
        ArrayList<Integer> depts = new ArrayList<>();
        int de;
        
        while (shopping) {
            try {
                System.out.println("Choose a Department:");
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                String select = "Select deptid, deptname FROM department where storeid = ?;";
                PreparedStatement pstmt = connection.prepareStatement(select);
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    de = rs.getInt(1);
                    depts.add(de);
                    System.out.println(de + ". " + rs.getString(2));
                }
                while (choosing) {
                    int choice = Integer.parseInt(scan.nextLine());
                    if(depts.contains(choice)) {
                        choosing = false;
                        shopping = false;
                        listItems(choice);
                    }
                }
                shopping = false;
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load driver.");
            } catch (SQLException e) {

            }
        }
    }

    public void listItems(int dept) {
        boolean quan = true;
        Scanner scan = new Scanner(System.in);
        boolean shopping = true;
        boolean choosing = true;
        ArrayList<Integer> arr = new ArrayList<>();

        while (shopping) {
            try {
                System.out.println("Choose an Item:");
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                String select = "Select itemid, itemname FROM item where deptid = ? order by itemid;";
                PreparedStatement pstmt = connection.prepareStatement(select);
                pstmt.setInt(1, dept);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    int num = rs.getInt(1);
                    System.out.println(num + ". " + rs.getString(2));
                    arr.add(num);
                }
                
                int max = arr.get(arr.size()-1);
                int min = arr.get(0);
                
                while (choosing) {
                    int choice = Integer.parseInt(scan.nextLine());
                    if (choice <= max && choice >= min) {
                        choosing = false;
                        while (quan) {
                        System.out.println("Enter a quantity.");
                        quantity = Integer.parseInt(scan.nextLine());
                        if (quantity > 0) {
                            addToShoppingList(choice, acc, quantity);
                            quan = false;
                            shopping = false;
                        } else {
                            System.out.println("Enter a positive integer.");
                        }
                        }
                    } else {
                        System.out.println("Enter a valid value.");
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load driver.");
                shopping = false;
            } catch (SQLException e) {
            }
        }
    }

    public void addToShoppingList(int id, int acc, int quantity) {
        try {
            Scanner scan = new Scanner(System.in);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            String select = "INSERT INTO shoppinglist (itemid, accountid, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, id);
            pstmt.setInt(2, acc);
            pstmt.setInt(3, quantity);
            
            pstmt.executeUpdate();
            
            showShoppingList(acc);
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) {
        }
    }
    
    public void showShoppingList(int id) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String select = "Select item.itemname, shoppinglist.quantity, item.itemcost FROM item join shoppinglist on item.itemid = shoppinglist.itemid where shoppinglist.accountid = ?;";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("Shopping List for Account ID: " + id);
            while(rs.next()) {
                System.out.println(rs.getString(1) + " - Quantity: " + rs.getInt(2) + " - Cost: $" + rs.getDouble(3));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) {
            
        }
    }
    
    public void clearShoppingList(int id) {
        try {
            
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String select = "DELETE FROM shoppinglist WHERE accountid = ?";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
            
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load driver.");
        } catch (SQLException e) { }
    }
    
}
