package com.alura.Literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne()
    private Author author;
    private String languages="";

    public Book(){}
    public Book(Long id, String title, Author author,String languages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.languages = languages;
    }
    public Book(BookDTO b) {
        this.id = b.id();
        this.title = b.title();
        if (!b.author().isEmpty())
            this.author = new Author(b.author().get(0));
        else
            this.author=new Author("Desconocido",0,0);

        if (!b.languages().isEmpty()) {
            for (String s : b.languages()) {
                this.languages += s + ",";
            }
            if (this.languages.endsWith(",")) {
                this.languages = this.languages.substring(0, this.languages.length() - 1);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
