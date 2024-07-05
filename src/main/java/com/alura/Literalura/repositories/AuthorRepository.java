package com.alura.Literalura.repositories;

import com.alura.Literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(String name);

    //WHERE a.birth <= :date AND a.death >= :date"
    @Query("SELECT a FROM Author a WHERE a.birth <= :date AND a.death >= :date")
    List<Author> aliveAuthorsInTheYear(int date);
}
