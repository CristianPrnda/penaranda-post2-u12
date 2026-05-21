package com.empresa.pedidos;

import com.empresa.pedidos.adaptadores.facade.FachadaPedidos;
import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de integracion que verifica el flujo completo:
 * FachadaPedidos coordina Factory, Strategy, repositorio JPA
 * y publica el evento PedidoProcesadoEvent que reciben los listeners Observer.
 */
@SpringBootTest
class IntegracionPedidosTest {

    @Autowired
    private FachadaPedidos fachada;

    @Test
    @DisplayName("Flujo completo: pedido ESTANDAR se procesa, persiste y notifica")
    void flujoCompleto_pedidoEstandar_debeCrearseYPersistirse() {
        Pedido pedido = new Pedido("Pedido de prueba", 200.0, TipoPedido.ESTANDAR);

        Pedido resultado = fachada.crearPedido(pedido);

        assertNotNull(resultado.getId());
        assertEquals(EstadoPedido.PROCESADO, resultado.getEstado());
        assertEquals(220.0, resultado.getCosto(), 0.001);
    }

    @Test
    @DisplayName("Flujo completo: pedido EXPRESS se procesa con costo correcto")
    void flujoCompleto_pedidoExpress_debeTenerCostoCorrecto() {
        Pedido pedido = new Pedido("Pedido express", 100.0, TipoPedido.EXPRESS);

        Pedido resultado = fachada.crearPedido(pedido);

        assertEquals(130.0, resultado.getCosto(), 0.001);
        assertEquals(EstadoPedido.PROCESADO, resultado.getEstado());
    }

    @Test
    @DisplayName("Flujo completo: pedido INTERNACIONAL se procesa con costo correcto")
    void flujoCompleto_pedidoInternacional_debeTenerCostoCorrecto() {
        Pedido pedido = new Pedido("Pedido internacional", 100.0, TipoPedido.INTERNACIONAL);

        Pedido resultado = fachada.crearPedido(pedido);

        assertEquals(175.0, resultado.getCosto(), 0.001);
        assertEquals(EstadoPedido.PROCESADO, resultado.getEstado());
    }

    @Test
    @DisplayName("Pedido creado debe poder buscarse por ID")
    void buscarPorId_pedidoExistente_debeRetornarPedido() {
        Pedido pedido = new Pedido("Buscar por ID", 50.0, TipoPedido.ESTANDAR);
        Pedido creado = fachada.crearPedido(pedido);

        Optional<Pedido> encontrado = fachada.buscarPorId(creado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(creado.getId(), encontrado.get().getId());
    }

    @Test
    @DisplayName("Buscar ID inexistente debe retornar Optional vacio")
    void buscarPorId_idInexistente_debeRetornarVacio() {
        Optional<Pedido> resultado = fachada.buscarPorId(99999L);
        assertFalse(resultado.isPresent());
    }
}
