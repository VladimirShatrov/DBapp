package com.example.dbapp.controller;

import com.example.dbapp.model.Author;
import com.example.dbapp.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var author = service.findAuthorById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(author);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Author author,
                                    UriComponentsBuilder uriComponentsBuilder) {
        try {
            Author savedAuthor = service.save(author);
            return ResponseEntity.created(uriComponentsBuilder
                    .path("author/{id}")
                    .build(Map.of("id", savedAuthor.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedAuthor);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while creating author: " + e.getMessage());
        }
    }

    @PostMapping("/updateWhereAgeBetween/{from}/{to}")
    public ResponseEntity<?> update(@PathVariable int from,
                                    @PathVariable int to) {
        try {
            List<Author> authorsToUpdate = service.findAllWhereAgeBetween(from, to);
            List<Author> updatedAuthors = new ArrayList<>();

            for (Author a: authorsToUpdate
                 ) {
                a.setAge(a.getAge() + 1);
                a.setFio(a.getFio() + "a");
                updatedAuthors.add(service.save(a));
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedAuthors);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while updating authors: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteWhereAgeBetween/{from}/{to}")
    public ResponseEntity<?> delete(@PathVariable int from,
                                    @PathVariable int to) {
        try {
            List<Author> authorsToDelete = service.findAllWhereAgeBetween(from, to);
            for (Author a: authorsToDelete
            ) {
                service.delete(a);
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while deleting authors: " + e.getMessage());
        }
    }

    @PostMapping("/updateWithErrorFIO/{id}")
    public ResponseEntity<?> errorUpdate(@PathVariable Long id) {
        try {
            Author author = service.findAuthorById(id);
            author.setFio("A".repeat(512));
            Author savedAuthor = service.save(author);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedAuthor);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while update author: " + e.getMessage());
        }
    }
}
