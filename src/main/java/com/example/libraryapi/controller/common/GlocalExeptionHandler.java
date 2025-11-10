package com.example.libraryapi.controller.common;
import com.example.libraryapi.exeptions.CampoInvalidExeption;
import com.example.libraryapi.exeptions.ExitIsbnExeption;
import com.example.libraryapi.exeptions.ExitsAutorExeption;
import com.example.libraryapi.exeptions.MetodoInvalidExeption;
import com.example.libraryapi.controller.dto.ErroCampo;
import com.example.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice  //objetivo dessa classe é capturar exeptions e dar uma resposta de erro como retorno
public class GlocalExeptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // capturando em todos controllers essa exceção
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // usado para Status do retorno
    public ErroResposta metodoInvalidExeption(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors(); // método para retornar quais campos deu erro na anotação valid no DTO
        List<ErroCampo> listaErros = fieldErrors
                .stream() // mapeando a lista fieldErros para a lista do tipo ErroCampo
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",listaErros);

    }

    @ExceptionHandler(ExitsAutorExeption.class)
    public ResponseEntity<Object>exitsAutorExeption(ExitsAutorExeption e){

        ErroResposta erroDto =  ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDto.status()).body(erroDto);
    }

    @ExceptionHandler(MetodoInvalidExeption.class)
    public ResponseEntity<Object> metodoInvalidExeption(MetodoInvalidExeption e){
        ErroResposta erroDto = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDto.status()).body(erroDto);
    }

    @ExceptionHandler(ExitIsbnExeption.class)
    public ResponseEntity<Object> isbnExistente(ExitIsbnExeption e){

        ErroResposta erroDto =  ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDto.status()).body(erroDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> erroNaoTratado(RuntimeException e){
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Entre em contato com o Servidor");
    }



    @ExceptionHandler(CampoInvalidExeption.class)
    public ResponseEntity<Object> PrecoExeption( CampoInvalidExeption e){
        ErroResposta erroDto = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                List.of(new ErroCampo(e.getCampo(),"preço está null")));
       return ResponseEntity.status(erroDto.status()).body(erroDto);
    }


}
