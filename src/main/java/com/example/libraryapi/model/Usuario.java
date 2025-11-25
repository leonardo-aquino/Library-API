package com.example.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", length = 20,nullable = false,unique = true)
    private String login;

    @Column(name = "senha", length = 300)
    private String senha;

    @Column(name = "roles", columnDefinition = "varchar[]")
    @Type(ListArrayType.class)
    private List<String> roles;

    // para mapear o tipo List para Array no bd devemos baixar a biblioteca hypersistence
    // e mapear dessa forma com ListArrayType.class para tranformar a List para Array
}
