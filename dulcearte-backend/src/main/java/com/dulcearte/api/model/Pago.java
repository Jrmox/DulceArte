package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "pagos", schema = "tablas") // Mapeo a tablas.pagos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    // =========================================================
    // RELACIONES DE MUCHOS A UNO (Foreign Keys)
    // =========================================================

    // 1. Relación con Pedido: Muchos Pagos pertenecen a un Pedido.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    // 2. Relación con TipoPago (El método usado: Efectivo, Tarjeta, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pago", nullable = false)
    private TipoPago tipoPago;

    // =========================================================
    // CAMPOS DE PAGO
    // =========================================================

    @Column(name = "monto_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "referencia_pago", length = 100)
    private String referenciaPago; // Por si es una transferencia o voucher

    @Column(name = "activo", nullable = false)
    private Boolean activo = true; // Para borrado lógico de pagos
}
