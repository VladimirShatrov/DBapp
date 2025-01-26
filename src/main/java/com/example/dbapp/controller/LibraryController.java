package com.example.dbapp.controller;

import com.example.dbapp.model.Author;
import com.example.dbapp.model.Library;
import com.example.dbapp.service.LibraryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var library = service.findById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(library);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Library>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Library library,
                                    UriComponentsBuilder uriComponentsBuilder) {
        try {
            Library savedLibrary = service.save(library);
            return ResponseEntity.created(uriComponentsBuilder
                    .path("library/{id}")
                    .build(Map.of("id", savedLibrary.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(savedLibrary);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while creating library: " + e.getMessage());
        }
    }

    @PostMapping("/updateWhereCloseBefore/{before}")
    public ResponseEntity<?> update(@PathVariable LocalTime before) {
        try {
            List<Library> librariesToUpdate = service.findAllWhereCloseBefore(before);
            List<Library> updatedLibraries = new ArrayList<>();

            for (Library l: librariesToUpdate
                 ) {
                l.setOpen(l.getClose().plusMinutes(30));
                l.setClose(l.getClose().plusMinutes(30));
                updatedLibraries.add(service.save(l));
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedLibraries);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while updating library: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteWhereCloseBefore/{before}")
    public ResponseEntity<?> delete(@PathVariable LocalTime before) {
        try {
            List<Library> librariesToDelete = service.findAllWhereCloseBefore(before);
            for (Library l: librariesToDelete
            ) {
                service.delete(l);
            }
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error while deleting libraries: " + e.getMessage());
        }
    }
}
