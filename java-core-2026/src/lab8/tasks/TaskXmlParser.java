package lab8.tasks;

import lab8.common.Book;
import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.XmlBookLibrary;

import java.nio.file.Path;

public class TaskXmlParser {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("task-xml-parser").resolve("library.xml");
        XmlBookLibrary.write(file, Lab8Data.sampleBooks());

        Book newBook = new Book("Effective Java", "Joshua Bloch", 2018);
        XmlBookLibrary.addBook(file, newBook);

        System.out.println("Books by Joshua Bloch:");
        for (Book book : XmlBookLibrary.findByAuthor(file, "Joshua Bloch")) {
            System.out.println(book);
        }

        System.out.println("Books from 2018:");
        for (Book book : XmlBookLibrary.findByYear(file, 2018)) {
            System.out.println(book);
        }

        boolean removed = XmlBookLibrary.removeByTitle(file, "War and Peace");
        System.out.println("War and Peace removed: " + removed);
        System.out.println("Final XML library:");
        for (Book book : XmlBookLibrary.read(file)) {
            System.out.println(book);
        }
    }
}
