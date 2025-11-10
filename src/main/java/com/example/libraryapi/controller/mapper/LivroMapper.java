package com.example.libraryapi.controller.mapper;

import com.example.libraryapi.controller.dto.CadastrolivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class) // uses = usado para retonar o DTO do autor
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
   public abstract Livro toEntity(CadastrolivroDTO dto);

    /* Mapeando dtolivro para a entidade Livro, mas o livro espera um autor e não o id do autor
    então temos que instanciar um autorRepository para irmos no banco atravez da expresssion, e se tiver o id passado
    vai retornar o autor, se não, vai retornar null
     */


    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}
