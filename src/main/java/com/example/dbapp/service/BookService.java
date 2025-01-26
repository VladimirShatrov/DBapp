package com.example.dbapp.service;

import com.example.dbapp.model.Author;
import com.example.dbapp.model.Book;
import com.example.dbapp.repository.AuthorRepository;
import com.example.dbapp.repository.BookInLibraryRepository;
import com.example.dbapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final BookInLibraryRepository bookInLibraryRepository;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
                       BookInLibraryRepository bookInLibraryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookInLibraryRepository = bookInLibraryRepository;
    }

    public Book findById(Long id) {
        return bookRepository.findBookById(id).orElseThrow(() ->
                new NoSuchElementException("Book with id: " + id + " not found."));
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByAuthorId(Long authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }

    @Transactional
    public Book save(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository.findAuthorById(book.getAuthor().getId()).orElse(null);
            if (author == null) {
                throw new NoSuchElementException("Author with ID " + book.getAuthor().getId() + " not found");
            }
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    public List<Book> findAllByPublicationDateAfter(LocalDate after) {
        return bookRepository.findAllByPublicationDateAfter(after);
    }

    @Transactional
    public void delete(Book book) {
        bookInLibraryRepository.deleteByBook(book);
        bookRepository.delete(book);
    }
}
