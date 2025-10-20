package com.example.libraryapi.controller;

import com.example.libraryapi.Service.LivroService;
import com.example.libraryapi.controller.dto.CadastrolivroDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid CadastrolivroDTO cadastrolivroDTO){

        return null;
    }

}
