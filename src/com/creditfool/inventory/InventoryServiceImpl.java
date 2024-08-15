package com.creditfool.inventory;

import com.creditfool.book.Book;

import java.util.HashSet;
import java.util.Set;

public class InventoryServiceImpl implements InventoryService {
    private final Set<Book> books = new HashSet<>();

    @Override
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyExistsException("Book already exists in inventory");
        }
        books.add(book);
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
    }

    @Override
    public Set<Book> getAllBook() {
        return new HashSet<>(books);
    }

    @Override
    public String toString() {
        return "InventoryServiceImpl{" +
                "books=" + books +
                '}';
    }
}
