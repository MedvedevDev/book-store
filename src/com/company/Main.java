package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args){

        String command = "";
        boolean otpusti = true;

        while (otpusti) {
            System.out.println("Add list get edit exit");

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

                case "get":
                    getBook();
                    break;

                case "edit":
                    editBook();
                    break;

                case "exit":
                    otpusti = false;
                    break;

                default:
                    System.out.println("Hi bruh");
            }
        }
    }

    private static Book bookEdit(Book book){
        System.out.println("Выберите параметр, который хотите изменить");
        System.out.println("1\t Название книги");
        System.out.println("2\t Автор");
        System.out.println("3\t Кол-во страниц");
        System.out.println("4\t Ничего");

        boolean quit = false;
        int menuItem;

        do {

            menuItem = in.nextInt();

            switch (menuItem) {
                case 1:
                    System.out.println("Введите новое название книги: ");
                    if (in.hasNextLine()) {
                        book.setBookName(in.nextLine());
                    }
                    break;
                case 2:
                    System.out.println("Введите автора книги: ");
                    if (in.hasNextLine()) {
                        book.setBookAuthor(in.nextLine());
                    }
                    break;
                case 3:
                    System.out.println("Введите кол-во страниц: ");
                    if (in.hasNextInt()) {
                        book.setBookPages(in.nextInt());
                    }
                    break;
                case 4:
                    quit = true;
                    break;

                default:
                    System.out.println("Выберите правильный вариант");

            }
        } while (!quit);
        return book;
    }

       /* String a = "";
        System.out.println("Изменить название книги? y/n");
        if (in.hasNextLine()) {
            a = in.nextLine();
            if (a.equals("y")) {
                System.out.println("Введите новое название книги: ");
                if (in.hasNextLine()) {
                    book.setBookName(in.nextLine());
                }
            } else  {
                System.out.println("Изменить автора книги? y/n");
                if (in.hasNextLine()) {
                    a = in.nextLine();
                    if (a.equals("y")) {
                        System.out.println("Введите нового автора книги: ");
                        if (in.hasNextLine()) {
                            book.setBookAuthor(in.nextLine());
                        }
                    } else if (a.equals("n")) {
                        System.out.println("Изменить кол-во стр. книги? y/n");
                        if (in.hasNextLine()) {
                            a = in.nextLine();
                            if (a.equals("y")) {
                                System.out.println("Введите кол-во страниц: ");
                                if (in.hasNextInt()) {
                                    book.setBookPages(in.nextInt());
                                    in.nextLine();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("wtf");
        }*/


    private static void editBook(){
        boolean continueLoop = true;
        while (continueLoop) {
            int index;
            Book book = new Book();
            System.out.println("Введите номер книги, которую надо отредактировать:");
            if (in.hasNextInt()) {
                index = in.nextInt() - 1;
                if (index < books.size() && index >= 0) {
                    book = books.get(index);
                    System.out.print("Текущая книга: ");
                    book.displayBookInfo();
                    bookEdit(book);
                } else {
                    System.out.println("Такой книги нет");
                }
                in.nextLine();
            }
            continueLoop = continueMethod("Отредактировать еще одну книгу? ");
        }
    }

    private static void getBook() {
        boolean continueLoop = true;
        while (continueLoop) {
        int index;
        Book book = new Book();
        System.out.println("Введите номер книги: ");
        if (in.hasNextInt()) {
            index = in.nextInt() - 1;
            if (index < books.size() && index >= 0) {
                book = books.get(index);
                book.displayBookInfo();
            } else {
                System.out.println("Такой книги нет");
            }
            in.nextLine(); // TODO: 11.07.2019 Узнать что делает метод
        }
        continueLoop = continueMethod("Найти еще книгу? ");
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

           continueLoop = continueMethod("Добавить еще книгу? ");
        }
    }


    public static boolean continueMethod(String str){
        boolean continueLoop = true;
        System.out.println(str + " y/n");

        String a = "";

        if (in.hasNextLine()) {
            a = in.nextLine();
            if (a.equals("y")) {
                continueLoop = true;
            } else if (a.equals("n")){
                continueLoop = false;
            } else {
                System.out.println("FATAL ERROR");
                continueLoop = false;
            }
        }
        return continueLoop;
    }
}


