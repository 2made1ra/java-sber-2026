package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task6 {
    public static List<String> findLinesWithWord(Path file, String word) throws IOException {
        List<String> foundLines = new ArrayList<String>();

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
                    foundLines.add(line);
                }
            }
        }

        return foundLines;
    }

    public static void main(String[] args) throws IOException {
        Path defaultFile = Lab7Files.demoFile(
                "task6",
                "text.txt",
                "Java streams\nFile input and output\nJava serialization\n");
        Scanner scanner = new Scanner(System.in);

        String fileName = InputUtils.readLineOrDefault(scanner, "Enter file path", defaultFile.toString());
        String word = InputUtils.readLineOrDefault(scanner, "Enter word to search", "Java");

        List<String> foundLines = findLinesWithWord(Paths.get(fileName), word);
        if (foundLines.isEmpty()) {
            System.out.println("No lines found");
            return;
        }

        System.out.println("Found lines:");
        for (String line : foundLines) {
            System.out.println(line);
        }
    }
}
