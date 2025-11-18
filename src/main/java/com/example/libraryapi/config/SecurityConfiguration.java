package com.example.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity //ativa o Spring Security na aplicação
public class SecurityConfiguration {

//     Esse mét0do registra um bean chamado SecurityFilterChain,
//     que define como a segurança será aplicada.
//     O parâmetro HttpSecurity permite configurar autenticação, autorização, CSRF, login, etc.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) //CSRF (Cross-Site Request Forgery) é uma proteção usada em aplicações web, Aqui está sendo desabilitada, geralmente porque APIs REST não precisamos dela em API REST
                .formLogin(Customizer.withDefaults()) // Habilita a autenticação via formulário HTML padrão do Spring Security.
                .httpBasic(Customizer.withDefaults()) //Permite autenticação via cabeçalho HTTP (usado em APIs).
                .authorizeHttpRequests( authoriza -> {
                    authoriza.anyRequest().authenticated(); // 	Define que todas as requisições precisam estar autenticadas, então qualquer endpoint da API só será acessível por usuários logados

                })
                .build(); // Constrói e retorna o objeto SecurityFilterChain
        //oi
    }
}
