import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ItemManager manager = new ItemManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Undo Delete");
            System.out.println("6. Add to Priority Queue");
            System.out.println("7. Remove from Priority Queue");
            System.out.println("8. View Priority Queues");
            System.out.println("9. Search Item ");
            System.out.println("10. View BST");
            System.out.println("11. Search by Name/Category");
            System.out.println("12. Save Items to File");
            System.out.println("13. Load Items from File");
            System.out.println("14. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1 -> { // Add Item
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    Item item = new Item(id, name, desc, category);
                    manager.addItem(item);
                }
                case 2 -> manager.viewItems(); // View all items
                case 3 -> { // Update Item
                    System.out.print("ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("New Name: ");
                    String name = scanner.nextLine();

                    System.out.print("New Description: ");
                    String desc = scanner.nextLine();

                    System.out.print("New Category: ");
                    String category = scanner.nextLine();

                    manager.updateItem(id, name, desc, category);
                }
                case 4 -> { // Delete Item
                    System.out.print("ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    manager.deleteItem(id);
                }
                case 5 -> manager.undoDelete(); // Undo delete
                case 6 -> { // Add to priority queue
                    System.out.print("ID to enqueue: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Priority (urgent/normal): ");
                    String priority = scanner.nextLine();

                    manager.enqueueByPriority(id, priority);
                }
                case 7 -> manager.dequeueByPriority(); // Remove from queue
                case 8 -> manager.viewPriorityQueues(); // View queues
                case 9 -> { // Search in BST
                    System.out.print("ID to search in BST: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    manager.searchBST(id);
                }
                case 10 -> manager.viewBST(); // View BST
                case 11 -> { // Search by keyword
                    System.out.print("Enter keyword (name or category): ");
                    String keyword = scanner.nextLine();
                    manager.searchByNameOrCategory(keyword);
                }
                case 12 -> { // Save to file
                    System.out.print("Enter filename to save: ");
                    String filename = scanner.nextLine();
                    manager.saveToFile(filename);
                }
                case 13 -> { // Load from file
                    System.out.print("Enter filename to load: ");
                    String filename = scanner.nextLine();
                    manager.loadFromFile(filename);
                }
                case 14 -> { // Exit program
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
 }
}
}


