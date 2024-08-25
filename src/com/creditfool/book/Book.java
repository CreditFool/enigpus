package com.creditfool.book;

import java.util.List;
import java.util.Objects;

public abstract class Book {
    private final String id;
    protected String title;
    private Integer releaseYear;

    public Book(String id, String title, Integer releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", releaseYear=" + releaseYear + "]";
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    
}
