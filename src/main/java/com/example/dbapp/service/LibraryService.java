package com.example.dbapp.service;

import com.example.dbapp.model.Library;
import com.example.dbapp.repository.BookInLibraryRepository;
import com.example.dbapp.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibraryService {
    private final LibraryRepository repository;

    private final BookInLibraryRepository bookInLibraryRepository;

    public LibraryService(LibraryRepository repository, BookInLibraryRepository bookInLibraryRepository) {
        this.repository = repository;
        this.bookInLibraryRepository = bookInLibraryRepository;
    }

    public Library findById(Long id) {
        return repository.findLibrariesById(id).orElseThrow(() ->
                new NoSuchElementException("Library with id: " + id + " not found."));
    }

    public List<Library> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Library save(Library library) {
        return repository.save(library);
    }

    public List<Library> findAllWhereCloseBefore(LocalTime before) {
        return repository.findAllByCloseBefore(before);
    }

    @Transactional
    public void delete(Library library) {
        bookInLibraryRepository.deleteByLibrary(library);
        repository.delete(library);
    }
}
