package com.dulcearte.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoResponseDTO {

    private Integer idPedido;
    private Integer idCliente; // Solo el ID del cliente
    private String nombreCliente; // Para mostrar en la UI
    private Integer idEstado; // Solo el ID del estado
    private String nombreEstado; // Para mostrar si está PENDIENTE, ENTREGADO, etc.

    private LocalDate fechaSolicitud;
    private LocalDateTime fechaEntregaProgramada;
    private LocalDateTime fechaEntregaReal;
    private BigDecimal montoTotal;
    private String resultadoEntrega;
    private LocalDateTime fechaRegistro;

    // Aquí incluimos la lista de DTOs de Detalle, NO las Entidades JPA.
    private List<DetallePedidoResponseDTO> detalles; 
}