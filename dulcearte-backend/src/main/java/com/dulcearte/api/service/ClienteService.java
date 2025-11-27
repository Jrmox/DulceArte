package com.dulcearte.api.service;

import com.dulcearte.api.model.Cliente;
import com.dulcearte.api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Inyección de Dependencias por Constructor (La forma recomendada)
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // =========================================================
    // 1. OBTENER TODOS LOS CLIENTES (GET /api/clientes)
    // =========================================================
    public List<Cliente> obtenerTodos() {
    // Usamos el método personalizado que solo trae los activos
        return clienteRepository.findByActivoTrue(); // ¡CAMBIAR AQUÍ!
    }

    // =========================================================
    // 2. OBTENER CLIENTE POR ID (GET /api/clientes/{id})
    // =========================================================
    public Optional<Cliente> obtenerPorId(Integer id) {
        return clienteRepository.findById(id);
    }
    
    // =========================================================
    // 3. GUARDAR/CREAR CLIENTE (POST /api/clientes)
    // =========================================================
    public Cliente guardarCliente(Cliente cliente) {
        // Lógica de Negocio: Aseguramos que la fecha de registro y el estado inicial
        // se establezcan al momento de la creación, antes de guardarlo.
        if (cliente.getFechaRegistro() == null) {
            cliente.setFechaRegistro(LocalDateTime.now());
            cliente.setActivo(true); 
        }
        
        return clienteRepository.save(cliente); 
    }

    // =========================================================
    // 4. ACTUALIZAR CLIENTE (PUT /api/clientes/{id})
    // =========================================================
    public Cliente actualizarCliente(Integer id, Cliente detallesCliente) {
        // 1. Buscamos el cliente existente o lanzamos una excepción
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id)); 
        
        // 2. Aplicamos solo los cambios:
        // Verificamos si los campos vienen con un valor diferente de nulo en los detalles
        if (detallesCliente.getNombreCompleto() != null) {
            clienteExistente.setNombreCompleto(detallesCliente.getNombreCompleto());
        }
        if (detallesCliente.getDireccion() != null) {
            clienteExistente.setDireccion(detallesCliente.getDireccion());
        }
        if (detallesCliente.getTelefono() != null) {
            clienteExistente.setTelefono(detallesCliente.getTelefono());
        }
        if (detallesCliente.getCorreoElectronico() != null) {
            clienteExistente.setCorreoElectronico(detallesCliente.getCorreoElectronico());
        }
        if (detallesCliente.getObservaciones() != null) {
            clienteExistente.setObservaciones(detallesCliente.getObservaciones());
        }
        
        // 3. Guardamos y JPA realiza la operación UPDATE
        return clienteRepository.save(clienteExistente);
    }
    
    // =========================================================
    // 5. ELIMINACIÓN LÓGICA (DELETE /api/clientes/{id})
    // =========================================================
    public void eliminarClienteLogico(Integer id) {
        // 1. Buscamos el cliente existente
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id)); 
        
        // 2. Realizamos la eliminación lógica: cambiamos 'activo' a false
        if (clienteExistente.getActivo()) {
            clienteExistente.setActivo(false); 
            clienteRepository.save(clienteExistente);
        }
    }
}