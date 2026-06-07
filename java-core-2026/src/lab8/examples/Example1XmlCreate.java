package lab8.examples;

import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.XmlBookLibrary;

import java.nio.file.Path;

public class Example1XmlCreate {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example1-xml-create").resolve("library.xml");
        XmlBookLibrary.write(file, Lab8Data.sampleBooks());
        System.out.println("XML file created: " + file.toAbsolutePath());
    }
}
