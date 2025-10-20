package com.example.libraryapi.Service;
import com.example.libraryapi.Exeptions.MetodoInvalidExeption;
import com.example.libraryapi.Validator.AutorValidator;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorValidator validator;
    @Autowired
    private LivroRepository livroRepository;

    //Método para salvar um Autor no Banco de Dados
    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    // método para buscar o id passado por parâmetro no Banco de Dados
    public Optional<Autor> obterPorId(UUID uuid){
        return autorRepository.findById(uuid);
    }

    // método para deletar um autor
    public void deletar(Autor autor){
        if (possuiLivro(autor)){
            throw new MetodoInvalidExeption(" não é possivel deletar um autor que possui algum livro");
        }
        autorRepository.delete(autor);
    }

    // método para retornar uma lista de Autor dependendo do parâmetro passado
    // se não for passado nada vai ser retornado todos
    //Query By Example
    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor,matcher);
        return autorRepository.findAll(autorExample);
    }

    // método para atualizar um Autor existente
    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor esteja cadastrado na base!");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
