package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Book implements Serializable {
    private String bookName;
    private int bookPages;
    private String bookAuthor;
    private int bookGenre;
    private String bookWrapper;
    private double bookPrice;
    private Map<Integer, String> listGenre = new HashMap<>();
    private Gson gson = new Gson();
    private String file_location = "D:/java/book-store/genres.txt";

    //private static final long serialVersionUID = 1L;

    public Book (String name, String author, int pages){
        this.bookName = name;
        this.bookAuthor = author;
        this.bookPages = pages;
    }

    private String displayGenre(){
        loadGenres();
        return listGenre.get(bookGenre);
    }

    public Book(){}

    public void displayBookInfo(){
        System.out.printf("Book name: %s Book author: %s Book pages: %d Book genre: %s Book wrapper: %s Book price: %.2f\n", bookName, bookAuthor, bookPages, displayGenre(), bookWrapper, bookPrice);
    }

    public int getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(int bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookWrapper() {
        return bookWrapper;
    }

    public void setBookWrapper(String bookWrapper) {
        this.bookWrapper = bookWrapper;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public int getBookPages() {
        return bookPages;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public  void loadGenres() {
        File crunchifyFile = new File(file_location);
        if (!crunchifyFile.exists())
            log("File doesn't exist");

        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(crunchifyFile), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);
            listGenre = gson.fromJson(myReader, new TypeToken<Map<Integer, String>>() {
            }.getType());

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }
        if (listGenre == null) {
            listGenre = new HashMap<Integer, String>();
        }
    }

    private void log(String string) {
        System.out.println(string);
    }
}