package com.example.libraryapi.validator;
import com.example.libraryapi.exeptions.CampoInvalidExeption;
import com.example.libraryapi.exeptions.ExitIsbnExeption;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LivroValidator {

    // CONSTANTE
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;


    public void validar(Livro livro){
        if (exitsLivroComIsbn(livro)){
            throw new ExitIsbnExeption("Isbn ja existente!");
        }
        if (anoValidador(livro)){
            throw new CampoInvalidExeption("preço","Ano maior que 2019 é obrigatorio informar o preço");
        }

    }

    private boolean exitsLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if (livroEncontrado.isEmpty()) {
            return false; // ISBN não existe no banco
        }

        if (livro.getId() == null) {
            return true; // Livro novo e ISBN já existe → duplicado
        }

        Livro livroExistente = livroEncontrado.get();
        if (!livro.getId().equals(livroExistente.getId())) {
            return true; // ISBN pertence a outro livro → duplicado
        }

        return false; // ISBN pertence ao mesmo livro → permitido
    }

    private boolean anoValidador (Livro livro){
        return livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO && livro.getPreco() == null;
    }
}
