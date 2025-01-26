package com.example.dbapp.repository;

import com.example.dbapp.model.Author;
import com.example.dbapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByAuthorId(Long authorId);

    Optional<Book> findBookById(Long id);

    List<Book> findAllByPublicationDateAfter(LocalDate after);

    void deleteByAuthor(Author author);
}
