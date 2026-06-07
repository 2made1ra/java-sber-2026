package lab8.common;

import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final int year;

    public Book(String title, String author, int year) {
        this.title = requireText(title, "title");
        this.author = requireText(author, "author");
        this.year = year;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty");
        }
        return value.trim();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', year=" + year + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Book)) {
            return false;
        }
        Book book = (Book) object;
        return year == book.year
                && title.equals(book.title)
                && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}
