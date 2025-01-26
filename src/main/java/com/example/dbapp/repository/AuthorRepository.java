package com.example.dbapp.repository;

import com.example.dbapp.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorById(Long id);

    List<Author> findAllByAgeIsBetween(int fromAge, int toAge);
}
