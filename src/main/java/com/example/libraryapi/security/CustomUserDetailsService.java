package com.example.libraryapi.security;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioService.
                obterPorLogin(login);

        if (usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User
                .builder()
                .username(usuario.get().getLogin())
                .password(usuario.get().getSenha())
                .roles(usuario.get().getRoles().toArray(new String[usuario.get().getRoles().size()]))
                .build();
    }
}
