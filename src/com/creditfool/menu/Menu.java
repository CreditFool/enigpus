package com.creditfool.menu;

import com.creditfool.book.Book;
import com.creditfool.book.Magazine;
import com.creditfool.book.Novel;
import com.creditfool.book.ReleasePeriod;
import com.creditfool.inventory.InventoryService;
import com.creditfool.inventory.InventoryServiceImpl;

import java.util.Scanner;
import java.util.Set;

public class Menu {
    private Scanner scanner;
    private InventoryService inventoryService;
    private UserInput userInput;

    public Menu() {
    }

    public Menu(Scanner scanner, InventoryService inventoryService) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
        this.userInput = new UserInput(scanner);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void mainMenu() {
        System.out.println("ENIGPUS ver1.0");
        int choice;
        do {
            System.out.println("Menu");
            System.out.println("1. Get all book");
            System.out.println("2. Search book by ID");
            System.out.println("3. Search book by Title");
            System.out.println("4. Add book");
            System.out.println("5. Update book");
            System.out.println("6. Delete book");
            System.out.println("7. Exit");
            System.out.println();
            choice = userInput.getIntegerChoiceInput("Select 1-7: ", 1, 7);
            switch (choice) {
                case 1 -> getAllBookMenu();
                case 2 -> searchBookByIdMenu();
                case 3 -> searchBookByTitleMenu();
                case 4 -> addBookMenu();
                case 5 -> updateBookMenu();
                case 6 -> deleteBookMenu();
                case 7 -> System.out.println("Good Bye~");
            }
            System.out.println();
        } while (choice != 7);
    }

    public void getAllBookMenu() {
        Set<Book> books = inventoryService.getAllBook();
        showBooks(books);
        System.out.println("Total: " + books.size());
    }

    public void showBooks(Set<Book> books) {
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| No  |      ID     |           Title           |  Year  |   Type   |");
        System.out.println("+-------------------------------------------------------------------+");
        if (books.isEmpty()) {
            System.out.println("| No data                                                           |");

        } else {
            int counter = 1;
            for (Book book: books) {
                System.out.printf(
                        "| %03d | %-11s | %-25s |  %03d  | %-8s |%n",
                        counter++,
                        book.getId(),
                        book.getTitle(),
                        book.getReleaseYear(),
                        getBookType(book)
                        );
            }
        }
        System.out.println("+-------------------------------------------------------------------+");
    }

