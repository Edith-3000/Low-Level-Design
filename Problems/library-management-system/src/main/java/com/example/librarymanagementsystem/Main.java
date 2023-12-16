package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.services.Library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library libraryInstance;
        DateTimeFormatter formatter;
        LocalDate dueDate;

        while(true) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "create_library":
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.setNumberOfRacks(Integer.parseInt(command[1]));
                    break;
                case "add_book":
                    List<String> authors = new ArrayList<>(Arrays.asList(command[3].split(",")));
                    List<String> publishers = new ArrayList<>(Arrays.asList(command[4].split(",")));
                    List<String> bookCopyIDs = new ArrayList<>(Arrays.asList(command[5].split(",")));
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.addBook(Integer.parseInt(command[1]), command[2], authors, publishers, bookCopyIDs);
                    break;
                case "remove_book_copy":
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.removeBookCopy(command[1]);
                    break;
                case "borrow_book":
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dueDate = LocalDate.parse(command[3], formatter);
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.borrowBook(Integer.parseInt(command[1]), command[2], dueDate);
                    break;
                case "borrow_book_copy":
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dueDate = LocalDate.parse(command[3], formatter);
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.borrowBookCopy(command[1], command[2], dueDate);
                    break;
                case "return_book_copy":
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.returnBookCopy(command[1]);
                    break;
                case "print_borrowed":
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.printBorrowed(command[1]);
                    break;
                case "search":
                    libraryInstance = Library.getLibraryInstance();
                    libraryInstance.search(command[1], command[2]);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid operation!");
            }
        }
    }
}
