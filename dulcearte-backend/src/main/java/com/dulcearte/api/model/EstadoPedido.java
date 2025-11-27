package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cat_estados_pedido", schema = "catalogos") // Mapeo al esquema 'catalogos'
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre_estado", unique = true, nullable = false, length = 50)
    private String nombreEstado;
}
