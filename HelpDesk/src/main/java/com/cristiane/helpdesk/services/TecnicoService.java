package com.cristiane.helpdesk.services;

import com.cristiane.helpdesk.domain.Tecnico;
import com.cristiane.helpdesk.repositories.TecnicoRepository;
import com.cristiane.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrato! ID: " + id));
    }
}
