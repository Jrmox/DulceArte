package com.dulcearte.api.controller;

import com.dulcearte.api.model.Cliente; // Importa la Entidad (Paquete 'model')
import com.dulcearte.api.service.ClienteService; // Importa el Servicio (Paquete 'service')
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 1. Indica que esta clase maneja peticiones REST
@RequestMapping("/api/clientes") // 2. Mapeo base para todos los endpoints (Ej: http://localhost:8080/api/clientes)
public class ClienteController {

    private final ClienteService clienteService;

    // Inyección del servicio
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // =========================================================
    // ENDPOINT POST: CREAR CLIENTE
    // Mapea peticiones POST a http://localhost:8080/api/clientes
    // =========================================================
    @PostMapping 
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Llama al servicio para ejecutar la lógica de negocio y guardar en BD
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        
        // Retorna la entidad creada y el código HTTP 201 (CREATED)
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }
    
    // =========================================================
    // ENDPOINT GET: OBTENER TODOS LOS CLIENTES
    // Mapea peticiones GET a http://localhost:8080/api/clientes
    // =========================================================
    @GetMapping 
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        
        // Retorna la lista de clientes y el código HTTP 200 (OK)
        return ResponseEntity.ok(clientes); 
    }
}