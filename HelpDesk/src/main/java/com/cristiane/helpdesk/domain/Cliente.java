package com.cristiane.helpdesk.domain;

import com.cristiane.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Cliente extends Pessoa {
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente(Integer id, String nome, String CPF, String email, String senha) {
        super(id, nome, CPF, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente() {
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
