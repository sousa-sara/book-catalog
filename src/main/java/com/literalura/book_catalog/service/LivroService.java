package com.literalura.book_catalog.service;

import com.literalura.book_catalog.client.GutendexClient;
import com.literalura.book_catalog.entity.Livro;
import com.literalura.book_catalog.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final GutendexClient gutendexClient;

    @Autowired
    public LivroService(LivroRepository livroRepository, GutendexClient gutendexClient) {
        this.livroRepository = livroRepository;
        this.gutendexClient = gutendexClient;
    }

    // Buscar e localizar livro por título
    public Livro buscarELocalizarLivro(String titulo) throws Exception {
        // Chama o GutendexClient para buscar o livro
        var livroJson = gutendexClient.buscarLivroPorTitulo(titulo);

        // Verifica se a resposta contém livros e retorna o primeiro
        if (livroJson.has("results") && livroJson.get("results").size() > 0) {
            var livroNode = livroJson.get("results").get(0);
            Livro livro = new Livro();
            livro.setTitulo(livroNode.get("title").asText());
            livro.setAutor(livroNode.get("authors").get(0).get("name").asText());
            livro.setIdioma(livroNode.get("languages").get(0).asText());
            livro.setDownloads(livroNode.get("download_count").asInt());
            return livroRepository.save(livro);  // Salva no banco de dados
        } else {
            throw new Exception("Livro não encontrado.");
        }
    }

    // Criar um novo livro
    public Livro criarLivro(Livro livro) {
        try {
            return livroRepository.save(livro);  // Salva o livro no banco de dados
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o livro: " + e.getMessage());
        }
    }

    // Listar todos os livros
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    // Buscar livro por ID
    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    // Atualizar um livro existente
    public Livro atualizarLivro(Long id, Livro livro) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setIdioma(livro.getIdioma());
        livroExistente.setDownloads(livro.getDownloads());

        return livroRepository.save(livroExistente);
    }

    // Deletar um livro
    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }
}
