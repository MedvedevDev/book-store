package com.company;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main  {

    static Scanner in = new Scanner(System.in);
    //static List<Book> books = (ArrayList)Arrays.asList(new Book[]{new Book("adsa","fsdfds", 2)});
    static List<Book> books = new ArrayList<>();
    static Gson gson = new Gson();
    private static String crunchify_file_location = "D:/java/book-store/crunchify.txt";

    public static void main(String[] args) throws  IOException{
        //Book book1 = new Book("Adsad","ddasdasd",12);
        //books.add(book1);
        //getBooks();
        crunchifyReadFromFile();
        listBook();

        String command = "";
        boolean otpusti = true;

        while (otpusti) {
            System.out.println("Add list search get edit del exit");

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

                case "search":
                    searchBooksByAuthor(1);
                    break;

                case "get":
                    getBook();
                    break;

                case "edit":
                    editBook();
                    break;

                case "del":
                    deleteBooksByAuthor();
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
        boolean continueLoop = true;
        while (continueLoop) {

            System.out.println("Выберите параметр, который хотите изменить");
            System.out.println("1\t Название книги");
            System.out.println("2\t Автор");
            System.out.println("3\t Кол-во страниц");
            System.out.println("4\t Жанр");
            System.out.println("5\t Оплёт");
            System.out.println("6\t Цену");
            System.out.println("7\t Ничего");

            int menuItem;

            menuItem = in.nextInt();
            in.nextLine();

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
                        in.nextLine();
                    }
                    break;
                case 4:
                    System.out.println("Введите жанр книги: ");
                    if (in.hasNextLine()) {
                        book.setBookGenre(in.nextLine());
                    }
                    break;
                case 5:
                    System.out.println("Введите оплёт книги: ");
                    if (in.hasNextLine()) {
                        book.setBookWrapper(in.nextLine());
                    }
                    break;
                case 6:
                    System.out.println("Введите цену: ");
                    if (in.hasNextDouble()) {
                        book.setBookPrice(in.nextDouble());
                        in.nextLine();
                    }
                    break;
                case 7:
                    return book;

                default:
                    System.out.println("Выберите правильный вариант");
            }
            continueLoop = continueMethod("Отредактировать еще один параметр? ");
        }
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
                in.nextLine();
                if (index < books.size() && index >= 0) {
                    book = books.get(index);
                    System.out.print("Текущая книга: ");
                    book.displayBookInfo();
                    bookEdit(book);
                } else {
                    System.out.println("Такой книги нет");
                }
            }
            saveBook1(books);
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
                in.nextLine();
                if (index < books.size() && index >= 0) {
                    book = books.get(index);
                    book.displayBookInfo();
                } else {
                    System.out.println("Такой книги нет");
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void listBook() {
        if (!books.isEmpty()) {
            for (Book b : books) {
                b.displayBookInfo();
            }
        } else {
            System.out.println("No books");
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

            System.out.println("Введите жанр книги: ");
            if (in.hasNextLine()) {
                book.setBookGenre(in.nextLine());
            }

            System.out.println("Введите оплёт книги: ");
            if (in.hasNextLine()) {
                book.setBookWrapper(in.nextLine());
            }

            System.out.println("Введите цену: ");
            if (in.hasNextDouble()) {
                book.setBookPrice(in.nextDouble());
                in.nextLine();
            }

            books.add(book);
            saveBook1(books);

            continueLoop = continueMethod("Добавить еще книгу? ");
        }
    }

    public static void saveBook(List<Book> book){
        try {
            FileOutputStream fos = new FileOutputStream("bookss1");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(book);
            os.close();
            fos.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void saveBook1(List<Book> book){
        crunchifyWriteToFile(gson.toJson(book));
    }

    public  static void crunchifyReadFromFile() {
        File crunchifyFile = new File(crunchify_file_location);
        if (!crunchifyFile.exists())
            log("File doesn't exist");

        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(crunchifyFile), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);
            books = gson.fromJson(myReader, new TypeToken<List<Book>>(){}.getType());

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }

        log("\nBooks loaded successfully from file " + crunchify_file_location);

    }

    public  static void crunchifyWriteToFile(String myData) {
        File crunchifyFile = new File(crunchify_file_location);
        if (!crunchifyFile.exists()) {
            try {
                File directory = new File(crunchifyFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                crunchifyFile.createNewFile();
            } catch (IOException e) {
                log("Excepton Occured: " + e.toString());
            }
        }

        try {
            // Convenience class for writing character files
            FileWriter crunchifyWriter;
            crunchifyWriter = new FileWriter(crunchifyFile.getAbsoluteFile());

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
            bufferWriter.write(myData.toString());
            bufferWriter.close();

            log("Book saved at file location: " + crunchify_file_location + "\n");
        } catch (IOException e) {
            log("Hmm.. Got an error while saving book to file " + e.toString());
        }
    }

    public static void getBooks(){
        try(FileInputStream fin = new FileInputStream("bookss1")) {
            ObjectInputStream oi = new ObjectInputStream(fin);
            books =  (ArrayList) oi.readObject();
            oi.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
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


    public static void searchBooksByAuthor(int decision){
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите автора, книги которого хотите найти");
            String author = "";

            if (in.hasNextLine()) {
                author = in.nextLine();
            }

            for (Book b : books) {
                if (b.getBookAuthor() != null && b.getBookAuthor().contains(author)) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void deleteBook(){

    }

    public static void deleteBooksByAuthor(){
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите автора, книги которого хотите удалить");
            String author = "";

            if (in.hasNextLine()) {
                author = in.nextLine();
            }

            for (int i=0; i<books.size();i++){
                if (books.get(i).getBookAuthor().contains(author)){
                    books.remove(i);
                    System.out.println("removed");
                }
            }
            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книги по автору? ");
        }
    }

    private static void log(String string) {
        System.out.println(string);
    }
}


