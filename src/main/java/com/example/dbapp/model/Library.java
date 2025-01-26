package com.example.dbapp.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime open;

    @Column(nullable = false)
    private LocalTime close;

    public Long getId() {
        return id;
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }

    public void setOpen(LocalTime open) {
        this.open = open;
    }

    public void setClose(LocalTime close) {
        this.close = close;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Library(Long id, LocalTime open, LocalTime close) {
        this.id = id;
        this.open = open;
        this.close = close;
    }

    public Library() {
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
