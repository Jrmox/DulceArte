package com.dulcearte.api.repository;

import com.dulcearte.api.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    
    // Ejemplo: Buscar todos los detalles de un pedido
    List<DetallePedido> findByPedidoIdPedido(Integer idPedido);
    
}
