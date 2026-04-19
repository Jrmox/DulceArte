package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido", schema = "tablas") // Mapeo a tablas.detalles_pedido
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    // =========================================================
    // RELACIONES DE MUCHOS A UNO (Foreign Keys)
    // =========================================================

    // 1. Relación con Pedido: Muchos Detalles pertenecen a un Pedido.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido; 

    // 2. Relación con TipoProducto (El producto específico que se compró)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_producto", nullable = false)
    private TipoProducto tipoProducto; 

    // =========================================================
    // CAMPOS DE DETALLE
    // =========================================================

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "observaciones_detalle", length = 500)
    private String observacionesDetalle;
}