package com.cristiane.helpdesk.repositories;

import com.cristiane.helpdesk.domain.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository <Chamado, Integer> {
}
