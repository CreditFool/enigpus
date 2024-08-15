package com.creditfool.book;

public class Novel extends Book {
    private String publisher;
    private String writer;

    private static int lastId = 1;

    public Novel() {
    }

    public Novel(String title, Integer releaseYear, String publisher, String writer) {
        super(title, releaseYear);
        this.publisher = publisher;
        this.writer = writer;
    }

    public Novel(String id, String title, Integer releaseYear, String publisher, String writer) {
        super(id, title, releaseYear);
        this.publisher = publisher;
        this.writer = writer;
    }

    @Override
    String createId() {
        return String.format("%04d-A-%04d", getReleaseYear(), lastId++);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Novel.lastId = lastId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id='" + getId() + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseYear=" + getReleaseYear() +
                ", writer='" + writer + '\'' +
                '}';
    }
}
