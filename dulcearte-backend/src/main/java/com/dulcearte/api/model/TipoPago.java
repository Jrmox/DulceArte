package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cat_tipos_pago", schema = "catalogos") // Mapeo a catalogos.cat_tipos_pago
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pago")
    private Integer idTipoPago;

    @Column(name = "nombre_pago", unique = true, nullable = false, length = 50)
    private String nombrePago;
}