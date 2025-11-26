package com.dulcearte.api.service;

import com.dulcearte.api.model.Cliente;
import com.dulcearte.api.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ClienteService {

    // Spring inyecta automáticamente la implementación de la interfaz Repository
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // 1. Método para obtener todos los clientes (GET)
    public List<Cliente> obtenerTodos() {
        // En una etapa avanzada, podríamos filtrar solo los clientes activos (activo=true)
        return clienteRepository.findAll();
    }

    // 2. Método para guardar/actualizar un cliente (POST/PUT)
    public Cliente guardarCliente(Cliente cliente) {
        // Lógica de negocio: asegura que la fecha de registro se establezca en la creación
        if (cliente.getFechaRegistro() == null) {
            cliente.setFechaRegistro(LocalDateTime.now());
            cliente.setActivo(true); 
        }
        
        return clienteRepository.save(cliente); 
    }
}