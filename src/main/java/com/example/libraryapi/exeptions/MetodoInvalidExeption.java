package com.example.libraryapi.exeptions;

public class MetodoInvalidExeption extends RuntimeException{
    public MetodoInvalidExeption(String message) {
        super(message);
    }
}
