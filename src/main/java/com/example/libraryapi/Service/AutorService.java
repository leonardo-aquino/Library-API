package com.example.libraryapi.Service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    AutorRepository autorRepository;

    //Método para salvar um Autor no Banco de Dados
    public Autor salvar(Autor autor){
        autorRepository.save(autor);
        return autor;
    }

    // método para buscar o id passado por parâmetro no Banco de Dados
    public Optional<Autor> obterPorId(UUID uuid){
        return autorRepository.findById(uuid);
    }

    // método para deletar um autor
    public void deletar(UUID uuid){
        autorRepository.deleteById(uuid);
    }
}
