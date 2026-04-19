package com.dulcearte.api.service;

import com.dulcearte.api.model.Pedido;
import com.dulcearte.api.model.DetallePedido;
import com.dulcearte.api.model.Pago;
import com.dulcearte.api.repository.PedidoRepository;
import com.dulcearte.api.repository.DetallePedidoRepository;
import com.dulcearte.api.repository.PagoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PagoRepository pagoRepository;

    // Inyección de dependencias para todos los repositorios
    public PedidoService(
        PedidoRepository pedidoRepository,
        DetallePedidoRepository detallePedidoRepository,
        PagoRepository pagoRepository) {
        
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.pagoRepository = pagoRepository;
    }

    // =========================================================
    // 1. CREAR PEDIDO COMPLETO (Transaccional)
    // =========================================================
    @Transactional // Garantiza que si un detalle o pago falla, todo el pedido se revierte.
    public Pedido guardarPedido(Pedido nuevoPedido) {
        
        // Lógica 1: Establecer fechas/montos en el Backend
        if (nuevoPedido.getFechaRegistro() == null) {
            nuevoPedido.setFechaRegistro(LocalDateTime.now());
        }

        // Lógica 2: Calcular el monto total (suma de detalles)
        BigDecimal montoCalculado = calcularMontoTotal(nuevoPedido.getDetalles());
        nuevoPedido.setMontoTotal(montoCalculado);

        // 1. Guardar el Pedido principal para obtener su ID
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        // 2. Guardar los Detalles del Pedido
        if (nuevoPedido.getDetalles() != null) {
            for (DetallePedido detalle : nuevoPedido.getDetalles()) {
                detalle.setPedido(pedidoGuardado); // Asignamos la referencia (FK)
                detallePedidoRepository.save(detalle);
            }
        }
        
        // 3. Guardar los Pagos asociados al Pedido
        if (nuevoPedido.getPagos() != null) {
             for (Pago pago : nuevoPedido.getPagos()) {
                pago.setPedido(pedidoGuardado); // Asignamos la referencia (FK)
                pago.setFechaPago(LocalDateTime.now()); // Aseguramos la fecha del pago
                pagoRepository.save(pago);
            }
        }
        
        // Devolvemos el pedido completo
        return pedidoGuardado;
    }

    // =========================================================
    // 2. OBTENER TODOS LOS PEDIDOS (GET)
    // =========================================================
    public List<Pedido> obtenerTodos() {
        // NOTA: No implementamos el filtro de borrado lógico aquí,
        // pero se haría con un método findByActivoTrue() en el Repositorio.
        return pedidoRepository.findAll();
    }
    
    // =========================================================
    // 3. OBTENER PEDIDO POR ID (GET por ID)
    // =========================================================
    public Optional<Pedido> obtenerPorId(Integer id) {
        return pedidoRepository.findById(id);
    }
    
    // =========================================================
    // 4. METODO PRIVADO: CALCULAR MONTO TOTAL
    // =========================================================
    private BigDecimal calcularMontoTotal(List<DetallePedido> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        // Suma el subtotal (cantidad * precioUnitario) de todos los detalles
        return detalles.stream()
            .map(detalle -> {
                // Aseguramos que los valores no sean nulos antes de multiplicar
                BigDecimal precio = detalle.getPrecioUnitario() != null ? detalle.getPrecioUnitario() : BigDecimal.ZERO;
                int cantidad = detalle.getCantidad() != null ? detalle.getCantidad() : 0;
                
                return precio.multiply(BigDecimal.valueOf(cantidad));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Aquí se añadirían métodos para actualizar estado, agregar pago, etc.
    // (Actualizar Pedido y Eliminar Pedido)
}
