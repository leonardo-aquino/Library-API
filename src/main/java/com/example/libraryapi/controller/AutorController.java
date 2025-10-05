package com.example.libraryapi.controller;

import com.example.libraryapi.Exeptions.ExitsAutorExeption;
import com.example.libraryapi.Exeptions.MetodoInvalidExeption;
import com.example.libraryapi.Service.AutorService;
import com.example.libraryapi.controller.dto.AutorDto;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.model.Autor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

 @Autowired
AutorService autorService;

    //Salvando um autor
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody AutorDto autorDto){
       Autor autor = autorDto.transferirDadosAutor();
       try{
           autorService.salvar(autor);
       //construindo a url http://localhost:8080/autores/:id para visualizar no header da request
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}").
                buildAndExpand(autor.getId())
                .toUri();

        return  ResponseEntity.created(location).build();
       }catch (ExitsAutorExeption e){

           var erroDto =  ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDto.status()).body(erroDto);
       }
    }

    // Obtendo dados do autor por Id
    @GetMapping("{id}")
    public ResponseEntity<AutorDto> obter(@PathVariable("id") String id){ // recebendo pela url o id do autor
        UUID Uuid  = UUID.fromString(id); // transfromando o id em UUID
       Optional<Autor> autor = autorService.obterPorId(Uuid); // recebendo um Optional
       if(autor.isPresent()){ // se estiver alguma coisa no autor então
           Autor entidade = autor.get(); // Autor entidade vai receber o que esta dentro do autor
           AutorDto dto = new AutorDto(entidade.getId(), // passando para o dto as informações que eu quero dar de resposta
                   entidade.getNome(),
                   entidade.getDataNascimento(),
                   entidade.getNacionalidade());
           return ResponseEntity.ok(dto);
       }
       return ResponseEntity.notFound().build(); // e se nao achar nada, vai retornar um notFound
    }

    // deletando um Autor por ID
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){ // recebendo na url o id do Autor
        try{
            var idAutor = UUID.fromString(id); // transformandoo ID em UUID
            Optional<Autor> autor = autorService.obterPorId(idAutor); // verificando se tem algum autor com o id
            if (autor.isPresent()){ // se tiver autor pelo ID então
                autorService.deletar(autor.get());// deleto o autor passando por parãmetro o ID dele
                return ResponseEntity.noContent().build(); // passando na response um codigo de 204(no Content)
            }else{ // se não estiver Autor com ID vairetornar um Not Found
                return ResponseEntity.notFound().build();
            }
        }catch (MetodoInvalidExeption e){

            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }


    }

    // retonar uma lista de DTOs  cujo os parâmetros são nome e nacionalidade ou nome ou nacionalidade
    // @required false = não é obrigatorio passar esses parâmetros
    @GetMapping
    public ResponseEntity <List<AutorDto>> pesquisar(@RequestParam (value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = autorService.pesquisa(nome,nacionalidade);
        List<AutorDto> lista = resultado.stream().map(autor -> new AutorDto(autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),      // pegando a strem de Autores que é a lista de resultado
                autor.getNacionalidade())       // transformando em uma strem de AutorDto
                ).collect(Collectors.toList()); // transformando a strem do Dto em uma lista
      return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody AutorDto dto){
        try{
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional= autorService.obterPorId(idAutor);
            if (autorOptional.isPresent()){
                var autor =autorOptional.get();         // recebendo pela url o id do autor e os dados que serão atualizados pelo DTO
                autor.setId(idAutor);                   // transformando o id em UUID
                autor.setNome(dto.nome());              // verificando se o id que foi passado corresponde com algum id no banco de dados
                autor.setDataNascimento(dto.dataNascimento());              //se estiver presente eu pego esse autor e seto nele os dados do DTO
                autor.setNacionalidade(dto.nacionalidade());                // atualizo ele na base com o método atualizar eretorno um 204
                autorService.atualizar(autor);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();               // caso nao ache o autor, retorno um Not Found
        }catch (ExitsAutorExeption e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);

        }
    }

}
