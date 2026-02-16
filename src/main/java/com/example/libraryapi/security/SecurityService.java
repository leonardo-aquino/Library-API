package com.example.libraryapi.security;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityService {

    private final UsuarioService usuarioService;

    public SecurityService(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    public Usuario obterUsuarioPorLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
       Optional<Usuario> usuarioLogado =usuarioService.obterPorLogin(userDetails.getUsername());
       if (usuarioLogado.isPresent()){
           return usuarioLogado.get();
       }
       else return null;

    }

}
