package br.com.book.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GutendexResponseDto(
        @JsonProperty("count") int count,
        @JsonProperty("results") List<BookDto> results
) {}
