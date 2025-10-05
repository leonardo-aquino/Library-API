package com.example.libraryapi.repository;
import com.example.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*Repositório de Autores que vai fazer a comunicação com o Banco de Dados*/
public interface AutorRepository extends JpaRepository<Autor, UUID>{

    // Buscando Autor pelo nome
    List<Autor> findByNome(String nome);

    // Buscando Autor pela Nascionalidade
    List<Autor> findByNacionalidade(String nacionalidade);

    // Buscando Autor pelo nome e Nacionalidade
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndNacionalidadeAndDataNascimento(String nome, String nacionalidade, LocalDate dataNascimento);


}
