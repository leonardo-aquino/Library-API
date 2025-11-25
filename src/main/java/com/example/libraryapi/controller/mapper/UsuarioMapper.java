package com.example.libraryapi.controller.mapper;

import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity (UsuarioDTO dto);

}
