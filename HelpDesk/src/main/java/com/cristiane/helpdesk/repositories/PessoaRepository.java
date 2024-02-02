package com.cristiane.helpdesk.repositories;

import com.cristiane.helpdesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Optional<Pessoa> findBycpf(String cpf);
    Optional<Pessoa> findByEmail(String email);
}
