package com.alura.Literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO (
        @JsonAlias("id") Long id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorDTO> author,
        @JsonAlias("languages") ArrayList<String>languages
){
}
