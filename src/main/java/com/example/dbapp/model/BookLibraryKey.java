package com.example.dbapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookLibraryKey implements Serializable {
    @Column(nullable = false)
    Long bookId;

    @Column(nullable = false)
    Long libraryId;

    public Long getBookId() {
        return bookId;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLibraryKey that = (BookLibraryKey) o;
        return bookId.equals(that.bookId) && libraryId.equals(that.libraryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, libraryId);
    }

    public BookLibraryKey(Long bookId, Long libraryId) {
        this.bookId = bookId;
        this.libraryId = libraryId;
    }

    public BookLibraryKey() {
    }

    @Override
    public String toString() {
        return "BookLibraryKey{" +
                "bookId=" + bookId +
                ", libraryId=" + libraryId +
                '}';
    }
}
