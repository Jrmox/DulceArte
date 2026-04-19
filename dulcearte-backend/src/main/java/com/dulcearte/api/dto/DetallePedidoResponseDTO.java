package com.dulcearte.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedidoResponseDTO {
    
    private Integer idDetalle;
    private Integer idTipoProducto; // Solo el ID del producto
    private String nombreTipoProducto; // Útil para mostrar al usuario
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal; // Se puede calcular o incluir si está en la entidad
    private String observacionesDetalle;
}
