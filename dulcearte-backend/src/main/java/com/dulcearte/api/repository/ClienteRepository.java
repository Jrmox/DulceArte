package com.dulcearte.api.repository;

import com.dulcearte.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // JpaRepository entiende que debe buscar clientes donde el campo 'activo' sea TRUE
    // Este método reemplazará a findAll() en tu servicio para obtener solo activos.
    List<Cliente> findByActivoTrue();
}