package com.creditfool.inventory;

import com.creditfool.book.Book;

import java.util.Set;

public interface InventoryService {
    void addBook(Book book);
    Set<Book> searchBookByTitle(String title);
    Book searchBookById(String id);
    void deleteBookById(String id);
    Set<Book> getAllBook();
}
