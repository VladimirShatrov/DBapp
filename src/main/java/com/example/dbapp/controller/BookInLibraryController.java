package com.example.dbapp.controller;

import com.example.dbapp.model.*;
import com.example.dbapp.service.BookInLibraryService;
import com.example.dbapp.service.BookService;
import com.example.dbapp.service.LibraryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookInLibrary")
public class BookInLibraryController {
    private final BookInLibraryService service;

    private final BookService bookService;

    private final LibraryService libraryService;

    public BookInLibraryController(BookInLibraryService service, BookService bookService, LibraryService libraryService) {
        this.service = service;
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable BookLibraryKey id) {
        try {
            var bookInLibrary = service.findById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookInLibrary);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookInLibrary>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BookInLibrary bookInLibrary,
                                    UriComponentsBuilder uriComponentsBuilder) {
        try {
            Book book = bookService.findById(bookInLibrary.getId().getBookId());
            Library library = libraryService.findById(bookInLibrary.getId().getLibraryId());
            bookInLibrary.setBook(book);
            bookInLibrary.setLibrary(library);

            BookInLibrary savedBookInLibrary = service.save(bookInLibrary);
            return ResponseEntity.created(uriComponentsBuilder
                    .path("bookInLibrary/{id}")
                    .build(Map.of("id", savedBookInLibrary.getId())))
                    .body(savedBookInLibrary);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while creating book in library: " + e.getMessage());
        }
    }

    @PostMapping("/updateWhereIsAvailable/{isAvailable}")
    public ResponseEntity<?> update(@PathVariable boolean isAvailable) {
        try {
            List<BookInLibrary> booksInLibrariesToUpdate = service.findAllWhereIsAvailable(isAvailable);
            List<BookInLibrary> updatedBooksInLibraries = new ArrayList<>();

            for (BookInLibrary b: booksInLibrariesToUpdate
                 ) {
                b.setAvailable(!isAvailable);
                updatedBooksInLibraries.add(service.save(b));
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedBooksInLibraries);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while updating book in library: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteWhereIsAvailable/{isAvailable}")
    public ResponseEntity<?> delete(@PathVariable boolean isAvailable) {
        try {
            List<BookInLibrary> booksInLibrariesToDelete = service.findAllWhereIsAvailable(isAvailable);
            for (BookInLibrary b: booksInLibrariesToDelete
                 ) {
                service.delete(b);
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while deleting books in libraries: " + e.getMessage());
        }
    }
}
