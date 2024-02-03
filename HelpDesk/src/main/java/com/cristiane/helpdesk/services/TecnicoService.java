package com.cristiane.helpdesk.services;

import com.cristiane.helpdesk.domain.Pessoa;
import com.cristiane.helpdesk.domain.Tecnico;
import com.cristiane.helpdesk.domain.dtos.TecnicoDTO;
import com.cristiane.helpdesk.repositories.PessoaRepository;
import com.cristiane.helpdesk.repositories.TecnicoRepository;
import com.cristiane.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.cristiane.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrato! ID: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);

        // Verificar se o CPF está preenchido antes de criar a entidade
        if (objDTO.getCpf() == null || objDTO.getCpf().isEmpty()) {
            throw new DataIntegrityViolationException("CPF não pode ser nulo ou vazio");
        }
        validaPorCpfEEmail(objDTO);

        Tecnico newObj = new Tecnico(objDTO);
        return tecnicoRepository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        //usa o findbyid para ver se existe e se nao existir lança a excessao que ja existe no findbyid
        Tecnico oldObj = findById(id);
        //chama a validação de cpf e email
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Existe chamado vinculado ao tecnico, não é possivel excluir");

        } else
            tecnicoRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        // Validar CPF
        Optional<Pessoa> objCpf = pessoaRepository.findBycpf(objDTO.getCpf());
        if (objCpf.isPresent() && !objCpf.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        // Validar E-mail
        Optional<Pessoa> objEmail = pessoaRepository.findByEmail(objDTO.getEmail());
        if (objEmail.isPresent() && !objEmail.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }


}
