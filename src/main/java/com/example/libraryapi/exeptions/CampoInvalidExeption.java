package com.example.libraryapi.exeptions;

import lombok.Getter;

@Getter
public class CampoInvalidExeption extends RuntimeException {

    private String campo;

    public CampoInvalidExeption(String campo,String message) {
        super(message);
        this.campo=campo;
    }
}
