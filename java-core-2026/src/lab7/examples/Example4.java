package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Example4 {
    public static void writeBuffered(File file, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        }
    }

    public static String readBuffered(File file) throws IOException {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (result.length() > 0) {
                    result.append(System.lineSeparator());
                }
                result.append(line);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String data = InputUtils.readLineOrDefault(scanner, "Enter text for buffered stream example", "Buffered stream data");
        Path filePath = Lab7Files.demoDirectory("example4").resolve("example_file.txt");
        File file = filePath.toFile();

        writeBuffered(file, data);
        System.out.println("Data written to file: " + file.getAbsolutePath());
        System.out.println("Data read from file: " + readBuffered(file));

        if (file.delete()) {
            System.out.println("File deleted: " + file.getName());
        }
    }
}
