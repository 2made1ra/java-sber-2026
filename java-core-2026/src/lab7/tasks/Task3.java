package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task3 {
    public static long countLines(Path file) throws IOException {
        long count = 0;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            while (reader.readLine() != null) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        Path defaultFile = Lab7Files.demoFile("task3", "text.txt", "First line\nSecond line\nThird line\n");
        Scanner scanner = new Scanner(System.in);
        String fileName = InputUtils.readLineOrDefault(scanner, "Enter file path", defaultFile.toString());
        Path file = Paths.get(fileName);

        System.out.println("Line count: " + countLines(file));
    }
}
