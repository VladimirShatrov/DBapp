package com.example.dbapp.service;

import com.example.dbapp.model.BookInLibrary;
import com.example.dbapp.model.BookLibraryKey;
import com.example.dbapp.repository.BookInLibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookInLibraryService {
    private final BookInLibraryRepository bookInLibraryRepository;

    public BookInLibraryService(BookInLibraryRepository bookInLibraryRepository) {
        this.bookInLibraryRepository = bookInLibraryRepository;
    }

    @Transactional
    public BookInLibrary save(BookInLibrary bookInLibrary) {
        return bookInLibraryRepository.save(bookInLibrary);
    }

    public BookInLibrary findById(BookLibraryKey id) {
        return bookInLibraryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Book in library with id: " + id + " not found."));
    }

    public List<BookInLibrary> getAll() {
        return bookInLibraryRepository.findAll();
    }

    public List<BookInLibrary> findAllWhereIsAvailable(boolean isAvailable) {
        return bookInLibraryRepository.findByIsAvailable(isAvailable);
    }

    public void delete(BookInLibrary bookInLibrary) {
        bookInLibraryRepository.delete(bookInLibrary);
    }
}
