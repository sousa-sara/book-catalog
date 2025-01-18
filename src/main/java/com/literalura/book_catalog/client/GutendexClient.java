package com.literalura.book_catalog.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component  // Marca como um bean do Spring
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private static final HttpClient client = HttpClient.newHttpClient(); // Reuso do cliente HTTP
    private static final ObjectMapper mapper = new ObjectMapper(); // Reuso do ObjectMapper

    public JsonNode buscarLivroPorTitulo(String titulo) throws Exception {
        String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8.toString());
        String url = BASE_URL + "?search=" + encodedTitulo;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao buscar livro. Código de status: " + response.statusCode());
        }

        return mapper.readTree(response.body());
    }
}
