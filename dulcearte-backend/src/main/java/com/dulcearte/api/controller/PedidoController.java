package com.dulcearte.api.controller;

import com.dulcearte.api.service.PedidoService;
import com.dulcearte.api.model.Pedido;
import com.dulcearte.api.model.Cliente;
import com.dulcearte.api.model.EstadoPedido;
import com.dulcearte.api.model.TipoProducto;
import com.dulcearte.api.model.DetallePedido;

import com.dulcearte.api.dto.PedidoRequestDTO;
import com.dulcearte.api.dto.PedidoResponseDTO;
import com.dulcearte.api.dto.DetallePedidoResponseDTO;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ModelMapper modelMapper;

    // Inyección de Dependencias
    public PedidoController(PedidoService pedidoService, ModelMapper modelMapper) {
        this.pedidoService = pedidoService;
        this.modelMapper = modelMapper;
    }

    // =========================================================
    // 1. POST: CREAR PEDIDO COMPLETO (Recibe DTO de Request)
    // URL: POST http://localhost:8080/api/pedidos
    // =========================================================
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        
        // 1. Convertir DTO de entrada a la Entidad Pedido (Lógica de Mapeo)
        Pedido pedido = mapearDtoAEntidad(pedidoRequestDTO);
        
        // 2. Guardar en el Service (Manejo Transaccional)
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);

        // 3. Mapeo final: Entidad guardada -> DTO de Response para la salida
        PedidoResponseDTO responseDTO = mapearEntidadADto(nuevoPedido);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED); // 201 CREATED
    }

    // =========================================================
    // 2. GET: OBTENER TODOS LOS PEDIDOS (Devuelve Lista de ResponseDTO)
    // URL: GET http://localhost:8080/api/pedidos
    // =========================================================
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodosLosPedidos() {
        // Obtener la lista de Entidades del Service
        List<Pedido> pedidos = pedidoService.obtenerTodos();

        // Mapear la lista de Entidades a la lista de DTOs de Respuesta
        List<PedidoResponseDTO> responseDTOs = pedidos.stream()
            .map(this::mapearEntidadADto) // Usamos el mismo método de mapeo de salida
            .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs); // 200 OK
    }
    
    // =========================================================
    // MÉTODOS AUXILIARES DE MAPEO (Para la conversión DTO/Entidad)
    // =========================================================

    /** * Mapea un PedidoRequestDTO (entrada) a la Entidad Pedido JPA.
     * Crea referencias a las entidades relacionadas (FKs) usando solo el ID.
     */
    private Pedido mapearDtoAEntidad(PedidoRequestDTO dto) {
        // Mapea los campos simples
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        
        // Configurar FKs que solo vienen como ID en el DTO 
        // Se asume la existencia de constructores con ID (ej: new Cliente(id))
        pedido.setCliente(new Cliente(dto.getIdCliente())); 
        pedido.setEstado(new EstadoPedido(dto.getIdEstado()));
        
        // Configurar Fecha de Entrega (String a LocalDateTime)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        pedido.setFechaEntregaProgramada(LocalDateTime.parse(dto.getFechaEntregaProgramada(), formatter));
        
        // Configurar Detalles (mapeo anidado)
        if (dto.getDetalles() != null) {
             pedido.setDetalles(dto.getDetalles().stream().map(detalleDTO -> {
                // Mapeo de DetallePedidoRequestDTO a Entidad DetallePedido
                DetallePedido detalle = modelMapper.map(detalleDTO, DetallePedido.class);
                
                // Asignar la FK TipoProducto
                detalle.setTipoProducto(new TipoProducto(detalleDTO.getIdTipoProducto())); 
                
                return detalle;
            }).collect(Collectors.toList()));
        } else {
            pedido.setDetalles(List.of()); 
        }
       
        return pedido;
    }

    /**
     * Mapea la Entidad Pedido (salida del Service) al PedidoResponseDTO.
     * Rompe la recursión y popula campos como nombres de cliente/estado.
     */
    private PedidoResponseDTO mapearEntidadADto(Pedido pedido) {
        // Mapea los campos simples.
        PedidoResponseDTO dto = modelMapper.map(pedido, PedidoResponseDTO.class);

        // Poblar campos de nombre de las FKs (para mostrar al usuario)
        // Esto asume que el Service o JPA ha cargado estas referencias.
        if (pedido.getCliente() != null) {
            dto.setIdCliente(pedido.getCliente().getIdCliente());
            dto.setNombreCliente(pedido.getCliente().getNombreCompleto()); 
        }

        if (pedido.getEstado() != null) {
            dto.setIdEstado(pedido.getEstado().getIdEstado());
            dto.setNombreEstado(pedido.getEstado().getNombreEstado());
        }
        
        // Mapear los detalles del pedido a DetallePedidoResponseDTO
        if (pedido.getDetalles() != null) {
             dto.setDetalles(pedido.getDetalles().stream()
                .map(detalle -> {
                    // Mapeamos DetallePedido a su DTO de respuesta
                    return modelMapper.map(detalle, DetallePedidoResponseDTO.class);
                })
                .collect(Collectors.toList()));
        }

        return dto;
    }
}