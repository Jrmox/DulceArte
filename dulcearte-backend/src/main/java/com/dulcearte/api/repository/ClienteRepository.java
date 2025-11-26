package com.dulcearte.api.repository;

import com.dulcearte.api.model.Cliente; // Importa la Entidad
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interfaz como un componente de acceso a datos
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Hereda automáticamente: save(), findById(), findAll(), delete(), etc.

    // No se requiere código adicional para las operaciones básicas (CRUD).
}