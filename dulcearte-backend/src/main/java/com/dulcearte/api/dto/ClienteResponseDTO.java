package com.dulcearte.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Usamos @Data para getters, setters, toString, etc.
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class ClienteResponseDTO {

    private Integer idCliente;
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String observaciones;
    // NOTA: Omitimos campos como fechaRegistro y activo para simplificar la respuesta.
}