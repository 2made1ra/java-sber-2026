package lab8.examples;

import lab8.common.HtmlPageParser;
import lab8.common.NewsItem;

import java.io.IOException;
import java.util.List;

public class Example6HtmlNews {
    private static final String URL = "http://fat.urfu.ru/index.html";

    public static void main(String[] args) throws Exception {
        String html;
        try {
            html = HtmlPageParser.fetchWithRetries(URL, 2, 5000);
        } catch (IOException exception) {
            System.out.println("News page is unavailable, local HTML sample will be used.");
            html = HtmlPageParser.sampleNewsPage();
        }

        List<NewsItem> news = HtmlPageParser.parseNews(html);
        for (NewsItem item : news) {
            System.out.println(item);
        }
    }
}
