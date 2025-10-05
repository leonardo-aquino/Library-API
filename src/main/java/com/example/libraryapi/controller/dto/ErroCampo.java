package com.example.libraryapi.controller.dto;

//DTO para retornat o campo que deu erro e o motivo
public record ErroCampo(String campo,String erro) {
}
