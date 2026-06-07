package lab8.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HtmlPageParser {
    private static final Pattern LINK_PATTERN = Pattern.compile(
            "(?is)<a\\s+[^>]*href\\s*=\\s*(['\"]?)([^'\"\\s>]+)\\1[^>]*>");
    private static final Pattern NEWS_BLOCK_PATTERN = Pattern.compile(
            "(?is)<(?:article|li|div)\\b[^>]*class\\s*=\\s*(['\"])[^'\"]*(?:news|item)[^'\"]*\\1[^>]*>(.*?)</(?:article|li|div)>");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\b\\d{1,2}[./-]\\d{1,2}[./-]\\d{2,4}\\b");

    private HtmlPageParser() {
    }

    public static String fetchWithRetries(String url, int attempts, int timeoutMillis) throws IOException {
        IOException lastException = null;

        for (int attempt = 1; attempt <= attempts; attempt++) {
            try {
                return fetch(url, timeoutMillis);
            } catch (IOException exception) {
                lastException = exception;
                System.out.println("Attempt " + attempt + " failed: " + exception.getMessage());
            }
        }

        throw new IOException("Could not load " + url + " after " + attempts + " attempts", lastException);
    }

    public static List<String> parseLinks(String html, String baseUrl) {
        List<String> links = new ArrayList<String>();
        Matcher matcher = LINK_PATTERN.matcher(html);

        while (matcher.find()) {
            String href = decodeHtml(matcher.group(2).trim());
            try {
                links.add(URI.create(baseUrl).resolve(href).toString());
            } catch (IllegalArgumentException exception) {
                links.add(href);
            }
        }

        return links;
    }

    public static List<NewsItem> parseNews(String html) {
        List<NewsItem> news = new ArrayList<NewsItem>();
        Matcher matcher = NEWS_BLOCK_PATTERN.matcher(html);

        while (matcher.find()) {
            String block = matcher.group(2);
            String title = findTitle(block);
            String date = findDate(block);

            if (!title.isEmpty()) {
                news.add(new NewsItem(title, date.isEmpty() ? "date not found" : date));
            }
        }

        return news;
    }

    public static void saveNews(Path file, List<NewsItem> news) throws IOException {
        Lab8Files.createParentDirectories(file);
        List<String> lines = new ArrayList<String>();

        for (NewsItem item : news) {
            lines.add(item.getDate() + " | " + item.getTitle());
        }

        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    public static String sampleLinksPage() {
        return "<html><body>"
                + "<a href=\"/courses/java\">Java course</a>"
                + "<a href=\"https://docs.oracle.com/javase/8/docs/api/\">Java API</a>"
                + "<a href=\"contacts.html\">Contacts</a>"
                + "</body></html>";
    }

    public static String sampleNewsPage() {
        return "<html><body>"
                + "<div class=\"news-item\"><span class=\"date\">03.06.2026</span>"
                + "<a class=\"title\" href=\"/news/1\">Java lab parser updated</a></div>"
                + "<div class=\"news-item\"><span class=\"date\">04.06.2026</span>"
                + "<a class=\"title\" href=\"/news/2\">Student conference announced</a></div>"
                + "</body></html>";
    }

    private static String fetch(String url, int timeoutMillis) throws IOException {
        URLConnection connection = URI.create(url).toURL().openConnection();
        connection.setConnectTimeout(timeoutMillis);
        connection.setReadTimeout(timeoutMillis);

        InputStream inputStream = connection.getInputStream();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        } finally {
            inputStream.close();
        }
    }

    private static String findTitle(String html) {
        String title = findTextByClass(html, "title");
        if (!title.isEmpty()) {
            return title;
        }

        title = findFirstTagText(html, "a");
        if (!title.isEmpty()) {
            return title;
        }

        title = findFirstTagText(html, "h2");
        if (!title.isEmpty()) {
            return title;
        }

        return findFirstTagText(html, "h3");
    }

    private static String findDate(String html) {
        String date = findTextByClass(html, "date");
        if (!date.isEmpty()) {
            return date;
        }

        date = findFirstTagText(html, "time");
        if (!date.isEmpty()) {
            return date;
        }

        Matcher matcher = DATE_PATTERN.matcher(stripTags(html));
        return matcher.find() ? matcher.group() : "";
    }

    private static String findTextByClass(String html, String classPart) {
        Pattern pattern = Pattern.compile("(?is)<[^>]*class\\s*=\\s*(['\"])[^'\"]*"
                + Pattern.quote(classPart)
                + "[^'\"]*\\1[^>]*>(.*?)</[^>]+>");
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? stripTags(matcher.group(2)) : "";
    }

    private static String findFirstTagText(String html, String tagName) {
        Pattern pattern = Pattern.compile("(?is)<" + tagName + "\\b[^>]*>(.*?)</" + tagName + ">");
        Matcher matcher = pattern.matcher(html);
        return matcher.find() ? stripTags(matcher.group(1)) : "";
    }

    private static String stripTags(String html) {
        return decodeHtml(html
                .replaceAll("(?is)<script.*?</script>", " ")
                .replaceAll("(?is)<style.*?</style>", " ")
                .replaceAll("(?is)<[^>]+>", " ")
                .replaceAll("\\s+", " ")
                .trim());
    }

    private static String decodeHtml(String value) {
        return value.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&#39;", "'");
    }
}
