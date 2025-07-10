package br.com.book.catalog.client;

import br.com.book.catalog.dto.GutendexResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GutendexClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public GutendexResponseDto buscarLivroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" +
                    URLEncoder.encode(titulo, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), GutendexResponseDto.class);

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao consultar a API Gutendex: " + e.getMessage());
            return null;
        }
    }

    public GutendexResponseDto buscarTopLivrosMaisBaixados() {
        try {
            String url = "https://gutendex.com/books/?page=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), GutendexResponseDto.class);

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar top livros mais baixados: " + e.getMessage());
            return null;
        }
    }
}
