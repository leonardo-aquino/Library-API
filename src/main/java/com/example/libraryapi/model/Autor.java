package com.example.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/* mapeando minha coluna no banco de dados */

@Entity // uma entidade
@Table(name = "autor", schema = "public") // tabela que se chama autor
@Getter
@Setter
@ToString(exclude = {"livros"})
@EntityListeners(AuditingEntityListener.class)
public class Autor { // classe Autor

    @Id                          /* meu atributo id vai ser uma chave primária da tabela e vai ser gerado  automaticamente*/
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false) // length = tamanho de caracteres que aceita/ nullable false = nao aceita nulo
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade",length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")  // relacionamento 1 autor pode ter muitos livros/ mappedBy = dentro da entidade Livro, como esta mapeado o autor
    private List<Livro> livros; // lista de livros

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    @Column(name = "id_usuario")
    private UUID idUsuario;


}
