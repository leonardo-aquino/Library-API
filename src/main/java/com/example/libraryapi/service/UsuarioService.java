package com.example.libraryapi.service;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.UsuarioRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder; // usado para criptografar a senha e para ver se a senha digitada esta batendo com o hash

    public void salvar (Usuario usuario){
       Optional <Usuario> usuario1 = repository.findByLogin(usuario.getLogin());
       if (usuario1.isPresent()) {
           if (usuario1.get().getLogin().equals(usuario.getLogin()) && usuario1.get().getRoles().equals(usuario.getRoles())) {
               throw new DuplicateRequestException("Ja existe um Login ");
           }
       }
        var senha = usuario.getSenha();
       usuario.setSenha(encoder.encode(senha));
       repository.save(usuario);
    }

    public Optional<Usuario> obterPorLogin(String login){
       Optional <Usuario> usuario = repository.findByLogin(login);
       if (usuario.isPresent()) return usuario;
       return null;
    }
}
