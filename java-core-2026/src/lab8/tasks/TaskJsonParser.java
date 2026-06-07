package lab8.tasks;

import lab8.common.Book;
import lab8.common.JsonBookLibrary;
import lab8.common.Lab8Data;
import lab8.common.Lab8Files;

import java.nio.file.Path;

public class TaskJsonParser {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("task-json-parser").resolve("library.json");
        JsonBookLibrary.write(file, Lab8Data.sampleBooks());

        Book newBook = new Book("Clean Code", "Robert Martin", 2008);
        JsonBookLibrary.addBook(file, newBook);

        System.out.println("Books by Robert Martin:");
        for (Book book : JsonBookLibrary.findByAuthor(file, "Robert Martin")) {
            System.out.println(book);
        }

        boolean removed = JsonBookLibrary.removeByTitle(file, "Python Programming");
        System.out.println("Python Programming removed: " + removed);
        System.out.println("Final JSON library:");
        for (Book book : JsonBookLibrary.read(file)) {
            System.out.println(book);
        }
    }
}
