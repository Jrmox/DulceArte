package com.dulcearte.api.repository;

import com.dulcearte.api.model.TipoProducto; // Entidad del Catálogo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {
    
    // Hereda todas las funcionalidades CRUD básicas
}
