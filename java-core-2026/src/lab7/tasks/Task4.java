package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task4 {
    public static long copyTextFile(Path source, Path target) throws IOException {
        Lab7Files.createParentDirectories(target);
        long copiedCharacters = 0;

        try (BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int readCharacters;

            while ((readCharacters = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, readCharacters);
                copiedCharacters += readCharacters;
            }
        }

        return copiedCharacters;
    }

    public static void main(String[] args) throws IOException {
        Path sourceDefault = Lab7Files.demoFile("task4", "source.txt", "Text for copying\nSecond line\n");
        Path targetDefault = Lab7Files.demoDirectory("task4").resolve("target.txt");
        Scanner scanner = new Scanner(System.in);

        String sourceName = InputUtils.readLineOrDefault(scanner, "Enter source file path", sourceDefault.toString());
        String targetName = InputUtils.readLineOrDefault(scanner, "Enter target file path", targetDefault.toString());

        long copiedCharacters = copyTextFile(Paths.get(sourceName), Paths.get(targetName));
        System.out.println("Copied characters: " + copiedCharacters);
        System.out.println("Target file: " + Paths.get(targetName).toAbsolutePath());
    }
}
