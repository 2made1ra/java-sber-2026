package lab8.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Lab8Files {
    private static final String ROOT_DIRECTORY = "java-core-2026-lab8";

    private Lab8Files() {
    }

    public static Path demoDirectory(String name) throws IOException {
        Path directory = Paths.get(System.getProperty("java.io.tmpdir"), ROOT_DIRECTORY, name);
        Files.createDirectories(directory);
        return directory;
    }

    public static void createParentDirectories(Path file) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
