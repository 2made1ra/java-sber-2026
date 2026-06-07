package lab8.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Lab8Data {
    private Lab8Data() {
    }

    public static List<Book> sampleBooks() {
        return new ArrayList<Book>(Arrays.asList(
                new Book("War and Peace", "Leo Tolstoy", 1869),
                new Book("The Master and Margarita", "Mikhail Bulgakov", 1967),
                new Book("Java Programming", "John Doe", 2015),
                new Book("Python Programming", "Jane Smith", 2018)
        ));
    }

    public static List<List<String>> sampleProducts() {
        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(Arrays.asList("Product", "Description", "Price"));
        rows.add(Arrays.asList("Book", "Java textbook", "1500"));
        rows.add(Arrays.asList("Laptop", "16 GB RAM, 512 GB SSD", "85000"));
        rows.add(Arrays.asList("Monitor", "27 inch IPS", "24000"));
        return rows;
    }
}
