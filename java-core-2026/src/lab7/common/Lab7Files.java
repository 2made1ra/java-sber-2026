package lab7.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Lab7Files {
    private static final String ROOT_DIRECTORY = "java-core-2026-lab7";

    private Lab7Files() {
    }

    public static Path demoDirectory(String name) throws IOException {
        Path directory = Paths.get(System.getProperty("java.io.tmpdir"), ROOT_DIRECTORY, name);
        Files.createDirectories(directory);
        return directory;
    }

    public static Path demoFile(String directoryName, String fileName, String content) throws IOException {
        Path file = demoDirectory(directoryName).resolve(fileName);
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
        return file;
    }

    public static void createParentDirectories(Path file) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
