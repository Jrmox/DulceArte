package com.dulcearte.api.repository;

import com.dulcearte.api.model.EstadoPedido; // Entidad del Catálogo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
    
    // Hereda todas las funcionalidades CRUD básicas (findAll, findById, etc.)
}