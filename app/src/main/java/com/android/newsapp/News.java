package com.android.newsapp;

public class News {

    private String section;
    private String title;
    private String webURL;
    private String date;

    public News(String section, String title, String webUrl, String date) {
        this.section = section;
        this.title = title;
        this.webURL = webUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getDate() {
        return date;
    }

}
