import java.util.*;

/**
 * LibraryApp — Simple Java console program to practice Data Structures & Algorithms.
 * Author: Bryan Gomez Esparza
 * Date: Fall 2024
 */
public class LibraryApp {

    // Class representing a Book with basic info
    static class Book {
        int id;
        String title;
        String author;
        int year;

        // Constructor to initialize book details
        Book(int id, String title, String author, int year) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
        }

        // Returns a readable string for the book
        @Override
        public String toString() {
            return String.format("[%d] %s — %s (%d)", id, title, author, year);
        }
    }

    // Binary Search Tree (BST) for organizing books by title
    static class BST {
        static class Node {
            Book book;
            Node left, right;
            Node(Book b) { this.book = b; } // Each node stores one book
        }

        private Node root;

        // Public method to insert a new book
        public void insert(Book b) { root = insertRec(root, b); }

        // Recursive helper method for insertion
        private Node insertRec(Node cur, Book b) {
            if (cur == null) return new Node(b);
            if (b.title.compareToIgnoreCase(cur.book.title) < 0)
                cur.left = insertRec(cur.left, b);
            else cur.right = insertRec(cur.right, b);
            return cur;
        }

        // Inorder traversal to get books in sorted order by title
        public void inorder(List<Book> out) { inorderRec(root, out); }

        // Recursive helper for inorder traversal
        private void inorderRec(Node n, List<Book> out) {
            if (n == null) return;
            inorderRec(n.left, out);
            out.add(n.book);
            inorderRec(n.right, out);
        }
    }

    // Class containing sorting and searching algorithms
    static class Algorithms {
        // Merge Sort algorithm for sorting a list of strings
        public static List<String> mergeSort(List<String> data) {
            if (data.size() <= 1) return new ArrayList<>(data);
            int mid = data.size() / 2;
            List<String> left = mergeSort(data.subList(0, mid));
            List<String> right = mergeSort(data.subList(mid, data.size()));
            return merge(left, right);
        }

        // Helper method that merges two sorted lists
        private static List<String> merge(List<String> a, List<String> b) {
            List<String> out = new ArrayList<>();
            int i = 0, j = 0;
            while (i < a.size() && j < b.size()) {
                if (a.get(i).compareToIgnoreCase(b.get(j)) <= 0) out.add(a.get(i++));
                else out.add(b.get(j++));
            }
            while (i < a.size()) out.add(a.get(i++));
            while (j < b.size()) out.add(b.get(j++));
            return out;
        }

        // Binary Search algorithm to find a title in a sorted list
        public static int binarySearch(List<String> sorted, String key) {
            int lo = 0, hi = sorted.size() - 1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                int cmp = sorted.get(mid).compareToIgnoreCase(key);
                if (cmp == 0) return mid;       // Found
                if (cmp < 0) lo = mid + 1; else hi = mid - 1;  // Search left or right
            }
            return -1; // Not found
        }
    }

    // Manages a collection of books using both list and BST
    static class BookCatalog {
        private final List<Book> books = new ArrayList<>();
        private final BST byTitleBST = new BST();

        // Adds a new book to the catalog
        public void add(Book b) {
            books.add(b);
            byTitleBST.insert(b);
        }

        // Returns books sorted by title using the BST
        public List<Book> listInOrder() {
            List<Book> out = new ArrayList<>();
            byTitleBST.inorder(out);
            return out;
        }

        // Returns book titles sorted alphabetically using merge sort
        public List<String> titlesSorted() {
            List<String> titles = new ArrayList<>();
            for (Book b : books) titles.add(b.title);
            return Algorithms.mergeSort(titles);
        }

        // Searches for a title using binary search
        public int searchTitle(String title) {
            return Algorithms.binarySearch(titlesSorted(), title);
        }
    }

    // Main method to test the app
    public static void main(String[] args) {
        BookCatalog catalog = new BookCatalog();

        // Add sample books
        catalog.add(new Book(1, "Algorithms Unlocked", "Cormen", 2013));
        catalog.add(new Book(2, "Clean Code", "Robert C. Martin", 2008));
        catalog.add(new Book(3, "The Pragmatic Programmer", "Hunt & Thomas", 1999));

        // Display books sorted by title (using BST)
        System.out.println("Books in order by title:");
        for (Book b : catalog.listInOrder()) System.out.println(b);

        // Display sorted titles (using merge sort)
        System.out.println("\nSorted titles:");
        for (String t : catalog.titlesSorted()) System.out.println(t);

        // Search for a specific title
        String key = "Clean Code";
        System.out.println("\nBinary Search for '" + key + "': " +
            (catalog.searchTitle(key) >= 0 ? "Found" : "Not found"));
    }
}