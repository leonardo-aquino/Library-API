package com.example.libraryapi.controller;

import com.example.libraryapi.service.LivroService;
import com.example.libraryapi.controller.common.Location;
import com.example.libraryapi.controller.dto.CadastrolivroDTO;
import com.example.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.example.libraryapi.controller.mapper.LivroMapper;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.model.enums.GeneroLivro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements Location {

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid CadastrolivroDTO cadastrolivroDTO){
        Livro livro = mapper.toEntity(cadastrolivroDTO);
        livroService.salvarLivro(livro);
        URI location = location(livro.getId());
        return  ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> obterPorId(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        Optional<Livro> livro =livroService.obterLivro(uuid);
        if (livro.isPresent()){
            Livro livroEntidade = livro.get();
            ResultadoPesquisaLivroDTO dto = mapper.toDTO(livroEntidade);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID não encontrado");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletarLivro (@PathVariable("id") String id){
        if (livroService.existById(UUID.fromString(id))){
            livroService.deletarLivro(UUID.fromString(id));
            return ResponseEntity.ok().body("Livro Deletado");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public  ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(@RequestParam (value = "isbn", required = false) String isbn
                                                          , @RequestParam (value = "titulo",required = false) String titulo,
                                                                     @RequestParam (value = "nome-autor", required = false) String nomeAutor,
                                                                     @RequestParam(value = "genero", required = false)
                                                            GeneroLivro generoLivro ,
                                                                     @RequestParam (value = "ano-publicação", required = false)Integer anoPublicacao,
                                                                     @RequestParam(value = "pagina", defaultValue = "0")Integer pagina,
                                                                     @RequestParam(value = "tamano-pagina", defaultValue = "10")Integer tamanhoPagina)
    {
        Page<Livro> paginaResultado = livroService.pesquisar(isbn,titulo,nomeAutor,generoLivro,anoPublicacao,pagina,tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable ("id") String id,
                                             @Valid @RequestBody CadastrolivroDTO dto ){
        /*pegando o id passado pela url
        * transformando em uuid
        * obtendo o livro por uuid
        * criando um livro novo e mapeando o dto recebido pra ele
        * setando no livro obtido os parametros de atualização
        * atualizando o livro obtido no banco de dados e se nao achar o livro pelo id vai retornar not Found */
       var uuid = UUID.fromString(id);

        return livroService.obterLivro(uuid).map(livro -> {
           Livro entidade = mapper.toEntity(dto);
           livro.setAutor(entidade.getAutor());
           livro.setGenero(entidade.getGenero());
           livro.setDataPublicacao(entidade.getDataPublicacao());
           livro.setIsbn(entidade.getIsbn());
           livro.setPreco(entidade.getPreco());
           livro.setTitulo(entidade.getTitulo());

           livroService.atualizar(livro);
           return ResponseEntity.noContent().build();
       }
       ).orElseGet( ()-> ResponseEntity.notFound().build());


    }


}
