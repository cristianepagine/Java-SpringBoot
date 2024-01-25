package com.cristiane.helpdesk.services;

import com.cristiane.helpdesk.domain.Chamado;
import com.cristiane.helpdesk.domain.Cliente;
import com.cristiane.helpdesk.domain.Tecnico;
import com.cristiane.helpdesk.domain.enums.Perfil;
import com.cristiane.helpdesk.domain.enums.Prioridade;
import com.cristiane.helpdesk.domain.enums.Status;
import com.cristiane.helpdesk.repositories.ChamadoRepository;
import com.cristiane.helpdesk.repositories.ClienteRepository;
import com.cristiane.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    public void instanciaDB(){
        Tecnico tec1 = new Tecnico(null, "Cristiane Pagine", "06454000923", "cristianepagine@gmail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);
        Cliente cli1 = new Cliente(null, "Josias Junior", "03801150933", "cesarjunior@gmail.com", "123");
        cli1.addPerfil(Perfil.CLIENTE);
        Chamado cham1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(cham1));
    }
}
