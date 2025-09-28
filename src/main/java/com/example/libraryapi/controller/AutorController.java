package com.example.libraryapi.controller;

import com.example.libraryapi.Service.AutorService;
import com.example.libraryapi.controller.dto.AutorDto;
import com.example.libraryapi.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

 @Autowired
AutorService autorService;

    //Salvando um autor
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDto autorDto){
       Autor autor = autorDto.transferirDadosAutor();
       autorService.salvar(autor);

       //construindo a url http://localhost:8080/autores/:id para visualizar no header da request
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}").
                buildAndExpand(autor.getId())
                .toUri();

        return  ResponseEntity.created(location).build();
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
    public ResponseEntity<String> deletar(@PathVariable("id") String id){ // recebendo na url o id do Autor
        var idAutor = UUID.fromString(id); // transformandoo ID em UUID
        Optional<Autor> autor = autorService.obterPorId(idAutor); // verificando se tem algum autor com o id
        if (autor.isPresent()){ // se tiver autor pelo ID então
            autorService.deletar(idAutor);// deleto o autor passando por parãmetro o ID dele
            return ResponseEntity.noContent().build(); // passando na response um codigo de 204(no Content)
        }else{ // se não estiver Autor com ID vairetornar um Not Found
            return ResponseEntity.notFound().build();
        }

    }




}
