package com.example.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;
// Dto para retornar os erros

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {

    //alguns métodos estáticos para respostas de erro
    public static ErroResposta respostaPadrao(String mensagem){
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(),mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem){
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem,List.of());
    }


}
