package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Example6 {
    public static void writeWithPrintWriter(Path file, String data) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file.toFile()), StandardCharsets.UTF_8))) {
            writer.println(data);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String data = InputUtils.readLineOrDefault(scanner, "Enter text for PrintWriter example", "PrintWriter data");
        Path file = Lab7Files.demoDirectory("example6").resolve("example_file.txt");

        writeWithPrintWriter(file, data);
        System.out.println("Data written to file: " + file.toAbsolutePath());
        System.out.println("Data read from file: " + new String(Files.readAllBytes(file), StandardCharsets.UTF_8).trim());

        Files.deleteIfExists(file);
    }
}
