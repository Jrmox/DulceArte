package com.dulcearte.api.repository;

import com.dulcearte.api.model.TipoPago; // Entidad del Catálogo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Integer> {
    
    // Hereda todas las funcionalidades CRUD básicas
}