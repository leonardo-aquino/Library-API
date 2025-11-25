package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.controller.mapper.UsuarioMapper;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
   public void salvar(@RequestBody UsuarioDTO dto){
       Usuario usuario = mapper.toEntity(dto);
       service.salvar(usuario);
   }
}
