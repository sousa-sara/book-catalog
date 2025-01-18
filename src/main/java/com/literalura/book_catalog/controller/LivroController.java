package com.literalura.book_catalog.controller;

import com.literalura.book_catalog.entity.Livro;
import com.literalura.book_catalog.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Buscar um livro por título
    @PostMapping("/buscar")
    public Livro buscarLivro(@RequestParam String titulo) {
        try {
            return livroService.buscarELocalizarLivro(titulo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Criar um novo livro
    @PostMapping("/criar")
    public Livro criarLivro(@RequestBody Livro livro) {
        try {
            return livroService.criarLivro(livro);  // Chama o método criarLivro do serviço
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o livro: " + e.getMessage());
        }
    }

    // Buscar todos os livros
    @GetMapping("/listar")
    public List<Livro> listarLivros() {
        try {
            return livroService.listarLivros();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage());
        }
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public Livro buscarPorId(@PathVariable Long id) {
        try {
            return livroService.buscarPorId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar livro por ID: " + e.getMessage());
        }
    }

    // Atualizar um livro
    @PutMapping("/atualizar/{id}")
    public Livro atualizarLivro(@PathVariable Long id, @RequestBody Livro livro) {
        try {
            return livroService.atualizarLivro(id, livro);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o livro: " + e.getMessage());
        }
    }

    // Deletar um livro
    @DeleteMapping("/deletar/{id}")
    public String deletarLivro(@PathVariable Long id) {
        try {
            livroService.deletarLivro(id);
            return "Livro deletado com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o livro: " + e.getMessage());
        }
    }
}
