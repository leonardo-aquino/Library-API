package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;
import java.time.LocalDate;
import java.util.UUID;

public record AutorDto( UUID id,
                       String nome,
                       LocalDate dataNascimento,
                       String nacionalidade) {


    public Autor transferirDadosAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
