package com.example.libraryapi.service;

import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.model.enums.GeneroLivro;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.repository.specs.LivroSpecs;
import com.example.libraryapi.security.SecurityService;
import com.example.libraryapi.validator.LivroValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service

public class LivroService {

    @Autowired
    private  LivroRepository livroRepository;
    @Autowired
    private  LivroValidator validator;
    @Autowired
    private SecurityService securityService;


    public Livro salvarLivro(Livro livro){

        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioPorLogin();
        livro.setUsuario(usuario);
         return livroRepository.save(livro);
    }

    public Optional<Livro> obterLivro(UUID id) {
        return  livroRepository.findById(id);
    }

    public void deletarLivro(UUID uuid) {
       livroRepository.deleteById(uuid);
    }

    public boolean existById(UUID uuid){
        return livroRepository.existsById(uuid);
    }

    // recebendo os parâmetros pela do controller para fazer a pesquisa
    public Page<Livro> pesquisar(String isbn
                                , String titulo,
                                 String nomeAutor,
                                 GeneroLivro genero,
                                 Integer anoPublicacao,
                                 Integer pagina,
                                 Integer tamanhoPagina) {


        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification
                .where(((root, query, cb) -> cb.conjunction()));


        if (isbn != null){
            //query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if (titulo != null){

            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if (genero != null){

            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if( anoPublicacao != null){
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        // criando um objeto Pageable para receber pela url a pesquisa paginda
        Pageable pageRequest = PageRequest.of(pagina,tamanhoPagina);

        // retornando os dados obtidos pelo query e o paginamento
         return livroRepository.findAll(specs, pageRequest);

    }

    public void  atualizar (Livro livro){
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor esteja cadastrado na base!");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }


}
