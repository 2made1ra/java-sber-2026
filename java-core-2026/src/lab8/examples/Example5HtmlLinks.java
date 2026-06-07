package lab8.examples;

import lab8.common.HtmlPageParser;

import java.io.IOException;
import java.util.List;

public class Example5HtmlLinks {
    private static final String URL = "https://itlearn.ru/first-steps";

    public static void main(String[] args) throws Exception {
        String html;
        try {
            html = HtmlPageParser.fetchWithRetries(URL, 2, 5000);
        } catch (IOException exception) {
            System.out.println("Page is unavailable, local HTML sample will be used.");
            html = HtmlPageParser.sampleLinksPage();
        }

        List<String> links = HtmlPageParser.parseLinks(html, URL);
        for (String link : links) {
            System.out.println(link);
        }
    }
}
