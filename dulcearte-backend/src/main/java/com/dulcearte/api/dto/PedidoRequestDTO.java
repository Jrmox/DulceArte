package com.dulcearte.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {

    private Integer idCliente; // ID del cliente que hace el pedido
    private Integer idEstado;  // ID del estado inicial (ej: PENDIENTE=1)
    private String fechaEntregaProgramada; // Recibimos la fecha como String
    
    // Lista de detalles: La lista de productos en el pedido
    private List<DetallePedidoRequestDTO> detalles; 
    
    // Lista de pagos: El primer pago/abono
    // Podrías necesitar otro DTO para Pago aquí, pero simplificaremos por ahora.
}
