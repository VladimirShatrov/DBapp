package com.example.dbapp.repository;

import com.example.dbapp.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findLibrariesById(Long id);

    List<Library> findAllByCloseBefore(LocalTime before);
}
