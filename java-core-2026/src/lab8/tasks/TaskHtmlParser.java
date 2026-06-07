package lab8.tasks;

import lab8.common.HtmlPageParser;
import lab8.common.Lab8Files;
import lab8.common.NewsItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class TaskHtmlParser {
    private static final String URL = "http://fat.urfu.ru/index.html";

    public static void main(String[] args) throws Exception {
        String html;
        try {
            html = HtmlPageParser.fetchWithRetries(URL, 3, 5000);
        } catch (IOException exception) {
            System.out.println("Connection error: " + exception.getMessage());
            System.out.println("Using local HTML sample after retry attempts.");
            html = HtmlPageParser.sampleNewsPage();
        }

        List<NewsItem> news = HtmlPageParser.parseNews(html);
        Path output = Lab8Files.demoDirectory("task-html-parser").resolve("news.txt");
        HtmlPageParser.saveNews(output, news);

        System.out.println("News saved to: " + output.toAbsolutePath());
        for (NewsItem item : news) {
            System.out.println(item);
        }
    }
}
