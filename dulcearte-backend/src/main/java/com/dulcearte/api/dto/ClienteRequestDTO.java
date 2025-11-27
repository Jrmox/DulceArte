package com.dulcearte.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    // Omitimos el idCliente, pues es generado por la BD.
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String observaciones;
    // Omitimos fechaRegistro y activo, ya que la lógica del Service los establece.
}