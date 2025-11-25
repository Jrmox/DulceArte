package com.dulcearte.api.repository;

import com.dulcearte.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Aquí no se necesita código para la inserción, JpaRepository lo proporciona.
}