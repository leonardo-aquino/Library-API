package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> , JpaSpecificationExecutor<Livro> {
/*Repositório de livros que vai ter a comunicação com o Banco de Dados*/

    /* Método para procurar o livro no banco de dados pelo titulo, caso ache retorne uma lista com os achados*/
    List<Livro> findByTitulo(String titulo);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);


}
