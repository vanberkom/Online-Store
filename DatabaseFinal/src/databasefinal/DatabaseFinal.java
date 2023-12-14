
package databasefinal;

import java.util.Scanner;

/**
 *
 * @author luke
 */
public class DatabaseFinal {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean running = true;
        Scanner scan = new Scanner(System.in);
        while (running) {
            System.out.println("Welcome to our Online Store!");
            System.out.println("1. Shop\n2. Show Shopping List\n3. Clear Shopping List\n4. List Account Information\n5. List Store Information\n6. List Department Information\n7. List Item Information\n8. Quit Program");
            try {
                int choice = Integer.parseInt(scan.nextLine());
                switch (choice) {
                    case 1:
                        ShoppingList shop = new ShoppingList();
                        shop.setAccount();
                        shop.chooseStore();
                    break;
                    case 2:
                        ShoppingList list = new ShoppingList();
                        list.setAccount();
                        list.showShoppingList(list.getAccount());
                        System.out.println("");
                    break;
                    case 3:
                        ShoppingList shopDelete = new ShoppingList();
                        shopDelete.setAccount();
                        shopDelete.clearShoppingList(shopDelete.getAccount());
                    break;
                    case 4:
                        Account.Menu();
                    break;
                    case 5:
                        Store.menu();
                    break;
                    case 6:
                        Department dep = new Department();
                        dep.menu();
                    break;
                    case 7:
                        Item it = new Item();
                        it.menu();
                    break;
                    case 8:
                        running = false;
                }
            } catch (NumberFormatException e) { }
        }
    }
    
}
