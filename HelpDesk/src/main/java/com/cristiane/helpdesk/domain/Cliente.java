package com.cristiane.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
private List<Chamado> chamados = new ArrayList<>();

    public Cliente(Integer id, String nome, String CPF, String email, String senha) {
        super(id, nome, CPF, email, senha);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
