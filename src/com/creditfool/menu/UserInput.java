package com.creditfool.menu;

import java.util.Scanner;

public class UserInput {
    private Scanner scanner;

    public UserInput() {
    }

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getYearInput(String prompt) {
        int year;
        while (true) {
            try {
                System.out.print(prompt);
                year = this.scanner.nextInt();
                this.scanner.nextLine();
                if (year < 0 || year > 9999) {
                    throw new NotValidInputException("Not a valid year");
                }
                break;

            } catch (NotValidInputException e) {
                System.out.println(e.getMessage());

            } catch (RuntimeException e) {
                this.scanner.nextLine();
                System.out.println("Enter a valid year\n");

            }
        }
        return year;
    }

    public String getBookIdInput(String prompt) {
        String id;
        while (true) {
            try {
                System.out.print(prompt);
                id = this.scanner.nextLine();
                String[] token = id.split("-");
                if (token.length != 3) {
                    throw new RuntimeException("Not a valid book ID");
                }
                if (!isNumber(token[0]) || (token[0].length() != 4)) {
                    throw new RuntimeException("Not a valid book ID");
                }
                if (!isNumber(token[2]) || (token[2].length() != 4)) {
                    throw new RuntimeException("Not a valid book ID");
                }
                break;

            } catch (RuntimeException e) {
                System.out.println("Enter a valid book ID format\n");

            }
        }
        return id;
    }

    public int getIntegerChoiceInput(String prompt, int first, int last) {
        int choice;
        while (true) {
            try {
                System.out.print(prompt);
                choice = this.scanner.nextInt();
                this.scanner.nextLine();
                if (choice < first || choice > last) {
                    throw new NotValidInputException("Not a valid choice\n");
                }
                break;

            } catch (NotValidInputException e) {
                System.out.println(e.getMessage());

            } catch (RuntimeException e) {
                this.scanner.nextLine();
                System.out.println("Enter a valid choice\n");

            }
        }
        return choice;
    }

    public boolean getYesOrNoInput(String prompt) {
        String choice;
        while (true) {
            try {
                System.out.print(prompt);
                choice = this.scanner.nextLine();
                switch (choice) {
                    case "y" -> {
                        return true;
                    }
                    case "n" -> {
                        return false;
                    }
                    default -> {
                        throw new NotValidInputException("Choose Y/n");
                    }
                }

            } catch (NotValidInputException e) {
                System.out.println(e.getMessage());

            } catch (RuntimeException e) {
                System.out.println("Enter a valid choice\n");

            }
        }
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine();
    }

    private boolean isNumber(String numString) {
        for (int i=0; i<numString.length(); i++) {
            if (!Character.isDigit(numString.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
