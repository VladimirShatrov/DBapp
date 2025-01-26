package com.example.dbapp.model;

import jakarta.persistence.*;

@Entity
public class BookInLibrary {

    @EmbeddedId
    private BookLibraryKey id;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    @ManyToOne
    @MapsId("libraryId")
    private Library library;

    @Column()
    private boolean isAvailable;

    public BookInLibrary(BookLibraryKey id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }

    public BookInLibrary() {
    }

    public BookLibraryKey getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setId(BookLibraryKey id) {
        this.id = id;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public BookInLibrary(BookLibraryKey id, Book book, Library library, boolean isAvailable) {
        this.id = id;
        this.book = book;
        this.library = library;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "BookInLibrary{" +
                "id=" + id +
                ", book=" + book +
                ", library=" + library +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
