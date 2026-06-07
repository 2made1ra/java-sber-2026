package lab8.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class JsonBookLibrary {
    private static final Pattern BOOKS_ARRAY_PATTERN = Pattern.compile("\"books\"\\s*:\\s*\\[(.*)]", Pattern.DOTALL);
    private static final Pattern BOOK_PATTERN = Pattern.compile("\\{\\s*(.*?)\\s*}", Pattern.DOTALL);
    private static final Pattern FIELD_PATTERN = Pattern.compile(
            "\"(title|author|year)\"\\s*:\\s*(\"(?:\\\\.|[^\"])*\"|-?\\d+)", Pattern.DOTALL);

    private JsonBookLibrary() {
    }

    public static void write(Path file, List<Book> books) throws IOException {
        Lab8Files.createParentDirectories(file);
        Files.write(file, toJson(books).getBytes(StandardCharsets.UTF_8));
    }

    public static List<Book> read(Path file) throws IOException {
        if (!Files.exists(file)) {
            return new ArrayList<Book>();
        }

        String json = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        Matcher arrayMatcher = BOOKS_ARRAY_PATTERN.matcher(json);
        String source = arrayMatcher.find() ? arrayMatcher.group(1) : json;
        Matcher bookMatcher = BOOK_PATTERN.matcher(source);
        List<Book> books = new ArrayList<Book>();

        while (bookMatcher.find()) {
            String objectBody = bookMatcher.group(1);
            String title = null;
            String author = null;
            Integer year = null;
            Matcher fieldMatcher = FIELD_PATTERN.matcher(objectBody);

            while (fieldMatcher.find()) {
                String name = fieldMatcher.group(1);
                String value = unquote(fieldMatcher.group(2));

                if ("title".equals(name)) {
                    title = value;
                } else if ("author".equals(name)) {
                    author = value;
                } else if ("year".equals(name)) {
                    year = Integer.parseInt(value);
                }
            }

            if (title != null && author != null && year != null) {
                books.add(new Book(title, author, year));
            }
        }

        return books;
    }

    public static void addBook(Path file, Book book) throws IOException {
        List<Book> books = read(file);
        books.add(book);
        write(file, books);
    }

    public static List<Book> findByAuthor(Path file, String author) throws IOException {
        return read(file).stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public static boolean removeByTitle(Path file, String title) throws IOException {
        List<Book> books = read(file);
        Iterator<Book> iterator = books.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            if (iterator.next().getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                removed = true;
            }
        }

        if (removed) {
            write(file, books);
        }

        return removed;
    }

    private static String toJson(List<Book> books) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"books\": [\n");

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            builder.append("    {\n");
            builder.append("      \"title\": \"").append(escape(book.getTitle())).append("\",\n");
            builder.append("      \"author\": \"").append(escape(book.getAuthor())).append("\",\n");
            builder.append("      \"year\": ").append(book.getYear()).append("\n");
            builder.append("    }");

            if (i < books.size() - 1) {
                builder.append(',');
            }
            builder.append('\n');
        }

        builder.append("  ]\n");
        builder.append("}\n");
        return builder.toString();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unquote(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
        }

        return value;
    }
}
