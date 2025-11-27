package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal; // Importamos para manejar montos monetarios
import java.util.List;

@Entity
@Table(name = "pedidos", schema = "tablas") // Mapeo a tablas.pedidos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    // =========================================================
    // RELACIONES DE MUCHOS A UNO (Foreign Keys)
    // =========================================================
    
    // 1. Relación con Cliente: Muchos Pedidos pertenecen a un Cliente.
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading es eficiente para relaciones ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false) // Columna de la llave foránea
    private Cliente cliente; 

    // 2. Relación con EstadoPedido: Muchos Pedidos tienen un Estado.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false) // Columna de la llave foránea
    private EstadoPedido estado;

    // =========================================================
    // CAMPOS PRINCIPALES DEL PEDIDO
    // =========================================================
    
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud; // Solo fecha

    @Column(name = "fecha_entrega_programada", nullable = false)
    private LocalDateTime fechaEntregaProgramada;

    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal; // Para manejar dinero (10 dígitos, 2 decimales)

    @Column(name = "resultado_entrega")
    private String resultadoEntrega;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    // =========================================================
    // RELACIONES DE UNO A MUCHOS (Datos relacionados)
    // Estos campos NO son columnas en la tabla 'pedidos'.
    // Son para que Java acceda a los detalles.
    // =========================================================

    // 1. Relación con DetallePedido (Productos dentro del pedido)
    // MappedBy indica el campo en la entidad DetallePedido que maneja la FK.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
    
    // 2. Relación con Pago
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;
}