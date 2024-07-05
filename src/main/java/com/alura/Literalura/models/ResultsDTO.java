package com.alura.Literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsDTO(
        @JsonAlias("count")int count,
        @JsonAlias("results") List<BookDTO> results
) {
}
