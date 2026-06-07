package lab8.common;

public class NewsItem {
    private final String title;
    private final String date;

    public NewsItem(String title, String date) {
        this.title = title == null ? "" : title.trim();
        this.date = date == null ? "" : date.trim();
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "NewsItem{date='" + date + "', title='" + title + "'}";
    }
}