    public void searchBookByIdMenu() {
        String id = userInput.getBookIdInput("Book ID: ");
        Book book = inventoryService.searchBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found");
        } else {
            displayBook(book);
        }
    }

    public void searchBookByTitleMenu() {
        String title = userInput.getStringInput("Book title: ");
        Set<Book> books = inventoryService.searchBookByTitle(title);
        showBooks(books);
        System.out.println("Total: " + books.size());
    }

    public void addBookMenu() {
        System.out.println();
        System.out.println("Choose type of book you want to add:");
        System.out.println("1. Novel");
        System.out.println("2. Magazine");
        Book newBook = null;
        int choice = userInput.getIntegerChoiceInput("Select 1-2: ", 1, 2);
        switch (choice) {
            case 1 -> {
                newBook = createNovel();
            }
            case 2 -> {
                newBook = createMagazine();
            }
        }
        if (newBook == null) {
            System.out.println("Action aborted");
            return;
        }
        inventoryService.addBook(newBook);
        System.out.println(newBook.getTitle() + " added with id " + newBook.getId());

    }

    public Book createNovel() {
        Book newNovel = null;
        while (true) {
            String title = userInput.getStringInput("Novel title: ");
            String publisher = userInput.getStringInput("Novel publisher: ");
            int year = userInput.getYearInput("Novel release year: ");
            String writer = userInput.getStringInput("Novel writer: ");
            System.out.println();
            System.out.println("Type\t\t: Novel");
            System.out.println("Title\t\t: " + title);
            System.out.println("Publisher\t: " + publisher);
            System.out.println("Release Year: " + year);
            System.out.println("Writer\t\t: " + writer);
            System.out.println();
            if (userInput.getYesOrNoInput("Add this book (Y/n)? ")) {
                newNovel = new Novel(title, year, publisher, writer);
                break;
            }
            if (!userInput.getYesOrNoInput("Reinput data (Y/n)? ")){
                break;
            }
        }
        return newNovel;
    }

    public Book createMagazine() {
        Book newMagazine = null;
        while (true) {
            String title = userInput.getStringInput("Magazine title: ");
            int year = userInput.getYearInput("Magazine release year: ");
            String prompt = "Publicaiton Periond:\n1. Weekly\n2. Montly\nSelect 1-2: ";
            int choice = userInput.getIntegerChoiceInput(prompt, 1, 2);
            ReleasePeriod releasePeriod = null;
            switch (choice) {
                case 1 -> releasePeriod = ReleasePeriod.WEEKLY;
                case 2 -> releasePeriod = ReleasePeriod.MONTHLY;
            }
            System.out.println();
            System.out.println("Type\t\t\t: Magazine");
            System.out.println("Title\t\t\t: " + title);
            System.out.println("Publish Period\t: " + releasePeriod);
            System.out.println("Release Year\t: " + year);
            System.out.println();
            if (userInput.getYesOrNoInput("Add this book (Y/n)? ")) {
                newMagazine = new Magazine(title, year, releasePeriod);
                break;
            }
            if (!userInput.getYesOrNoInput("Reinput data (Y/n)? ")){
                break;
            }
        }
        return newMagazine;
    }

    public void updateBookMenu() {
        String id = userInput.getBookIdInput("Book ID: ");
        Book book = inventoryService.searchBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found");
            return;
        }
        displayBook(book);

        do {
            if (book instanceof Novel) {
                updateNovel((Novel) book);

            } else if (book instanceof Magazine) {
                updateMagazine((Magazine) book);

            }
            ((InventoryServiceImpl) inventoryService).saveToCsv();
            displayBook(book);
            System.out.println();
        } while (userInput.getYesOrNoInput("Continue update this book (y/N)? "));
    }

    public void updateNovel(Novel novel) {
        System.out.println();
        System.out.println("1. Update title");
        System.out.println("2. Update publisher");
        System.out.println("3. Update release year");
        System.out.println("4. Update writer");
        int choice = userInput.getIntegerChoiceInput("Select 1-4: ", 1, 4);
        switch (choice) {
            case 1 -> {
                String newTitle = userInput.getStringInput("New title: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    novel.setTitle(newTitle);
                }
            }
            case 2 -> {
                String publisher = userInput.getStringInput("New publisher: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    novel.setPublisher(publisher);
                }
            }
            case 3 -> {
                int year = userInput.getYearInput("New release year: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    novel.setReleaseYear(year);
                }
            }
            case 4 -> {
                String newWriter = userInput.getStringInput("New writer: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    novel.setWriter(newWriter);
                }
            }
        }
    }

    public void updateMagazine(Magazine magazine) {
        System.out.println();
        System.out.println("1. Update title");
        System.out.println("2. Update publish period");
        System.out.println("3. Update release year");
        int choice = userInput.getIntegerChoiceInput("Select 1-3: ", 1, 3);
        switch (choice) {
            case 1 -> {
                String newTitle = userInput.getStringInput("New title: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    magazine.setTitle(newTitle);
                }
            }
            case 2 -> {
                String prompt = "Publicaiton Periond:\n1. Weekly\n2. Montly\nSelect 1-2: ";
                int input = userInput.getIntegerChoiceInput(prompt, 1, 2);
                ReleasePeriod releasePeriod = null;
                switch (input) {
                    case 1 -> releasePeriod = ReleasePeriod.WEEKLY;
                    case 2 -> releasePeriod = ReleasePeriod.MONTHLY;
                }
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    magazine.setReleasePeriod(releasePeriod);
                }
            }
            case 3 -> {
                int year = userInput.getYearInput("New release year: ");
                if (userInput.getYesOrNoInput("Apply the change (y/N)? ")) {
                    magazine.setReleaseYear(year);
                }
            }
        }
    }

    public void deleteBookMenu() {
        String id = userInput.getBookIdInput("Book ID: ");
        Book book = inventoryService.searchBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found");
            return;
        }
        displayBook(book);
        System.out.println();
        boolean isAgree = userInput.getYesOrNoInput("Are you sure to delete this book (Y/n)? ");
        if (isAgree) {
            inventoryService.deleteBookById(id);
            System.out.println("Book deleted");
        } else {
            System.out.println("Action aborted");
        }
    }

    private String getBookType(Book book) {
        if (book instanceof Novel) {
            return "Novel";
        }
        if (book instanceof Magazine) {
            return "Magazine";
        }
        return "Others";
    }

    private void displayBook(Book book) {
        System.out.println();
        if (book instanceof Novel) {
            System.out.println("ID\t\t\t: " + book.getId());
            System.out.println("Type\t\t: Novel");
            System.out.println("Title\t\t: " + book.getTitle());
            System.out.println("Publisher\t: " + ((Novel) book).getPublisher());
            System.out.println("Release Year: " + book.getReleaseYear());
            System.out.println("Writer\t\t: " + ((Novel) book).getWriter());

        } else if (book instanceof Magazine) {
            System.out.println("ID\t\t\t\t: " + book.getId());
            System.out.println("Type\t\t\t: Magazine");
            System.out.println("Title\t\t\t: " + book.getTitle());
            System.out.println("Publish Period\t: " + ((Magazine) book).getReleasePeriod());
            System.out.println("Release Year\t: " + book.getReleaseYear());

        } else {
            System.out.println("ID\t\t: " + book.getId());
            System.out.println("Type\t\t: Other");
            System.out.println("Title\t\t: " + book.getTitle());
            System.out.println("Release Year: " + book.getReleaseYear());

        }
    }
}
