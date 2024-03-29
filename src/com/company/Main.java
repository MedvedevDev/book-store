package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class Main {

    static Scanner in = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static Map<Integer, String> genreList;
    static Gson gson = new Gson();
    private static String crunchify_file_location = "D:/java/book-store/crunchify.txt";
    private static GenreUtility gu = new GenreUtility();

    public static void main(String[] args) throws IOException {
        genreList = gu.loadGenres();
        if (genreList == null) {
            System.out.println(" NULL ");
            genreList = new HashMap<Integer, String>();
        } else {
            log(genreList.size() + " SIZE ");
        }
        crunchifyReadFromFile();
        listBook();

        String command = "";
        boolean otpusti = true;

        while (otpusti) {
            System.out.println("***** Add list search get edit del genres exit *****");

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
                    searchBook();
                    break;

                case "get":
                    getBook();
                    break;

                case "edit":
                    editBook();
                    break;

                case "del":
                    deleteBook();
                    break;

                case "genres":
                    genresMenu();
                    break;

                case "exit":
                    otpusti = false;
                    break;

                default:
                    System.out.println("Hi bruh");
            }
        }
    }

    //---------------------------------------- GENRES

    private static void genresMenu() {
        String command = "";
        boolean otpusti = true;

        while (otpusti) {
            System.out.println("**GENRES MENU: Add del list edit exit *****");

            if (in.hasNextLine()) {
                command = in.nextLine().toLowerCase();
            }

            switch (command) {
                case "add":
                    genreList = gu.addGenre();
                    break;

                case "del":
                    genreList = gu.deleteGenre();
                    break;

                case "list":
                    listGenres();
                    break;

                case "edit":
                    genreList = gu.editGenre();
                    break;

                case "exit":
                    otpusti = false;
                    break;

                default:
                    System.out.println("Выберите правильный пункт");
            }
        }
    }

    public static void listGenres() {
        if (!genreList.isEmpty()) {
            for (Map.Entry me : genreList.entrySet()) {
                System.out.println("ID: " + me.getKey() + " & ЖАНР: " + me.getValue());
            }
        } else {
            System.out.println("No genres");
        }
    }

    private static void chooseNewGenre(Book book) {
        System.out.println("-- Выберите новый жанр книги:");
        listGenres();
        int userChoose = validateInt();
        if (genreList.get(userChoose) != null) {
            book.setBookGenre(userChoose);
        } else {
            System.out.println("Не правельный ID");
        }
    }


    private static Book bookEdit(Book book) {
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
                    book.setBookPages(validateInt());
                    break;
                case 4:
                       chooseNewGenre(book);
                    break;
                case 5:
                    System.out.println("Введите оплёт книги: ");
                    if (in.hasNextLine()) {
                        book.setBookWrapper(in.nextLine());
                    }
                    break;
                case 6:
                    System.out.println("Введите цену: ");
                    book.setBookPrice(validateDouble());
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

    private static void editBook() {
        boolean continueLoop = true;
        while (continueLoop) {
            int index;
            Book book = new Book();
            System.out.println("Введите номер книги, которую надо отредактировать:");
            index = validateInt() - 1;
            if (index < books.size() && index >= 0) {
                book = books.get(index);
                System.out.print("Текущая книга: ");
                book.displayBookInfo();
                bookEdit(book);
            } else {
                System.out.println("Такой книги нет");
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

            index = validateInt() - 1;
            if (index < books.size() && index >= 0) {
                book = books.get(index);
                book.displayBookInfo();
            } else {
                System.out.println("Такой книги нет");
            }
        }
        continueLoop = continueMethod("Найти еще книгу? ");
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

    public static void addBook() {
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
            book.setBookPages(validateInt());

            chooseNewGenre(book);

            System.out.println("Введите оплёт книги: ");
            if (in.hasNextLine()) {
                book.setBookWrapper(in.nextLine());
            }

            System.out.println("Введите цену: ");
            book.setBookPrice(validateDouble());

            books.add(book);
            saveBook1(books);

            continueLoop = continueMethod("Добавить еще книгу? ");
        }
    }

    public static void saveBook(List<Book> book) {
        try {
            FileOutputStream fos = new FileOutputStream("bookss1");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(book);
            os.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveBook1(List<Book> book) {
        crunchifyWriteToFile(gson.toJson(book));
    }

    public static void crunchifyReadFromFile() {
        File crunchifyFile = new File(crunchify_file_location);
        if (!crunchifyFile.exists())
            log("File doesn't exist");

        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(crunchifyFile), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);
            books = gson.fromJson(myReader, new TypeToken<List<Book>>() {
            }.getType());
            if (books == null){
                books = new ArrayList<>();
            }

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }

        log("\nBooks loaded successfully from file " + crunchify_file_location);

    }

    public static void crunchifyWriteToFile(String myData) {
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

    public static void getBooks() {
        try (FileInputStream fin = new FileInputStream("bookss1")) {
            ObjectInputStream oi = new ObjectInputStream(fin);
            books = (ArrayList) oi.readObject();
            oi.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean continueMethod(String str) {
        boolean continueLoop = true;
        String userinput = "";
        System.out.println(str + " y/n");

//        String a = "";
//
//        if (in.hasNextLine()) {
//            a = in.nextLine();
//            if (a.equals("y")) {
//                continueLoop = true;
//            } else if (a.equals("n")) {
//                continueLoop = false;
//            } else {
//                System.out.println("FATAL ERROR");
//                continueLoop = false;
//            }
//        }
            boolean choiceIsOK = false;
            do{
                if (in.hasNextLine()) {
                    userinput = in.nextLine();
                }

                char choice = userinput.toLowerCase().charAt(0);
                switch(choice){
                    case 'y':
                        continueLoop = true;
                        choiceIsOK = true;

                        break;
                    case 'n':
                        continueLoop = false;
                        choiceIsOK = true;
                        break;
                    default:
                        // error or warning
                        System.out.println("Type Y or N to respectively continue or quit");
                        break;
                }
            }while(!choiceIsOK);

        return continueLoop;
    }
    // -----------------------------------------------  ПОИСК

    public static void searchBook() {
        boolean continueLoop = true;
        while (continueLoop) {

            System.out.println("** По какому параметру найти книгу? ");
            System.out.println("1\t По названию");
            System.out.println("2\t По автору");
            System.out.println("3\t По кол-ву страниц");
            System.out.println("4\t По жанру");
            System.out.println("5\t По оплёту");
            System.out.println("6\t По цене");
            System.out.println("7\t Не искать");

            int menuItem;

            menuItem = in.nextInt();
            in.nextLine();

            switch (menuItem) {
                case 1:
                    searchBooksByName();
                    break;
                case 2:
                    searchBooksByAuthor();
                    break;
                case 3:
                    searchBooksByPages();
                    break;
                case 4:
                    searchBooksByGenre();
                    break;
                case 5:
                    searchBooksByWrapper();
                    break;
                case 6:
                    searchBooksByPrice();
                    break;
                case 7:
                    return;

                default:
                    System.out.println("Выберите правильный вариант");
            }
            continueLoop = continueMethod("** Найти еще книги по параметрам? ");
        }
    }

    public static void searchBooksByName() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите название книги, которую хотите найти");
            String name = inputMore();

//            do {
//                if (in.hasNextLine()) {
//                    name = in.nextLine();
//                }
//                if (name.length()<3){
//                    System.out.println("Введите больше 3-х символов");
//                }
//            }while (name.length()<3);

            for (Book b : books) {
                if (b.getBookName() != null && b.getBookName().toLowerCase().contains(name.toLowerCase())) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void searchBooksByPages() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите минимальное кол-во стр.");
            int minP =  validateInt();

            System.out.println("Введите максимальное кол-во стр.");
            int maxP =  validateInt();

            for (Book b : books) {
                if (b.getBookPages() != 0 && b.getBookPages() >= minP && b.getBookPages() <= maxP) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void searchBooksByAuthor() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите автора, книги которого хотите найти");
            String author = inputMore();

            for (Book b : books) {
                if (b.getBookAuthor() != null && b.getBookAuthor().contains(author)) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void searchBooksByGenre() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите жанр книги, которую хотите найти");
            listGenres();
            int userChoose = validateInt();
            for (Book b : books) {
                if (b.getBookGenre() != 0 && b.getBookGenre() == userChoose) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void searchBooksByWrapper() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите оплёт книги, которую хотите найти");
            String wrapper = "";

            if (in.hasNextLine()) {
                wrapper = in.nextLine();
            }

            for (Book b : books) {
                if (b.getBookWrapper() != null && b.getBookWrapper().contains(wrapper)) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    public static void searchBooksByPrice() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите минимальную цену");
            double minP = validateDouble();

            System.out.println("Введите максимальную цену");
            double maxP = validateDouble();

            for (Book b : books) {
                if (b.getBookPrice() != 0 && b.getBookPrice() >= minP && b.getBookPrice() <= maxP) {
                    b.displayBookInfo();
                }
            }
            continueLoop = continueMethod("Найти еще книгу? ");
        }
    }

    // -----------------------------------------------  УДАЛЕНИЕ

    public static void deleteBook() {
        boolean continueLoop = true;
        while (continueLoop) {

            System.out.println("** По какому параметру удалить книгу? ");
            System.out.println("1\t По названию");
            System.out.println("2\t По автору");
            System.out.println("3\t По кол-ву страниц");
            System.out.println("4\t По жанру");
            System.out.println("5\t По оплёту");
            System.out.println("6\t По цене");
            System.out.println("7\t Не искать");

            int menuItem;

            menuItem = in.nextInt();
            in.nextLine();

            switch (menuItem) {
                case 1:
                    deleteBooksByName();
                    break;
                case 2:
                    deleteBooksByAuthor();
                    break;
                case 3:
                    deleteBooksByPages();
                    break;
                case 4:
                    deleteBooksByGenre();
                    break;
                case 5:
                    deleteBooksByWrapper();
                    break;
                case 6:
                    deleteBooksByPrice();
                    break;
                case 7:
                    return;

                default:
                    System.out.println("Выберите правильный вариант");
            }
            continueLoop = continueMethod("** Удалить еще книги по параметрам? ");
        }
    }

    public static void deleteBooksByName() {
        List<Book> booksToDelete = new ArrayList<>();
        boolean isDeleted = false;
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите название книги, которую хотите удалить");
            String name = inputMore();

            for (Book b : books) {
                if (b.getBookName() != null && b.getBookName().toLowerCase().contains(name.toLowerCase())) {
                    isDeleted = true;
                    booksToDelete.add(b);
                }
            }

            if (!isDeleted) {
                System.out.println("Книг не найдено");
            } else {
                deleteBooks(booksToDelete);
            }

            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книги по названию? ");
        }
    }

    public static void deleteBooksByAuthor() {
        List<Book> booksToDelete = new ArrayList<>();
        boolean isDeleted = false;
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите автора, книги которого хотите удалить");
            String author = inputMore();

            for (Book b : books) {
                if (b.getBookAuthor() != null && b.getBookAuthor().toLowerCase().contains(author.toLowerCase())) {
                    isDeleted = true;
                    booksToDelete.add(b);
                }
            }

            if (!isDeleted) {
                System.out.println("Книг не найдено");
            } else {
                deleteBooks(booksToDelete);
            }
            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книги по автору? ");
        }
    }

    public static void deleteBooksByPages() {
        List<Book> booksToDelete = new ArrayList<>();
        boolean isDeleted = false;
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите минимальное кол-во стр.");
            int minP = validateInt();

            System.out.println("Введите максимальное кол-во стр.");
            int maxP = validateInt();

            for (Book b : books) {
                if (b.getBookPages() != 0 && b.getBookPages()>= minP && b.getBookPages() <= maxP) {
                    isDeleted = true;
                    booksToDelete.add(b);
                }
            }
            if (!isDeleted) {
                System.out.println("Книг не найдено");
            } else {
                deleteBooks(booksToDelete);
            }

            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книгу? ");
        }
    }

    public static void deleteBooksByGenre() {
        List<Book> booksToDelete = new ArrayList<>();
        boolean isDeleted = false;
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("** Выберите жанр книг, которые хотите удалить");
            listGenres();

            int userChoose = validateInt();
            try {
                for (Book b : books) {
                    if (b.getBookGenre() != 0 && b.getBookGenre() == userChoose) {
                        isDeleted = true;
                        booksToDelete.add(b);
                    }
                }
                if (!isDeleted) {
                    System.out.println("Книг не найдено");
                } else {
                    deleteBooks(booksToDelete);
                }

            } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели некорректный индекс");
             }
            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книги по жанру? ");
        }
    }

    public static void deleteBooksByWrapper() {
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите оплёт книги, которую хотите удалить");
            String wrapper = "";

            if (in.hasNextLine()) {
                wrapper = in.nextLine();
            }

//            for (int i = 0; i < books.size(); i++) {
//                if (books.get(i).getBookWrapper().contains(wrapper)) {
//                    books.remove(i);
//                    System.out.println("removed");
//                }
//            }
            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книги по оплёту? ");
        }
    }

    public static void deleteBooksByPrice() {
        List<Book> booksToDelete = new ArrayList<>();
        boolean isDeleted = false;
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Введите минимальную цену");
            double minP = validateDouble();

            System.out.println("Введите максимальную цену");
            double maxP = validateDouble();

            for (Book b : books) {
                if (b.getBookPages() != 0 && b.getBookPrice()>= minP && b.getBookPrice() <= maxP) {
                    isDeleted = true;
                    booksToDelete.add(b);
                }
            }
            if (!isDeleted) {
                System.out.println("Книг не найдено");
            } else {
                deleteBooks(booksToDelete);
            }

            saveBook1(books);
            continueLoop = continueMethod("Удалить еще книгу? ");
        }
    }

    private static void log(String string) {
        System.out.println(string);
    }

//    private static void patternDec(){
//        Pattern pattern = new Pattern("[A-Za-z]{1,20}");
//        if ((pattern.matcher(s).matches()))
//        { System.out.println("111");};
//    }

    private static int validateInt() {
        int number;
        String mas = "";
        do {
            System.out.print(mas);
            while (!in.hasNextInt()) {
                String input = in.next();
                System.out.printf("\"%s\" - не является числом, попробуйсте снова: ", input);
            }
            number = in.nextInt();
            mas = "Вы ввели отрицательное число, попробуйсте снова: ";
            in.nextLine();
        } while (number < 0);

        return number;
    }

    private static double validateDouble() {
        double number;
        String mas = "";
        do {
            System.out.print(mas);
            while (!in.hasNextDouble()) {
                String input = in.next();
                System.out.printf("\"%s\" - не является числом, попробуйсте снова: ", input);
            }
            number = in.nextDouble();
            mas = "Вы ввели отрицательное число, попробуйсте снова: ";
            in.nextLine();
        } while (number < 0);

        return number;
    }

    private static String inputMore(){
        String name = "";
        do {
            if (in.hasNextLine()) {
                name = in.nextLine();
            }
            if (name.length()<3){
                System.out.println("Введите больше 3-х символов");
            }
        } while(name.length()<3);

        return name;
    }

    private static void deleteBooks(List<Book> booksToDelete){
        System.out.println("------ Хотите удалить ? y/n-----");
        for (Book b : booksToDelete){
            b.displayBookInfo();
        }
        boolean choiceIsOK = false;
        String userinput = "";
        do {
            if (in.hasNextLine()) {
                userinput = in.nextLine();
            }

            char choice = userinput.toLowerCase().charAt(0);
            switch (choice) {
                case 'y':
                    books.removeAll(booksToDelete);
                    System.out.println("Книги удалены");
                    choiceIsOK = true;
                    break;
                case 'n':
                    choiceIsOK = true;
                    break;
                default:
                    // error or warning
                    System.out.println("Type Y or N to respectively continue or quit");
                    break;
            }
        } while (!choiceIsOK);
    }
}


