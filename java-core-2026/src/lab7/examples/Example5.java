package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Example5 {
    public static void writeUtf8(Path file, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file.toFile()), StandardCharsets.UTF_8))) {
            writer.write(data);
        }
    }

    public static void convertToUpperCase(Path inputFile, Path outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile.toFile()), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputFile.toFile()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase(Locale.ROOT));
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String data = InputUtils.readLineOrDefault(scanner, "Enter text for UTF-8 conversion example", "Mixed case data");
        Path directory = Lab7Files.demoDirectory("example5");
        Path inputFile = directory.resolve("input.txt");
        Path outputFile = directory.resolve("output.txt");

        writeUtf8(inputFile, data);
        convertToUpperCase(inputFile, outputFile);

        System.out.println("Input file: " + inputFile.toAbsolutePath());
        System.out.println("Output file: " + outputFile.toAbsolutePath());
        System.out.println("Converted data: " + new String(Files.readAllBytes(outputFile), StandardCharsets.UTF_8).trim());

        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(outputFile);
    }
}
