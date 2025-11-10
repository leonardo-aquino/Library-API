package com.example.libraryapi.model;

import com.example.libraryapi.model.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
public class Livro {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", nullable = false, length = 20, unique = true)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 150 )
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)// dizendo que é uma enumeração string que vai para o banco de dados
    @Column(name = "genero", nullable = false, length = 30)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2) //precision = quantos numeros aceita ao todo // scale= depois da virgula
    private BigDecimal preco;

    @JoinColumn(name = "id_autor")
    @ManyToOne
    private Autor autor;
    /* quando queremos fazer uma relação entre tabelas ultilizamos a chave estrangeira
    com a anotação JoinColumn e o nome como esta no banco de dados
    como aqui é um relacionamento de um autor pode ter varios livros entao colocamos
    ManytoOne = 1 ou mais livros para 1 autor
     */

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    @Column(name = "id_usuario")
    private UUID idUsuario;
}
