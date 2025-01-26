package com.example.dbapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Temporal(TemporalType.DATE)
    private LocalDate publicationDate;

    @ManyToOne
    private Author author;

    private short pages;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long circulation;

    private BigDecimal price;

    private Float rating;

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPages(short pages) {
        this.pages = pages;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCirculation(Long circulation) {
        this.circulation = circulation;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public short getPages() {
        return pages;
    }

    public String getDescription() {
        return description;
    }

    public Long getCirculation() {
        return circulation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Book(Long id, String title, LocalDate publicationDate, Author author, short pages, String description, Long circulation, BigDecimal price, Float rating) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.author = author;
        this.pages = pages;
        this.description = description;
        this.circulation = circulation;
        this.price = price;
        this.rating = rating;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicationDate=" + publicationDate +
                ", author=" + author +
                ", pages=" + pages +
                ", description='" + description + '\'' +
                ", circulation=" + circulation +
                '}';
    }
}
