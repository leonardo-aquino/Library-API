package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDto( UUID id,
                        @NotBlank(message = "campo obrigatório")
                        @Size(max = 100,min = 2, message = "campo fora do tamanho padrão")
                        String nome,
                       @NotNull(message = "Campo brigatório")
                       @Past(message = "Não pode ser uma data futura")
                       LocalDate dataNascimento,
                       @NotBlank(message = "Campo brigatório")
                       @Size(max = 50,min = 3, message = "campo fora do tamanho padrão")
                       String nacionalidade) {


    public Autor transferirDadosAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
