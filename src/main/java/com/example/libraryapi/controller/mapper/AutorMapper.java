package com.example.libraryapi.controller.mapper;

import com.example.libraryapi.controller.dto.AutorDto;
import com.example.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {


    @Mapping(source = "nome", target ="nome")  // no sorce colocamos o campo do parâmetro e no target o nome do campo que vai retornar
    @Mapping(source = "dataNascimento", target ="dataNascimento")
    @Mapping(source = "nacionalidade", target ="nacionalidade")
    Autor toEntity (AutorDto dto);


    AutorDto toDTO (Autor autor);
}
