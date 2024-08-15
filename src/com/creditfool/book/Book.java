package com.creditfool.book;

import java.util.Objects;

public abstract class Book {
    private final String id;
    protected String title;
    private Integer releaseYear;

    public Book() {
        this.id = createId();
    }

    public Book(String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.id = createId();
    }

    public Book(String id, String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.id = id;
    }

    abstract String createId();

    public abstract String getTitle();

    public String getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
