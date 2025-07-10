package br.com.book.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDto(
        @JsonProperty("name") String name,
        @JsonProperty("birth_year") Integer birthYear,
        @JsonProperty("death_year") Integer deathYear
) {}
