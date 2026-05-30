package lab7.examples;

import lab7.common.Lab7Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Example1 {
    public static void main(String[] args) throws IOException {
        Path directory = Lab7Files.demoDirectory("example1").resolve("example_folder");
        File folder = directory.toFile();
        File file = new File(folder, "example_file.txt");

        if (!folder.exists() && folder.mkdirs()) {
            System.out.println("Folder created: " + folder.getAbsolutePath());
        }

        if (!file.exists() && file.createNewFile()) {
            System.out.println("File created: " + file.getAbsolutePath());
        }

        if (file.exists() && file.delete()) {
            System.out.println("File deleted: " + file.getName());
        }

        if (folder.exists() && folder.delete()) {
            System.out.println("Folder deleted: " + folder.getName());
        }
    }
}
