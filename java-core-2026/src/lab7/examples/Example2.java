package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class Example2 {
    public static void writeBytes(File file, String data) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static String readBytes(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int readBytes;

            while ((readBytes = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readBytes);
            }

            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String data = InputUtils.readLineOrDefault(scanner, "Enter text for byte stream example", "Byte stream data");
        Path filePath = Lab7Files.demoDirectory("example2").resolve("example_file.txt");
        File file = filePath.toFile();

        writeBytes(file, data);
        System.out.println("Data written to file: " + file.getAbsolutePath());
        System.out.println("Data read from file: " + readBytes(file));

        if (file.delete()) {
            System.out.println("File deleted: " + file.getName());
        }
    }
}
