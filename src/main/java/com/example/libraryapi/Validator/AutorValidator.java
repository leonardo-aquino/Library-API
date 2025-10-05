package com.example.libraryapi.Validator;

import com.example.libraryapi.Exeptions.ExitsAutorExeption;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AutorValidator {

    @Autowired
    private AutorRepository autorRepository;

    public void validar(Autor autor){

        // se existir um autor ja com os dados abaixo vai lançar a exeção personalizada
        if(existeAutorCadastrado(autor)) {
            throw new ExitsAutorExeption("Autor já cadastrado");
        }
    }

    // método para ver se existe algum autor cadastrado com esses dados
    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndNacionalidadeAndDataNascimento(autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());
        if (autor.getId() == null){
            return autorEncontrado.isPresent();
        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}