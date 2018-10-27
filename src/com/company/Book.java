package com.company;

public class Book {
    private String bookName;
    private int bookPages;
    private String bookAuthor;

    public Book (String name, String author, int pages){
        this.bookName = name;
        this.bookAuthor = author;
        this.bookPages = pages;
    }

    public Book(){}

    public void displayBookInfo(){
        System.out.printf("Book name: %s Book author: %s Book pages: %d \n", bookName, bookAuthor, bookPages);
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
