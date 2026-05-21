package com.empresa.pedidos.adaptadores.rest;

import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.dominio.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST que expone los endpoints del sistema de pedidos.
 * Solo depende de FachadaPedidos, sin conocer la logica interna del sistema.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final FachadaPedidos fachada;

    public PedidoController(FachadaPedidos fachada) {
        this.fachada = fachada;
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(fachada.crearPedido(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        Optional<Pedido> resultado = fachada.buscarPorId(id);
        return resultado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
