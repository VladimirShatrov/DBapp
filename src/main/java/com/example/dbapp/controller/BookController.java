package com.example.dbapp.controller;

import com.example.dbapp.model.Book;
import com.example.dbapp.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var book = service.findById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Book book,
                                    UriComponentsBuilder uriComponentsBuilder) {
        try {
            Book savedBook = service.save(book);
            return ResponseEntity.created(uriComponentsBuilder
                    .path("book/{id}")
                    .build(Map.of("id", savedBook.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedBook);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while creating book: " + e.getMessage());
        }
    }

    @PostMapping("/updateWherePublicationDateAfter/{after}")
    public ResponseEntity<?> update(@PathVariable LocalDate after) {
        try {
            List<Book> booksToUpdate = service.findAllByPublicationDateAfter(after);
            List<Book> updatedBooks = new ArrayList<>();

            for (Book b: booksToUpdate
                 ) {
                b.setCirculation(b.getCirculation() + 2);
                b.setPages((short) (b.getPages() / 2));
                b.setTitle(b.getTitle() + " - a");
                b.setDescription("-----");
                b.setPublicationDate(b.getPublicationDate().plusDays(2));
                b.setPrice(b.getPrice().add(BigDecimal.valueOf(2)));
                b.setRating(b.getRating() + 1);
                updatedBooks.add(service.save(b));
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedBooks);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while updating books: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteWherePublicationDateAfter/{after}")
    public ResponseEntity<?> delete(@PathVariable LocalDate after) {
        try {
            List<Book> booksToDelete = service.findAllByPublicationDateAfter(after);
            for (Book b: booksToDelete
                 ) {
                service.delete(b);
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while deleting books: " + e.getMessage());
        }
    }
}
