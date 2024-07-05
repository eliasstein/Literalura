package com.alura.Literalura.repositories;

import com.alura.Literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByTitleIgnoreCase(String name);

    @Query("SELECT b FROM Book b WHERE b.languages ILIKE(:lang)")
    List<Book> getBooksFromLanguage(String lang);
}
