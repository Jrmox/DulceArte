package com.dulcearte.api.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedidoRequestDTO {
    // Solo necesitamos el ID del producto y la cantidad/precio
    private Integer idTipoProducto; // (FK a TipoProducto)
    private Integer cantidad;
    private BigDecimal precioUnitario;
    // ... otros campos del detalle si se envían ...
}
