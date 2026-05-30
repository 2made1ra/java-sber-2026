package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task5 {
    public static long getFileSize(Path file) throws IOException {
        return Files.size(file);
    }

    public static void main(String[] args) throws IOException {
        Path defaultFile = Lab7Files.demoFile("task5", "data.txt", "File size example");
        Scanner scanner = new Scanner(System.in);
        String fileName = InputUtils.readLineOrDefault(scanner, "Enter file path", defaultFile.toString());

        System.out.println("File size in bytes: " + getFileSize(Paths.get(fileName)));
    }
}
