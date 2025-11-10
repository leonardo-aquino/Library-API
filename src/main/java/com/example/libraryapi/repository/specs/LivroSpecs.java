package com.example.libraryapi.repository.specs;

import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.enums.GeneroLivro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

//classe criada para ultilizarmos metodos estáticos injetados no service de livros
public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        // lambida para ver se o isbn passado é igual ao isbn que estamos procurando
        return (root,query, cb) -> cb.equal(root.get("isbn"),isbn);
    }

    // comparando o titulo passado por parametro com o titulo da entidade livro
    public static Specification<Livro> tituloLike (String titulo){
        return (root, query, cb)
                -> cb.like(cb.upper( root.get("titulo")), "%"+ titulo.toUpperCase()+ "%");
    }

    // comparando se o genero passado é igual a algum genero da entidade livro
    public static Specification<Livro> generoEqual (GeneroLivro genero){
        return (root,query,cb)-> cb.equal(root.get("genero"),genero);
    }

    // comparando o ano da data de publicação é igual ao ano passado pelo parâmetro
    public static Specification<Livro> anoPublicacaoEqual (Integer anoPublicacao){
        // funçao para extrair apenas o ano de publicação
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return (root,query,cb)
                -> cb.equal(cb.function("to_char",String.class, root.get("dataPublicacao"),
                cb.literal("YYYY")),anoPublicacao.toString());
    }


//    select * from livro l join autor a
//    on a.id = l.id_autor where upper(a.nome) like upper('%...%') ;
    public static Specification<Livro> nomeAutorLike (String nomeAutor){
        return (root,query,cb)-> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")),"%"+nomeAutor.toUpperCase()+ "%");
        };

        // como nome do autor não esta no livro, devemos fazer um join, e vamos comparar o nome do autor
        //passado por parâmetro com algum autor do Banco de Dados
    }
}
