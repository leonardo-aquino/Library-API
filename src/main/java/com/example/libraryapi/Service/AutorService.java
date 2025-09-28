package com.example.libraryapi.Service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // método para retornar uma lista de Autor dependendo do parâmetro passado
    // se não for passado nada vai ser retornado todos
    public  List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome!= null && nacionalidade == null){
            return autorRepository.findByNome(nome);
        }
        if(nome== null && nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        if(nome!= null && nacionalidade != null){
            return  autorRepository.findByNomeAndNacionalidade(nome,nacionalidade);
        }
        return autorRepository.findAll();
    }

    // método para atualizar um Autor existente
    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor esteja cadastrado na base!");
        }
        autorRepository.save(autor);
    }
}
