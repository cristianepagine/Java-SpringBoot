package com.cristiane.helpdesk.services;

import com.cristiane.helpdesk.domain.Chamado;
import com.cristiane.helpdesk.domain.Cliente;
import com.cristiane.helpdesk.domain.Tecnico;
import com.cristiane.helpdesk.domain.dtos.ChamadoDTO;
import com.cristiane.helpdesk.domain.enums.Prioridade;
import com.cristiane.helpdesk.domain.enums.Status;
import com.cristiane.helpdesk.repositories.ChamadoRepository;
import com.cristiane.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = chamadoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrato! ID: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO obj) {
        return chamadoRepository.save(newChamado(obj));
    }
    public Chamado update(Integer id, ChamadoDTO objDTO) {
        //garante que o ID existe
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        //se existir recebe as informações atualizadas
        oldObj = newChamado(objDTO);
        return chamadoRepository.save(oldObj);
    }

    public Chamado newChamado(ChamadoDTO obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        if(obj.getId() != null) {
            chamado.setId(obj.getId());
        }

        //se status for 2 inclui data de fechamento no chamado
        if(obj.getStatus().equals(2))
        {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }


}
