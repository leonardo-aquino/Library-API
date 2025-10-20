package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//DTO para Cadastro de Livros
public record CadastrolivroDTO(
        @ISBN
        @NotBlank(message = "Campo Obrigatório")
        String isbn,
        @NotBlank(message = "Campo Obrigatório")
        String titulo,
        @NotNull(message = "Campo Obrigatório")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "Campo Obrigatório")
        UUID id_autor) {

    public Livro mapear(){
        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);
        livro.setDataPublicacao(dataPublicacao);
        livro.setGenero(genero);
        livro.setPreco(preco);
       return null;
    }
}
