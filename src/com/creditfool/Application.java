package com.creditfool;

import com.creditfool.book.Book;
import com.creditfool.book.Magazine;
import com.creditfool.book.Novel;
import com.creditfool.book.ReleasePeriod;
import com.creditfool.inventory.InventoryService;
import com.creditfool.inventory.InventoryServiceImpl;
import com.creditfool.menu.Menu;

import java.util.Scanner;

public class Application {
    public static void start(boolean injectData) {
        InventoryService inventoryService = new InventoryServiceImpl();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner,inventoryService);
        if (injectData && inventoryService.getAllBook().isEmpty()) {
            populateInventory(inventoryService);
        }
        menu.mainMenu();
        scanner.close();
    }

    private static void populateInventory(InventoryService inventoryService) {
        Book novel1 = new Novel("Hyouka", 2018, "Haru", "Yonezawa Honobu");
        Book novel2 = new Novel("Goodbye Fairy", 2010, "Haru", "Yonezawa Honobu");
        Book magazine1 = new Magazine("Tempo", 2015, ReleasePeriod.MONTHLY);
        Book magazine2 = new Magazine("Shonen Jump", 2018, ReleasePeriod.WEEKLY);

        inventoryService.addBook(novel1);
        inventoryService.addBook(novel2);
        inventoryService.addBook(magazine1);
        inventoryService.addBook(magazine2);
    }
}
