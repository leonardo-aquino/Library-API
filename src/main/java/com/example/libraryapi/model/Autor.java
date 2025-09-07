package com.example.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/* mapeando minha coluna no banco de dados */

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false) // length = tamanho de caracteres que aceita/ nullable false = nao aceita nulo
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nascionalidade",length = 50, nullable = false)
    private String nascionalidade;

    @OneToMany(mappedBy = "autor")  // relacionamento 1 autor pode ter muitos livros/ mappedBy = dentro da entidade Livro, como esta mapeado o autor

    private List<Livro> livros; // lista de livros
}
