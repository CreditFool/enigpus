package com.creditfool.book;

public class Magazine extends Book {
    private ReleasePeriod releasePeriod;

    private static int lastId = 1;

    public Magazine() {
    }

    public Magazine(String title, Integer releaseYear, ReleasePeriod releasePeriod) {
        super(title, releaseYear);
        this.releasePeriod = releasePeriod;
    }

    public Magazine(String id, String title, Integer releaseYear, ReleasePeriod releasePeriod) {
        super(id, title, releaseYear);
        this.releasePeriod = releasePeriod;
    }

    @Override
    String createId() {
        return String.format("%04d-B-%04d", getReleaseYear(), lastId++);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Magazine.lastId = lastId;
    }

    public ReleasePeriod getReleasePeriod() {
        return releasePeriod;
    }

    public void setReleasePeriod(ReleasePeriod releasePeriod) {
        this.releasePeriod = releasePeriod;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id='" + getId() + '\'' +
                ", title='" + title + '\'' +
                ", releasePeriod=" + releasePeriod +
                ", releaseYear=" + getReleaseYear() +
                '}';
    }
}
