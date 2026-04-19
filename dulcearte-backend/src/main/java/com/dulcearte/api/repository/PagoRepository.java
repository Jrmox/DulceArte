package com.dulcearte.api.repository;

import com.dulcearte.api.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    // Para implementar el borrado lógico en pagos
    List<Pago> findByActivoTrue();
}
