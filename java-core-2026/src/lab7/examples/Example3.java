package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Example3 {
    public static void writeText(File file, String data) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        }
    }

    public static String readText(File file) throws IOException {
        StringBuilder result = new StringBuilder();

        try (FileReader reader = new FileReader(file)) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                result.append((char) symbol);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String data = InputUtils.readLineOrDefault(scanner, "Enter text for FileWriter example", "Character stream data");
        Path filePath = Lab7Files.demoDirectory("example3").resolve("example_file.txt");
        File file = filePath.toFile();

        writeText(file, data);
        System.out.println("Data written to file: " + file.getAbsolutePath());
        System.out.println("Data read from file: " + readText(file));

        if (file.delete()) {
            System.out.println("File deleted: " + file.getName());
        }
    }
}
