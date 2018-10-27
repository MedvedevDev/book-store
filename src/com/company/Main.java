package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {

        String command = "";
        boolean otpusti = true;

        while (otpusti) {
            System.out.println("Add list edit exit");

            if (in.hasNextLine()) {
                command = in.nextLine();
            }

            switch (command) {
                case "add":
                    addBook();
                    break;

                case "list":
                    listBook();
                    break;

                case "exit":
                    otpusti = false;
                    break;


                default:
                    System.out.println("Hi bruh");
            }
        }
    }

    public static void listBook() {
        for (Book b : books) {
            b.displayBookInfo();
        }
    }

    public static void addBook (){
        boolean continueLoop = true;
        while (continueLoop) {
            Book book = new Book();
            System.out.println("Введите название книги: ");
            if (in.hasNextLine()) {
                book.setBookName(in.nextLine());
            }

            System.out.println("Введите автора книги: ");
            if (in.hasNextLine()) {
                book.setBookAuthor(in.nextLine());
            }

            System.out.println("Введите кол-во страниц: ");
            if (in.hasNextInt()) {
                book.setBookPages(in.nextInt());
                in.nextLine();
            }

            books.add(book);

            System.out.println("Continue? y/n");

            String a = "";

            if (in.hasNextLine()) {
                a = in.nextLine();
                if (a.equals("y")) {
                    continueLoop = true;
                } else if (a.equals("n")){
                    continueLoop = false;
                } else {
                    System.out.println("FATAL ERROR");
                }
            }
        }
    }
}


