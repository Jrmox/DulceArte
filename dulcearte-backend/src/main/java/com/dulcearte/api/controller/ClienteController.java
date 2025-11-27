package com.dulcearte.api.controller;

import com.dulcearte.api.service.ClienteService;
import com.dulcearte.api.model.Cliente;
import com.dulcearte.api.dto.ClienteRequestDTO;
import com.dulcearte.api.dto.ClienteResponseDTO;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ModelMapper modelMapper;

    // Inyección de Dependencias por Constructor (Spring inyecta automáticamente)
    public ClienteController(ClienteService clienteService, ModelMapper modelMapper) {
        this.clienteService = clienteService;
        this.modelMapper = modelMapper;
    }

    // =========================================================
    // 1. POST: CREAR CLIENTE (Recibe RequestDTO, Devuelve ResponseDTO)
    // URL: POST http://localhost:8080/api/clientes
    // =========================================================
    @PostMapping 
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        // Mapeo: DTO (entrada) -> Entidad (persistencia)
        Cliente cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
        
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        
        // Mapeo: Entidad (guardada) -> DTO (salida)
        ClienteResponseDTO responseDTO = modelMapper.map(nuevoCliente, ClienteResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED); // 201 CREATED
    }
    
    // =========================================================
    // 2. GET: OBTENER TODOS LOS CLIENTES (Devuelve Lista de ResponseDTO)
    // URL: GET http://localhost:8080/api/clientes
    // =========================================================
    @GetMapping 
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        
        // Mapeo: Lista de Entidades -> Lista de DTOs de Respuesta (usando Stream)
        List<ClienteResponseDTO> responseDTOs = clientes.stream()
            .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(responseDTOs); // 200 OK
    }
    
    // =========================================================
    // 3. GET: OBTENER POR ID (Devuelve ResponseDTO)
    // URL: GET http://localhost:8080/api/clientes/{id}
    // =========================================================
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.obtenerPorId(id)
            .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class)) // Mapeo a DTO si existe
            .map(ResponseEntity::ok) // 200 OK
            .orElse(ResponseEntity.notFound().build()); // 404 NOT FOUND
    }

    // =========================================================
    // 4. PUT: ACTUALIZAR CLIENTE (Recibe RequestDTO)
    // URL: PUT http://localhost:8080/api/clientes/{id}
    // =========================================================
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
        try {
            // Mapeo: DTO de Request -> Entidad (para pasar solo los detalles de actualización)
            Cliente clienteDetalles = modelMapper.map(clienteRequestDTO, Cliente.class);
            
            Cliente clienteActualizado = clienteService.actualizarCliente(id, clienteDetalles);
            
            // Mapeo: Entidad actualizada -> DTO de Response
            ClienteResponseDTO responseDTO = modelMapper.map(clienteActualizado, ClienteResponseDTO.class);
            
            return ResponseEntity.ok(responseDTO); // 200 OK
        } catch (RuntimeException ex) {
            // Manejo de cliente no encontrado
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }
    }
    
    // =========================================================
    // 5. DELETE: ELIMINACIÓN LÓGICA (Soft Delete)
    // URL: DELETE http://localhost:8080/api/clientes/{id}
    // =========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        try {
            clienteService.eliminarClienteLogico(id);
            
            return ResponseEntity.noContent().build(); // 204 NO CONTENT (Éxito sin contenido de retorno)
        } catch (RuntimeException ex) {
            // Manejo de cliente no encontrado
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }
    }
}