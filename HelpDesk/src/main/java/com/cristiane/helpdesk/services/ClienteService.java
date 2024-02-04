package com.cristiane.helpdesk.services;

import com.cristiane.helpdesk.domain.Cliente;
import com.cristiane.helpdesk.domain.Pessoa;
import com.cristiane.helpdesk.domain.dtos.ClienteDTO;
import com.cristiane.helpdesk.repositories.ClienteRepository;
import com.cristiane.helpdesk.repositories.PessoaRepository;
import com.cristiane.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.cristiane.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrato! ID: " + id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);

        // Verificar se o CPF está preenchido antes de criar a entidade
        if (objDTO.getCpf() == null || objDTO.getCpf().isEmpty()) {
            throw new DataIntegrityViolationException("CPF não pode ser nulo ou vazio");
        }
        validaPorCpfEEmail(objDTO);

        Cliente newObj = new Cliente(objDTO);
        return clienteRepository.save(newObj);
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        //usa o findbyid para ver se existe e se nao existir lança a excessao que ja existe no findbyid
        Cliente oldObj = findById(id);
        //chama a validação de cpf e email
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return clienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (!obj.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Existe chamado vinculado ao cliente, não é possivel excluir");

        } else
            clienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
