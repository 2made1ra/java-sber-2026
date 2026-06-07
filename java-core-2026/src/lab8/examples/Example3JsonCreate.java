package lab8.examples;

import lab8.common.JsonBookLibrary;
import lab8.common.Lab8Data;
import lab8.common.Lab8Files;

import java.nio.file.Path;

public class Example3JsonCreate {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example3-json-create").resolve("library.json");
        JsonBookLibrary.write(file, Lab8Data.sampleBooks());
        System.out.println("JSON file created: " + file.toAbsolutePath());
    }
}
