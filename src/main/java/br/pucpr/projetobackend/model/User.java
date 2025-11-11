package br.pucpr.projetobackend.model;

import jakarta.persistence.*;
import lombok.Data;

import br.pucpr.projetobackend.security.Role;

@Data
@Entity
@Table(name = "TAB_USUARIO")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "ROLE")
    private Role role;

}
