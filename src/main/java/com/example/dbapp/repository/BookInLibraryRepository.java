package com.example.dbapp.repository;

import com.example.dbapp.model.Book;
import com.example.dbapp.model.BookInLibrary;
import com.example.dbapp.model.BookLibraryKey;
import com.example.dbapp.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookInLibraryRepository extends JpaRepository<BookInLibrary, BookLibraryKey> {
    List<BookInLibrary> findByIsAvailable(boolean available);

    void deleteByLibrary(Library library);

    void deleteByBook(Book book);
}
