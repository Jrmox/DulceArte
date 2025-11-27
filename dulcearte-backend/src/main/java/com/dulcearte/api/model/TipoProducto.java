package com.dulcearte.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cat_tipos_producto", schema = "catalogos") // Mapeo a catalogos.cat_tipos_producto
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto")
    private Integer idTipoProducto;

    @Column(name = "nombre_tipo", unique = true, nullable = false, length = 50)
    private String nombreTipo;
}
