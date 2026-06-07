package lab8.examples;

import lab8.common.Book;
import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.XmlBookLibrary;

import java.nio.file.Path;

public class Example2XmlRead {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example2-xml-read").resolve("library.xml");
        XmlBookLibrary.write(file, Lab8Data.sampleBooks());

        System.out.println("Root element: library");
        for (Book book : XmlBookLibrary.read(file)) {
            System.out.println(book);
        }
    }
}
