package com.company;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookName;
    private int bookPages;
    private String bookAuthor;
    private String bookGenre;
    private String bookWrapper;
    private double bookPrice;

    //private static final long serialVersionUID = 1L;

    public Book (String name, String author, int pages){
        this.bookName = name;
        this.bookAuthor = author;
        this.bookPages = pages;
    }

    public Book(){}

    public void displayBookInfo(){
        System.out.printf("Book name: %s Book author: %s Book pages: %d Book genre: %s Book wrapper: %s Book price: %.2f\n", bookName, bookAuthor, bookPages, bookGenre, bookWrapper, bookPrice);
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
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
}