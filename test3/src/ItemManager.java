import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.io.Serializable; // For save and load from file
import java.io.*;
import java.util.ArrayList;

// Item class representing an item with basic properties
class Item implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    private int id;
    private String name;
    private String description;
    private String category;

    // Constructor to initialize an Item
    public Item(int id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Setters for Item properties
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }

    // Getters for Item properties
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }

    // String representation of an Item
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Category: " + category;
    }
}

// Node class for linked list implementation
class Node {
    Item data;
    Node next;

    // Constructor to create a node with given item
    public Node(Item data) {
        this.data = data;
        this.next = null;
    }
}

// Custom linked list implementation for managing items
class LinkedListCustom {
    Node head; // Head of the linked list

    // Constructor initializes empty list
    public LinkedListCustom() { head = null; }

    // Add an item to the end of the list
    public void add(Item item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            return;
        }
        // Traverse to the end of the list
        Node current = head;
        while (current.next != null) current = current.next;
        current.next = newNode;
    }

    // Delete an item by ID
    public boolean delete(int id) {
        if (head == null) return false; // Empty list

        // If head is the node to delete
        if (head.data.getId() == id) {
            head = head.next;
            return true;
        }

        // Search for the node to delete
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getId() == id) {
                current.next = current.next.next; // Bypass the node to delete
                return true;
            }
            current = current.next;
        }
        return false; // Not found
    }

    // Find an item by ID
    public Item find(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.getId() == id) return current.data;
            current = current.next;
        }
        return null; // Not found
    }

    // Update an item's properties
    public boolean update(int id, String name, String desc, String category) {
        Item item = find(id);
        if (item == null) return false;
        item.setName(name);
        item.setDescription(desc);
        item.setCategory(category);
        return true;
    }

    // Display all items in the list
    public void display() {
        if (head == null) {
            System.out.println("No items.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}

// Node class for Binary Search Tree (BST)
class BSTNode {
    Item data; // The item stored in this node
    BSTNode left, right; // Left and right children

    // Constructor to create a BST node
    public BSTNode(Item data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

// Binary Search Tree implementation for efficient searching
class BST {
    private BSTNode root; // Root of the BST

    // Constructor initializes empty tree
    public BST() { root = null; }

    // Public method to insert an item
    public void insert(Item item) {
        root = insertRec(root, item);
    }

    // Recursive helper for insertion
    private BSTNode insertRec(BSTNode root, Item item) {
        if (root == null) return new BSTNode(item); // Found insertion point

        // Insert in left or right subtree based on ID comparison
        if (item.getId() < root.data.getId()) root.left = insertRec(root.left, item);
        else if (item.getId() > root.data.getId()) root.right = insertRec(root.right, item);
        else System.out.println("Duplicate ID, not inserted."); // Duplicate found

        return root;
    }

    // Public method to search for an item by ID
    public Item search(int id) { return searchRec(root, id); }

    // Recursive helper for searching
    private Item searchRec(BSTNode root, int id) {
        if (root == null) return null; // Not found
        if (id == root.data.getId()) return root.data; // Found
        if (id < root.data.getId()) return searchRec(root.left, id); // Search left
        else return searchRec(root.right, id); // Search right
    }

    // Public method to delete an item by ID
    public void delete(int id) { root = deleteRec(root, id); }

    // Recursive helper for deletion
    private BSTNode deleteRec(BSTNode root, int id) {
        if (root == null) return null; // Not found

        // Search for the node to delete
        if (id < root.data.getId()) root.left = deleteRec(root.left, id);
        else if (id > root.data.getId()) root.right = deleteRec(root.right, id);
        else { // Node to delete found
            // Node with only one child or no child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Node with two children: get inorder successor (smallest in right subtree)
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getId());
        }
        return root;
    }

    // Helper to find minimum value in a subtree
    private Item minValue(BSTNode root) {
        Item minv = root.data;
        while (root.left != null) {
            root = root.left;
            minv = root.data;
        }
        return minv;
    }

    // Public method for inorder traversal (sorted order)
    public void inorder() { inorderRec(root); }

    // Recursive helper for inorder traversal
    private void inorderRec(BSTNode root) {
        if (root != null) {
            inorderRec(root.left); // Visit left subtree
            System.out.println(root.data); // Visit node
            inorderRec(root.right); // Visit right subtree
        }
    }
}

// Main class for managing items with multiple data structures
public class ItemManager {
    private LinkedListCustom itemList; // Main storage using linked list
    private Stack<Item> deletedStack; // Stack for undo functionality
    private Queue<Item> urgentQueue; // Queue for high priority items
    private Queue<Item> normalQueue; // Queue for normal priority items
    private BST bst; // BST for efficient searching

    // Constructor initializes all data structures
    public ItemManager() {
        itemList = new LinkedListCustom();
        deletedStack = new Stack<>();
        urgentQueue = new LinkedList<>();
        normalQueue = new LinkedList<>();
        bst = new BST();
    }

    // Add a new item to all data structures
    public boolean addItem(Item item) {
        if (itemList.find(item.getId()) != null) {
            System.out.println("ID must be unique.");
            return false;
        }
        itemList.add(item);
        bst.insert(item);
        System.out.println("Item added.");
        return true;
    }

    // View all items
    public void viewItems() {
        itemList.display();
    }

    // Update an existing item
    public boolean updateItem(int id, String name, String desc, String category) {
        boolean updated = itemList.update(id, name, desc, category);
        if (updated) {
            // Update BST by removing and reinserting
            bst.delete(id);
            bst.insert(itemList.find(id));
            System.out.println("Item updated.");
        } else {
            System.out.println("Item not found.");
        }
        return updated;
    }

    // Delete an item
    public boolean deleteItem(int id) {
        Item item = itemList.find(id);
        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }
        boolean deleted = itemList.delete(id);
        if (deleted) {
            bst.delete(id);
            deletedStack.push(item); // Save for possible undo
            System.out.println("Item deleted. You can undo.");
            return true;
        }
        return false;
    }

    // Undo the last deletion
    public boolean undoDelete() {
        if (deletedStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }
        Item item = deletedStack.pop();
        itemList.add(item);
        bst.insert(item);
        System.out.println("Undo done. Item restored.");
        return true;
    }

    // Add item to priority queue
    public void enqueueByPriority(int id, String priority) {
        Item item = itemList.find(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (priority.equalsIgnoreCase("urgent")) urgentQueue.add(item);
        else if (priority.equalsIgnoreCase("normal")) normalQueue.add(item);
        else System.out.println("Invalid priority.");
    }

    // Remove item from priority queues (urgent first)
    public void dequeueByPriority() {
        Item item;
        if (!urgentQueue.isEmpty()) item = urgentQueue.poll();
        else if (!normalQueue.isEmpty()) item = normalQueue.poll();
        else {
            System.out.println("No items in priority queues.");
            return;
        }
        System.out.println("Dequeued item: " + item);
    }

    // View items in both priority queues
    public void viewPriorityQueues() {
        System.out.println("Urgent Queue:");
        if (urgentQueue.isEmpty()) System.out.println("Empty");
        else urgentQueue.forEach(System.out::println);

        System.out.println("Normal Queue:");
        if (normalQueue.isEmpty()) System.out.println("Empty");
        else normalQueue.forEach(System.out::println);
    }

    // Search for item in BST
    public void searchBST(int id) {
        Item found = bst.search(id);
        if (found != null) System.out.println("Found: " + found);
        else System.out.println("Not found.");
    }

    // View BST in sorted order
    public void viewBST() {
        System.out.println("BST Inorder traversal:");
        bst.inorder();
    }

    // Search by name or category keyword
    public void searchByNameOrCategory(String keyword) {
        Node current = itemList.head;
        boolean found = false;
        while (current != null) {
            Item item = current.data;
            if (item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(item);
                found = true;
            }
            current = current.next;
        }
        if (!found) System.out.println("No items match the search.");
    }

    // Save items to file
    public void saveToFile(String filename) {
        ArrayList<Item> items = new ArrayList<>();
        Node current = itemList.head;
        while (current != null) {
            items.add(current.data);
            current = current.next;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(items);
            System.out.println("Items saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // Load items from file
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Item> items = (ArrayList<Item>) ois.readObject();

            // Reinitialize data structures
            itemList = new LinkedListCustom();
            bst = new BST();

            // Add all loaded items
            for (Item item : items) {
                itemList.add(item);
                bst.insert(item);
            }
            System.out.println("Items loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}