package lab8.examples;

import lab8.common.Book;
import lab8.common.JsonBookLibrary;
import lab8.common.Lab8Data;
import lab8.common.Lab8Files;

import java.nio.file.Path;

public class Example4JsonRead {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example4-json-read").resolve("library.json");
        JsonBookLibrary.write(file, Lab8Data.sampleBooks());

        for (Book book : JsonBookLibrary.read(file)) {
            System.out.println(book);
        }
    }
}
