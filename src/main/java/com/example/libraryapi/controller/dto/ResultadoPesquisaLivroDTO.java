package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.enums.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

//Dto para resultado da pesquisa de Livros
public record ResultadoPesquisaLivroDTO(UUID id,String isbn, String titulo,
                                        LocalDate dataPublicacao,
                                        GeneroLivro genero, BigDecimal preco,
                                        AutorDto autor
                                        ) {
}
