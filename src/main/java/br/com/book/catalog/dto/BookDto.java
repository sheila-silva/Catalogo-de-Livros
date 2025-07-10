package br.com.book.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDto(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<AuthorDto> authors,
        @JsonProperty("languages") List<String> languages,
        @JsonProperty("download_count") Integer downloadCount
) {}
