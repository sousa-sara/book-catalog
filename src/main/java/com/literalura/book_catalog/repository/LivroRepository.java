package com.literalura.book_catalog.repository;

import com.literalura.book_catalog.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
