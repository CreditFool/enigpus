package com.creditfool.inventory;

import com.creditfool.book.Book;
import com.creditfool.book.Magazine;
import com.creditfool.book.Novel;
import com.creditfool.book.ReleasePeriod;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class InventoryServiceImpl implements InventoryService {
    private final Set<Book> books = new HashSet<>();

    public InventoryServiceImpl() {
        File file = new File("enigpus.csv");
        if (file.exists()) {
            loadFromCsv(file);
        }
    }

    @Override
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyExistsException("Book already exists in inventory");
        }
        books.add(book);
        saveToCsv();
    }

    @Override
    public Set<Book> searchBookByTitle(String title) {
        Set<Book> results = new HashSet<>();
        title = title.toLowerCase();
        for (Book book: books) {
            if (book.getTitle().toLowerCase().contains(title)) {
                results.add(book);
            }
        }
        return results;
    }

    @Override
    public Book searchBookById(String id) {
        for (Book book: books) {
            if (book.getId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void deleteBookById(String id) {
        Book book = searchBookById(id);
        if (book == null) {
            throw new BookNotExistsException("Book with id " + id + " not found");
        }
        books.remove(book);
        saveToCsv();
    }

    @Override
    public Set<Book> getAllBook() {
        return new HashSet<>(books);
    }

    public void saveToCsv() {
        File csv = new File("enigpus.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv))) {
            bw.write("");
            bw.append("novel=" + Novel.getLastId() + ";" + "magazine=" + Magazine.getLastId() + "\n");
            for (Book book: books) {
                String bookType = getBookType(book);
                switch (bookType) {
                    case "novel" -> bw.append(convertNovelToCsv((Novel) book));
                    case "magazine" -> bw.append(convertMagazineToCsv((Magazine) book));
                }
            }

        } catch (IOException e) {
            e.getStackTrace();

        }
    }

    public void loadFromCsv(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String firstLine = br.readLine();
            setBookCounterFromCsv(firstLine);
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                switch (data[0]) {
                    case "novel" -> addBook(createNovelFromCsv(line));
                    case "magazine" -> addBook(createMagazineFromCsv(line));
                }
            }
        } catch (IOException e) {

        }
    }

    public void setBookCounterFromCsv(String counterCsvLine) {
        for (String data: counterCsvLine.split(";")) {
            String[] bookData = data.split("=");
            switch (bookData[0]) {
                case "novel" -> Novel.setLastId(Integer.parseInt(bookData[1]));
                case "magazine" -> Magazine.setLastId(Integer.parseInt(bookData[1]));
            }
        }
    }

    private String getBookType(Book book) {
        if (book instanceof Novel) {
            return "novel";
        }
        if (book instanceof Magazine) {
            return "magazine";
        }
        return "others";
    }

    private String convertNovelToCsv(Novel novel) {
        return String.format(
                "%s;%s;%s;%s;%d;%s\n",
                "novel",
                novel.getId(),
                novel.getTitle(),
                novel.getPublisher(),
                novel.getReleaseYear(),
                novel.getWriter()
                );
    }

    private String convertMagazineToCsv(Magazine magazine) {
        return String.format(
                "%s;%s;%s;%s;%d\n",
                "magazine",
                magazine.getId(),
                magazine.getTitle(),
                magazine.getReleasePeriod(),
                magazine.getReleaseYear()
        );
    }

    private Book createNovelFromCsv(String line) {
        String[] data = line.split(";");
        return new Novel(data[1], data[2], Integer.parseInt(data[4]), data[3], data[5]);
    }

    private Book createMagazineFromCsv(String line) {
        String[] data = line.split(";");
        ReleasePeriod releasePeriod = null;
        switch (data[3]) {
            case "WEEKLY" -> releasePeriod = ReleasePeriod.WEEKLY;
            case "MONTHLY" -> releasePeriod = ReleasePeriod.MONTHLY;
        }
        return new Magazine(data[1], data[2], Integer.parseInt(data[4]), releasePeriod);
    }

    @Override
    public String toString() {
        return "InventoryServiceImpl{" +
                "books=" + books +
                '}';
    }
}
