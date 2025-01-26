package com.example.dbapp.service;

import com.example.dbapp.model.Author;
import com.example.dbapp.repository.AuthorRepository;
import com.example.dbapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    private final BookRepository bookRepository;


    public AuthorService(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    public Author findAuthorById(Long id) {
        return repository.findAuthorById(id).orElseThrow(() ->
                new NoSuchElementException("Author with id: " + id + " not found"));
    }

    public List<Author> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Author save(Author author) {
        return repository.save(author);
    }

    public List<Author> findAllWhereAgeBetween(int from, int to) {
        return repository.findAllByAgeIsBetween(from, to);
    }

    @Transactional
    public void delete(Author author) {
        bookRepository.deleteByAuthor(author);
        repository.delete(author);
    }
}
