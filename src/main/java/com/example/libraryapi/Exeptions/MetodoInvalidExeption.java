package com.example.libraryapi.Exeptions;

public class MetodoInvalidExeption extends RuntimeException{
    public MetodoInvalidExeption(String message) {
        super(message);
    }
}
