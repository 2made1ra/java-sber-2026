package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task7 {
    public static int writeText(Path file, String text) throws IOException {
        Lab7Files.createParentDirectories(file);

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(text);
        }

        return text.length();
    }

    public static void main(String[] args) throws IOException {
        Path defaultFile = Lab7Files.demoDirectory("task7").resolve("output.txt");
        Scanner scanner = new Scanner(System.in);

        String fileName = InputUtils.readLineOrDefault(scanner, "Enter file path", defaultFile.toString());
        String text = InputUtils.readLineOrDefault(scanner, "Enter text to write", "Text for writing to file");

        int writtenCharacters = writeText(Paths.get(fileName), text);
        System.out.println("Written characters: " + writtenCharacters);
    }
}
