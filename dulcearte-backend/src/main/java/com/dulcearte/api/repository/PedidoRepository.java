package com.dulcearte.api.repository;

import com.dulcearte.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    // Ejemplo: Buscar pedidos por cliente ID
    List<Pedido> findByClienteIdCliente(Integer idCliente);
    
}